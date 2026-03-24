package com.framwork.core.common.cors;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "framework.cors")
public class CorsPolicyProperties {

    private List<String> allowedOrigins = new ArrayList<>();
}
