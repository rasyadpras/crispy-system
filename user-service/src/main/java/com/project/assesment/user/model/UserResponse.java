package com.project.assesment.user.model;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private String address;
    private Integer age;
}
