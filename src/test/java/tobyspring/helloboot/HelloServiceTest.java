package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.METHOD)
@UnitTest
@interface FastUnitTest{
}

@Retention(RetentionPolicy.RUNTIME)
//UnitTest는 메타어노테이션으로는 사용 불가
//@Target(ElementType.METHOD)
//UnitTest는 메타어노테이션으로 사용하기 위해서 타겟을추가
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Test
@interface UnitTest{
}
public class HelloServiceTest {
    @FastUnitTest
//    @UnitTest
    void simpleHelloService(){
        SimpleHelloService helloService = new SimpleHelloService();

        String ret= helloService.sayHello("Test");

        //Live Templates을 이용해 asj 만들기
        assertThat(ret).isEqualTo("Hello Test");
    }

    @Test
    void helloDecorator(){
        HelloDecorator helloDecorator=new HelloDecorator(name -> name);

        String ret =helloDecorator.sayHello("Test");
        assertThat(ret).isEqualTo("*Test*");
    }
}
