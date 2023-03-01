package tobyspring.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.MyAutoConfiguration;

//@Configuration
//@Conditional(TomcatWebServerConfig.TomcatCondition.class)
//직접 작성한 커스텀 어노테이션으로 대체
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
@MyAutoConfiguration
public class TomcatWebServerConfig {
    //구성정보를 나타내는 서블릿웹서버팩토리 객체와, 디스패처서블릿 객체를 빈으로 등록
    @Bean("TomcatWebServerFactory")
    //동일한 타입의 빈이 존재하면 해당 빈을 사용
    // : 커스텀 WebServerConfiguration이 존재하면 커스텀 사용하고
    //존재하지 않는다면 자동구성정보를 사용하도록 설정한다.
    //ImportSelector 인터페이스를 구현한 Deferred를 사용한 이유가
    //먼저 유저구성정보가 로딩된 다음에 자동구성정보를 로딩하도록 하기 위해서이다.
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactory(){
        return new TomcatServletWebServerFactory();
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
