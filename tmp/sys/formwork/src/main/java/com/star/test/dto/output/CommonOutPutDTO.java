package com.star.test.dto.output;

import lombok.Data;

@Data
public class CommonOutPutDTO<T> extends SimpleOutPutDTO {

    private T data;

    public CommonOutPutDTO(T data) {
        this.data = data;
    }
}
