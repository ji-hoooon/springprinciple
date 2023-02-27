package tobyspring.config.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class DispatcherServletConfig {
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
        //기본 생성자임에도 스프링 컨테이너가 빈의 생명주기에 의해 디스패처서블릿에 애플리케이션 컨텍스트를 자동 주입
    }
}
