# syntax=docker/dockerfile:1

################################################################################
# Stage 1 — Build the Spring Boot fat jar with the Maven wrapper.
# Uses a full JDK. Dependencies are resolved in a cached layer so that source
# changes don't force a full re-download on every build.
################################################################################
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /build

# Copy the Maven wrapper first and prime the local repository. This layer is
# only invalidated when the wrapper or the pom change, not on source edits.
COPY --chmod=0755 mvnw ./
COPY .mvn/ .mvn/
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -B -DskipTests

# Copy the source and build the repackaged (executable) jar.
COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -B -DskipTests

# Split the fat jar into Spring Boot layers so the runtime image can cache
# dependencies separately from the fast-changing application classes.
RUN cp target/*.jar app.jar \
    && java -Djarmode=layertools -jar app.jar extract --destination extracted

################################################################################
# Stage 2 — Minimal runtime image. Only a JRE plus the extracted layers.
################################################################################
FROM eclipse-temurin:17-jre-jammy AS final

# curl is used by the container HEALTHCHECK to hit the actuator endpoint.
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Run as an unprivileged user rather than root.
ARG UID=10001
RUN useradd --uid "${UID}" --no-create-home --shell /usr/sbin/nologin appuser
USER appuser

# Copy the Spring Boot layers in order of change-frequency (most stable first)
# to maximise Docker layer cache hits.
COPY --from=build /build/extracted/dependencies/ ./
COPY --from=build /build/extracted/spring-boot-loader/ ./
COPY --from=build /build/extracted/snapshot-dependencies/ ./
COPY --from=build /build/extracted/application/ ./

# Default port matches the "dev" profile (server.port=6000). The "prod" profile
# listens on 4032 — publish the matching port in compose / `docker run`.
EXPOSE 6000

# Container-level health check against Spring Boot Actuator. SERVER_PORT lets
# the check follow whichever port the active profile binds to.
ENV SERVER_PORT=6000
HEALTHCHECK --interval=15s --timeout=5s --start-period=60s --retries=5 \
    CMD curl -fsS "http://localhost:${SERVER_PORT}/actuator/health" || exit 1

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
