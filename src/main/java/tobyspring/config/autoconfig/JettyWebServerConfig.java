package tobyspring.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.MyAutoConfiguration;

//@Configuration
@MyAutoConfiguration
//option+enter : create inner class (중첩 스태틱 클래스로 조건 클래스 작성)
//@Conditional(JettyWebServerConfig.JettyCondition.class)
//직접 작성한 커스텀 어노테이션으로 대체
@ConditionalMyOnClass("org.eclipse.jetty.server.Server")
public class JettyWebServerConfig {
    //구성정보를 나타내는 서블릿웹서버팩토리 객체와, 디스패처서블릿 객체를 빈으로 등록
    @Bean("JettyWebServerFactory")
    //동일한 타입의 빈이 존재하면 해당 빈을 사용
    // : 커스텀 WebServerConfiguration이 존재하면 커스텀 사용하고
    //존재하지 않는다면 자동구성정보를 사용하도록 설정한다.
    //ImportSelector 인터페이스를 구현한 Deferred를 사용한 이유가
    //먼저 유저구성정보가 로딩된 다음에 자동구성정보를 로딩하도록 하기 위해서이다.
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactory(){
        return new JettyServletWebServerFactory();
    }
    //디스패처가 사용할 컨트롤러를 찾기 위해 컨트롤러를 넘겨주기 위해 애플리케이션 컨텍스트를 생성자의 파라미터로 전달


//    static class JettyCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
////            return true;
//            return ClassUtils.isPresent("org.eclipse.jetty.server.Server", context.getClassLoader());
//        }
//    }
}
