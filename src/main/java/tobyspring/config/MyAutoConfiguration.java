package tobyspring.config;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//규격화된 방식으로 작성된 외부 설정 파일을 이용해 자동 구성 정보를 분리하기 위한 어노테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//이 어노테이션을 붙이면 구성정보를 담은 클래스라는 것을 알려주는 어노테이션을 메타 어노테이션으로 설정
//기본값
//@Configuration(proxyBeanMethods = true)
//proxyBeanMethods 설정을 false로 변경
@Configuration(proxyBeanMethods = false)
public @interface MyAutoConfiguration {
}
