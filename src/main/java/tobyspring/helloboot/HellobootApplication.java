package tobyspring.helloboot;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.print.attribute.standard.Media;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//클래스 레벨에서 빈 구성정보를 가진 클래스임을 전달하는 어노테이션
//: 빈 팩토리 메서드 뿐만 아니라, 전체 애플리케이션 구성 정보를 가진다.
@Configuration
@ComponentScan
public class HellobootApplication {
//    //스프링 컨테이너에게 빈으로 등록해달라고 전달하는 어노테이션
//    @Bean
//    //자바코드 구성 정보 사용을 위한 팩토리 메서드 작성
//    //: 서비스에 의존하는 컨트롤러의 인자로 서비스를 받도록 스프링 컨테이너에게 요청
//    public HelloController helloController(HelloService helloService){
//        return new HelloController(helloService);
//    }
//    @Bean
//    //반환타입을 인터페이스로 설정해, 변경에 유연함을 더한다.
//    public HelloService helloService(){
//        return new SimpleHelloService();
//    }
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


//    public static void main(String[] args) {
//        MySpringApplication.run(HellobootApplication.class, args);
//    }

    public static void main(String[] args) {
        SpringApplication.run(HellobootApplication.class, args);
    }


}
