package tobyspring.config.autoconfig;

import org.springframework.stereotype.Component;
import tobyspring.config.MyConfigurationProperties;

//@MyConfigurationProperties에 포함시킨다.
//@Component
@MyConfigurationProperties
public class ServerProperties {
    private String contextPath;
    private int port;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
