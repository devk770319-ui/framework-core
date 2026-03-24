package com.framwork.core.common.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "framework.file")
public class StorageProperties {

    private String baseDir = "./uploads";
}
