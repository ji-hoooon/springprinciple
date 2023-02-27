package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

//빈의 구성정보를 담은 팩토리 메서드를 가지는 클래스로
//이 클래스도 스프링 구성정보로 등록이 되어야 한다.
//@Component를 메타어노테이션으로 가지는 어노테이션
@Configuration
public class Config {
    //구성정보를 나타내는 서블릿웹서버팩토리 객체와, 디스패처서블릿 객체를 빈으로 등록
    @Bean
    public ServletWebServerFactory servletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }
    //디스패처가 사용할 컨트롤러를 찾기 위해 컨트롤러를 넘겨주기 위해 애플리케이션 컨텍스트를 생성자의 파라미터로 전달

    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
        //기본 생성자임에도 스프링 컨테이너가 빈의 생명주기에 의해 디스패처서블릿에 애플리케이션 컨텍스트를 자동 주입

    }
}

