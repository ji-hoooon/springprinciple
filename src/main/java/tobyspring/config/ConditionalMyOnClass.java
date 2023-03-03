package tobyspring.config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Conditional(MyOnClassCondition.class)
public @interface ConditionalMyOnClass {
    //컨디션에서 읽을 수 있는 속성(엘리먼트) 값을 제공하는 역할
    String value();
}
