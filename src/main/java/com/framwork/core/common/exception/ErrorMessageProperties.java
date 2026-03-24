package com.framwork.core.common.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "framework.error")
public class ErrorMessageProperties {

    private String defaultCode = "BIZ-0001";
}
