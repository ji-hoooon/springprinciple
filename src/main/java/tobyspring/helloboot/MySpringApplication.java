package tobyspring.helloboot;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {
    public static void run(Class<?> applicationClass, String ... args) {
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
//                ServletWebServerFactory serverFactory= new TomcatServletWebServerFactory();
                //빈으로 등록한 구성정보 객체와 디스패처서블릿을 얻는다.
                ServletWebServerFactory serverFactory= this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet=this.getBean(DispatcherServlet.class);
                //팩토리 메서드로 생성자 없이 생성했으므로, 디스패처서블릿에 스프링 컨테이너(애플리케이션 컨텍스트) 주입
                dispatcherServlet.setApplicationContext(this);

                //서블릿 컨테이너 추상체에 ServletContextInitializer를 구현한 클래스를 전달해 서블릿 등
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    //(1) 서블릿 등록 (요청과 응답)
                    //서블릿 이름과 서블릿 클래스 정보 or 서블릿 타입의 오브젝트 전달
                    //: 서블릿 타입의 오브젝트는 요청과 응답 객체를 전달하는 HttpServlet을 만들어서 전달

                    //사용할 컨트롤러 객체 생성 -> 스프링 컨테이너에게 주입받는 방식으로 변경
                    //프론트 컨트롤러 -> DispatcherServlet으로 전환 : DispatcherServlet은 등록된 빈의 정보를 알아야 하므로,
                    // 스프링 컨테이너(GenericWebApllicationContext)를 파라미터로 전달
//                    servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this) {
                    //빈을 통해 얻은 디스패처서블릿 전달
                    servletContext.addServlet("dispatcherServlet",dispatcherServlet
//                //(2) 서블릿 매핑
                    ).addMapping("/*");
                });
                //생성한 톰캣 웹서버를 통해 서블릿 컨테이너를 띄우는 메서드
                webServer.start();
            }
        };
        //빈에 대한 메타정보를 제공해서 빈 등록
//        applicationContext.registerBean(HelloController.class);
//        applicationContext.registerBean(SimpleHelloService.class);
        //: AnnotationConfigWebApplicationContext을 이용한 자바구성정보를 이용하기 위해 등록하는 것으로 변경
//        applicationContext.register(HellobootApplication.class);
        //run메서드로 추출 후, 타입을 받을 수 있도록 변경
        applicationContext.register(applicationClass);
        //애플리케이션 컨텍스트에 등록된 빈 초기화
        applicationContext.refresh();
    }
}
