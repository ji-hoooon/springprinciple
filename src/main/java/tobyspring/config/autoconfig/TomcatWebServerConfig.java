package tobyspring.config.autoconfig;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import tobyspring.config.MyAutoConfiguration;

//@Configuration
@Conditional(TomcatWebServerConfig.TomcatCondition.class)
@MyAutoConfiguration
public class TomcatWebServerConfig {
    //구성정보를 나타내는 서블릿웹서버팩토리 객체와, 디스패처서블릿 객체를 빈으로 등록
    @Bean("TomcatWebServerFactory")
    public ServletWebServerFactory servletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }
    //디스패처가 사용할 컨트롤러를 찾기 위해 컨트롤러를 넘겨주기 위해 애플리케이션 컨텍스트를 생성자의 파라미터로 전달


    static class TomcatCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }

}