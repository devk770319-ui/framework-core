package com.biz.sample.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SampleRequestVo {

    @NotBlank
    private String name;

    @NotNull
    private Integer quantity;
}
