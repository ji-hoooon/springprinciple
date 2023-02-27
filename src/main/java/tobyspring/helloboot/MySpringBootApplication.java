package tobyspring.helloboot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//자바 어노테이션인 @Retenation의 범위는 Class이기 때문에
//: 컴파일된 클래스타임까지만 살아있고, 런타임시에는 로딩될 때 사라진다.
@Retention(RetentionPolicy.RUNTIME)
//HellobootApplication 클래스에 적용하기 위해
//: 클래스,인터페이스 ,이넘에 적용된다.
@Target(ElementType.TYPE)
@Configuration
@ComponentScan

public @interface MySpringBootApplication {
}
