package tobyspring.helloboot;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

//클래스 레벨에서 빈 구성정보를 가진 클래스임을 전달하는 어노테이션
//: 빈 팩토리 메서드 뿐만 아니라, 전체 애플리케이션 구성 정보를 가진다.
//@Configuration
//@ComponentScan
//합성 어노테이션으로 변경
//@MySpringBootApplication
@SpringBootApplication
public class HellobootApplication {
    private final JdbcTemplate jdbcTemplate;

    public HellobootApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //자바 표준 어노테이션으로 스프링 프레임워크에 존재하던 라이프사이클 인터페이스를 쉽게 사용가능
    //스프링 프레임워크에 존재하던 라이프사이클 인터페이스 : implements InitializingBean
    //implements InitializingBean이 @PostConstruct의 init()을 찾아서 실행시켜준다.

    @PostConstruct
    // :스프링 부트 애플리케이션이 모든 준비가 끝나면 실행되는 메서드
    void init(){
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }


    //    public static void main(String[] args) {
//        MySpringApplication.run(HellobootApplication.class, args);
//    }

    //초기화 작업, 컨테이너 기능을 수행해 간단하게 확인할 경우에 사용하는 방법
    @Bean
    //빈 생성시 다른 빈을 주입
    //: 스프링이 제공하는 환경정보를 추상화한 객
    ApplicationRunner applicationRunner(Environment env){
        return args -> {
            String name = env.getProperty("my.name");
            System.out.println("my.name: "+name);
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(HellobootApplication.class, args);
    }

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

    //Config 클래스로 대체
//    //구성정보를 나타내는 서블릿웹서버팩토리 객체와, 디스패처서블릿 객체를 빈으로 등록
//    @Bean
//    public ServletWebServerFactory servletWebServerFactory(){
//        return new TomcatServletWebServerFactory();
//    }
//    //디스패처가 사용할 컨트롤러를 찾기 위해 컨트롤러를 넘겨주기 위해 애플리케이션 컨텍스트를 생성자의 파라미터로 전달
//
//    @Bean
//    public DispatcherServlet dispatcherServlet(){
//        return new DispatcherServlet();
//        //기본 생성자임에도 스프링 컨테이너가 빈의 생명주기에 의해 디스패처서블릿에 애플리케이션 컨텍스트를 자동 주입
//
//    }


}
