package com.project.ecommerce_api.category.domain;

import com.project.ecommerce_api.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {


    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;
}
