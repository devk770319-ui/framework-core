package com.framwork.core.common.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "framework.datasource")
public class CoreDatasourceProperties {

    private Mode mode = Mode.MULTI;
    private Local local = new Local();
    private Jndi jndi = new Jndi();

    public enum Mode {
        MULTI,
        XA
    }

    @Data
    public static class Local {
        private Db primary = new Db();
        private Db secondary = new Db();
    }

    @Data
    public static class Jndi {
        private String primaryName;
        private String secondaryName;
    }

    @Data
    public static class Db {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private String xaDataSourceClassName;
        private Map<String, String> xaProperties = new HashMap<>();
    }
}
