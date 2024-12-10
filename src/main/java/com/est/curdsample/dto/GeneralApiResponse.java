package com.est.curdsample.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneralApiResponse<T> {
    private T data;
    private String msg;
}
