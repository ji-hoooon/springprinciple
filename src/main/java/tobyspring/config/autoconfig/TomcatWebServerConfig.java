package tobyspring.config.autoconfig;

import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.MyAutoConfiguration;

//@Configuration
//@Conditional(TomcatWebServerConfig.TomcatCondition.class)
//직접 작성한 커스텀 어노테이션으로 대체
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
@MyAutoConfiguration
//사용할 프로퍼티 클래스를 직접 임포트
@Import(ServerProperties.class)
public class TomcatWebServerConfig {

    //치환자를 이용해 프로퍼티값을 가져온다.
    //: 해당 빈이 생성된 이후에 치환자에 프로퍼티값이 채워지게 되므로, 빈 메서드에서 사용가능한 필드가 된다.
//    @Value("${contextPath:}")
//    String contextPath;
//
//    @Value("${port : 8080}")
//    int port;
    //: 프로퍼티를 담은 클래스로 추출

    //구성정보를 나타내는 서블릿웹서버팩토리 객체와, 디스패처서블릿 객체를 빈으로 등록
    @Bean("TomcatWebServerFactory")
    //동일한 타입의 빈이 존재하면 해당 빈을 사용
    // : 커스텀 WebServerConfiguration이 존재하면 커스텀 사용하고
    //존재하지 않는다면 자동구성정보를 사용하도록 설정한다.
    //ImportSelector 인터페이스를 구현한 Deferred를 사용한 이유가
    //먼저 유저구성정보가 로딩된 다음에 자동구성정보를 로딩하도록 하기 위해서이다.
    @ConditionalOnMissingBean
//    public ServletWebServerFactory servletWebServerFactory(){
//        return new TomcatServletWebServerFactory();
//    }
    //Setter메서드를 이용해 자동 구성의 기본 설정 값의 변경 하기 위해 factory 변수로 받는다.
//    public ServletWebServerFactory servletWebServerFactory(){
    //property값을 읽어서 지정하는 방법을 적용하기 위해 Environment 객체를 주입 (빈 메서드이기 때문에 주입 가능)
//    public ServletWebServerFactory servletWebServerFactory(Environment env){
    //빈 클래스의 필드를 가져오는 방법으로 변경
//    public ServletWebServerFactory servletWebServerFactory(){
    //프로퍼티를 담은 빈을 파라미터로 전달
    public ServletWebServerFactory servletWebServerFactory(ServerProperties properties){

        TomcatServletWebServerFactory factory=new TomcatServletWebServerFactory();
        //add, set 등등 다양한 메서드 존재
        //: contextpath 지정해 모든 서블릿 매핑 앞에 경로 설정
//        factory.setContextPath("/app");
        //application.properties에 지정
//        factory.setContextPath(env.getProperty("contextPath"));
        //빈 클래스에 필드를 이용
        factory.setContextPath(properties.getContextPath());
        //해당 필드 이름의 프로퍼티가 존재하지 않는 경우 : 에러 발생
        //-> 기본값을 넣어서 에러 발생하지 않도록 설정
        factory.setPort(properties.getPort());

        return factory;
    }
    //디스패처가 사용할 컨트롤러를 찾기 위해 컨트롤러를 넘겨주기 위해 애플리케이션 컨텍스트를 생성자의 파라미터로 전달



//    static class TomcatCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
////            return false;
//            //ClassUtils 클래스의 isPresent()를 이용해 특정 라이브러리 포함 여부를 체크한다.
//            //: 인자로 클래스의 풀 네임과 classLoader를 전달한다.
//            //이전의 classLoader 생성 방식 : 생성자 혹은 라이프사이클 인터페이스 구현한 setter메서드로 주입
//            //하지만 ConditionContext에 애플리케이션 환경에 관련된 객체들 중에 classLoader가 존재
//            return ClassUtils.isPresent("org.apache.catalina.startup.Tomcat", context.getClassLoader());
//        }
//    }

}
