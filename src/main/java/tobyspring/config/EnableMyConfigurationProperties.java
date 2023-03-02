package tobyspring.config;


import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Enable이름이 붙은 어노테이션은 대부분 안에 @Import을 메타어노테이션으로 가지는 어노테이션
//: 특정 기능을 가진 Configuration클래스나 ImportSelector같은 클래스를 가져오는 목적
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//자동 구성 클래스를 동적으로 로딩하기 위해서 사용했던 임포트 셀렉터를 작성
@Import(MyConfigurationPropertiesImportSelector.class)
public @interface EnableMyConfigurationProperties {
    //엘리먼트 타입을 클래스타입으로 정의
    Class<?> value();
}
