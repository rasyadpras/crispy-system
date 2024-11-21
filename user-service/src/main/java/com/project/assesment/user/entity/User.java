package com.project.assesment.user.entity;

import lombok.Builder;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String email;
    private String address;
    private Integer age;
}
