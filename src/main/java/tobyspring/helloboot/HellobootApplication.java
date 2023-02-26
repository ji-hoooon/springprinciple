package tobyspring.helloboot;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

public class HellobootApplication {
//    public static void main(String[] args) {
//        //ServletWebServerFactory 인터페이스를 구현한 TomcatServletWebServerFactory
//       ServletWebServerFactory serverFactory= new TomcatServletWebServerFactory();
//       //서블릿 컨테이너 추상체에 ServletContextInitializer를 구현한 클래스를 전달해 서블릿 등
//        WebServer webServer = serverFactory.getWebServer(servletContext -> {
//            //(1) 서블릿 등록
//            //(2) 서블릿 매핑
//            //(1) 서블릿 등록 (요청과 응답)
//            //서블릿 이름과 서블릿 클래스 정보 or 서블릿 타입의 오브젝트 전달
//            //: 서블릿 타입의 오브젝트는 요청과 응답 객체를 전달하는 HttpServlet을 만들어서 전달
//            servletContext.addServlet("hello", new HttpServlet() {
//                @Override
//                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                    //서블릿 요청 처리 작성
//                    String name = req.getParameter("name");
//
//                    //서블릿 응답 작성
//                    //: 상태코드, 헤더, 바디
////                    resp.setStatus(200);
//                    resp.setStatus(HttpStatus.OK.value());
////                    resp.setHeader("Content-Type", "text/plain");
//                    resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
//                    resp.getWriter().println("Hello "+name);
//                }
//            //(2) 서블릿 매핑
//            }).addMapping("/hello");
//        });
//        //생성한 톰캣 웹서버를 통해 서블릿 컨테이너를 띄우는 메서드
//        webServer.start();
//    }

    public static void main(String[] args) {
        //스프링 컨테이너를 위한 인터페이스를 이용해 스프링 컨테이너 생성
//        GenericApplicationContext applicationContext = new GenericApplicationContext();

        //템플릿 메서드 패턴으로 작성된 메서드를 익명 클래스로 오버라이딩
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(){


        };
        //빈에 대한 메타정보를 제공해서 빈 등록
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        //애플리케이션 컨텍스트에 등록된 빈 초기화
        applicationContext.refresh();

        //ServletWebServerFactory 인터페이스를 구현한 TomcatServletWebServerFactory
        ServletWebServerFactory serverFactory= new TomcatServletWebServerFactory();
        //서블릿 컨테이너 추상체에 ServletContextInitializer를 구현한 클래스를 전달해 서블릿 등
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            //(1) 서블릿 등록
            //(2) 서블릿 매핑

            //(1) 서블릿 등록 (요청과 응답)
            //서블릿 이름과 서블릿 클래스 정보 or 서블릿 타입의 오브젝트 전달
            //: 서블릿 타입의 오브젝트는 요청과 응답 객체를 전달하는 HttpServlet을 만들어서 전달

            //사용할 컨트롤러 객체 생성 -> 스프링 컨테이너에게 주입받는 방식으로 변경
//            HelloController helloController = new HelloController();
            //프론트 컨트롤러 -> DispatcherServlet으로 전환 : DispatcherServlet은 등록된 빈의 정보를 알아야 하므로, 스프링 컨테이너(GenericWebApllicationContext)를 파라미터로 전달
//            servletContext.addServlet("frontcontroller", new HttpServlet() {
            servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext) {
//                @Override
//                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                    //인증, 보안, 다국어, 공통 기능 처리
//
//                    //매핑과 바인딩
//                    //: 모든 url의 요청을 프론트 컨트롤러가 받고, 프론트 컨트롤러 내에서 매핑 처리 후, 데이터 바인딩
//                    if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())){
//                        //서블릿 요청 처리 작성 (파라미터 추출해 바인딩)
//                        String name = req.getParameter("name");
//                        //애플리케이션 컨텍스트에게 빈 주입받는다.
//                        //: 빈의 이름을 전달하는 방법 혹은 클래스 타입을 전달하는 방법
//                        //-> 객체가 필요할 때마다 새로 생성하지 않고도, 애플리케이션 컨텍스트에게 요청해 사용할 수 있다.
//                        HelloController helloController=applicationContext.getBean(HelloController.class);
//                        String ret = helloController.hello(name);
//
//                        //서블릿 응답 작성
//                        //: 상태코드, 헤더, 바디
////                    resp.setStatus(200);
////                        resp.setStatus(HttpStatus.OK.value());    //생략 가능
////                    resp.setHeader("Content-Type", "text/plain");
////                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
//                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
////                        resp.getWriter().println("Hello "+name);
//
//                        //HelloController 객체를 이용한 응답 객체 작성
//                        resp.getWriter().println(ret);
//
//
//                    }else if(req.getRequestURI().equals("/user")){
//                        //...
//                    }else{
//                        resp.setStatus(HttpStatus.NOT_FOUND.value());
//                    }
//                }
//                //(2) 서블릿 매핑
            }).addMapping("/*");
        });
        //생성한 톰캣 웹서버를 통해 서블릿 컨테이너를 띄우는 메서드
        webServer.start();
    }
}
