package com.project.assesment.user.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Response<T> {
    private Integer status;
    private String message;
    private T data;
}
