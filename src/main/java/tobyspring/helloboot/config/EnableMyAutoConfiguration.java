package tobyspring.helloboot.config;


import org.springframework.context.annotation.Import;
import tobyspring.helloboot.config.autoconfig.DispatcherServletConfig;
import tobyspring.helloboot.config.autoconfig.TomcatWebServerConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//MySpringBootApplication 어노테이션에 붙였던 @Import문을 가져온다.
//@Import({TomcatWebServerConfig.class, DispatcherServletConfig.class})
//동적으로 자동 구성 정보를 등록할 수 있도록 변경
@Import(MyAutoConfigImportSelector.class)
public @interface EnableMyAutoConfiguration {
}
