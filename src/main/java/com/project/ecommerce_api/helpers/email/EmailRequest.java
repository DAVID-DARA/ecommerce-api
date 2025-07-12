package com.project.ecommerce_api.helpers.email;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String to;
    private String subject;
    private String body;
}
