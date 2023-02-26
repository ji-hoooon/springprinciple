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
public class HellobootApplication {
    //스프링 컨테이너에게 빈으로 등록해달라고 전달하는 어노테이션
    @Bean
    //자바코드 구성 정보 사용을 위한 팩토리 메서드 작성
    //: 서비스에 의존하는 컨트롤러의 인자로 서비스를 받도록 스프링 컨테이너에게 요청
    public HelloController helloController(HelloService helloService){
        return new HelloController(helloService);
    }
    @Bean
    //반환타입을 인터페이스로 설정해, 변경에 유연함을 더한다.
    public HelloService helloService(){
        return new SimpleHelloService();
    }

    public static void main(String[] args) {
        //스프링 컨테이너를 위한 인터페이스를 이용해 스프링 컨테이너 생성
//        GenericApplicationContext applicationContext = new GenericApplicationContext();
        //템플릿 메서드 패턴으로 작성된 메서드를 익명 클래스로 오버라이딩
//        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(){
        //: 자바 구성 정보를 사용할 수 있는 타입으로 변경
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(){
            @Override
            protected void onRefresh() {
                super.onRefresh();
                //ServletWebServerFactory 인터페이스를 구현한 TomcatServletWebServerFactory
                ServletWebServerFactory serverFactory= new TomcatServletWebServerFactory();
                //서블릿 컨테이너 추상체에 ServletContextInitializer를 구현한 클래스를 전달해 서블릿 등
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    //(1) 서블릿 등록 (요청과 응답)
                    //서블릿 이름과 서블릿 클래스 정보 or 서블릿 타입의 오브젝트 전달
                    //: 서블릿 타입의 오브젝트는 요청과 응답 객체를 전달하는 HttpServlet을 만들어서 전달

                    //사용할 컨트롤러 객체 생성 -> 스프링 컨테이너에게 주입받는 방식으로 변경
                    //프론트 컨트롤러 -> DispatcherServlet으로 전환 : DispatcherServlet은 등록된 빈의 정보를 알아야 하므로, 스프링 컨테이너(GenericWebApllicationContext)를 파라미터로 전달
                    servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this) {
//                //(2) 서블릿 매핑
                    }).addMapping("/*");
                });
                //생성한 톰캣 웹서버를 통해 서블릿 컨테이너를 띄우는 메서드
                webServer.start();
            }
        };
        //빈에 대한 메타정보를 제공해서 빈 등록
//        applicationContext.registerBean(HelloController.class);
//        applicationContext.registerBean(SimpleHelloService.class);
        //: AnnotationConfigWebApplicationContext을 이용한 자바구성정보를 이용하기 위해 등록하는 것으로 변경
        applicationContext.register(HellobootApplication.class);
        //애플리케이션 컨텍스트에 등록된 빈 초기화
        applicationContext.refresh();
    }
}
