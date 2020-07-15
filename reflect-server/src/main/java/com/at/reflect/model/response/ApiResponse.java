package com.at.reflect.model.response;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private T data;
    
}
