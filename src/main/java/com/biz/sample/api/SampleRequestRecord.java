package com.biz.sample.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SampleRequestRecord(
        @NotBlank String name,
        @Positive int quantity
) {
}
