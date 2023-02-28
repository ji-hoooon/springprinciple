package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class ConditionalTest {
    @Test
    void conditional1(){
        //true
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(Config1.class);
        ac.refresh();

        //null 리턴하거나 예외를 발생시키는 경우를 고려해야한다.
        MyBean bean = ac.getBean(MyBean.class);
        //false
        AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
        ac2.register(Config1.class);
        ac2.refresh();

        //null 리턴하거나 예외를 발생시키는 경우를 고려해야한다.
        MyBean bean2 = ac2.getBean(MyBean.class);
    }
    @Test
    void conditional2(){
        //true
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration(Config1.class)
                //람다식을 인자로 받는 실행 메서드
                .run(context -> {
                    //Configuration 어노테이션 클래스와 해당 어노테이션이 붙은 클래스도 빈으로 등록되는지 테스트
                    Assertions.assertThat(context).hasSingleBean(MyBean.class);
                    Assertions.assertThat(context).hasSingleBean(Config1.class);
                });


        //false
        new ApplicationContextRunner().withUserConfiguration(Config2.class)
                //람다식을 인자로 받는 실행 메서드
                .run(context -> {
                    //Configuration 어노테이션 클래스와 해당 어노테이션이 붙은 클래스도 빈으로 등록되지 않는지 테스트
                    Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
                    Assertions.assertThat(context).doesNotHaveBean(Config2.class);
                });
    }
    @Test
    void conditional3(){
        //true
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration(Config3.class)
                //람다식을 인자로 받는 실행 메서드
                .run(context -> {
                    //Configuration 어노테이션 클래스와 해당 어노테이션이 붙은 클래스도 빈으로 등록되는지 테스트
                    Assertions.assertThat(context).hasSingleBean(MyBean.class);
                    Assertions.assertThat(context).hasSingleBean(Config3.class);
                });


        //false
        new ApplicationContextRunner().withUserConfiguration(Config4.class)
                //람다식을 인자로 받는 실행 메서드
                .run(context -> {
                    //Configuration 어노테이션 클래스와 해당 어노테이션이 붙은 클래스도 빈으로 등록되지 않는지 테스트
                    Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
                    Assertions.assertThat(context).doesNotHaveBean(Config4.class);
                });
    }


    //@Conditional을 메타어노테이션으로 하는 테스트하고 싶은 조건을 가진 어노테이션
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(TrueCondition.class)
    @interface TrueConditional{}

    //@Conditional을 메타어노테이션으로 하는 테스트하고 싶은 조건을 가진 어노테이션
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(FalseCondition.class)
    @interface FalseConditional{}


    //AnnotatedTypeMetadata를 이용한 조건부 자동 설정
    //: True 조건과 False 조건을 어노테이션의 엘리먼트로 지정할 수 있는 어노테이션 작성
    //@Conditional을 메타어노테이션으로 하는 테스트하고 싶은 조건을 가진 어노테이션
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface BooleanConditional{
        boolean value();
        //필드로 value를 사용하면 @BooleanConditional 지정시 속성 이름을 생략할 수 있다.
    }

    static class BooleanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            //어노테이션의 정보인 속성값(엘리먼트)를 가져와서 조건으로 사용한다.
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            return (Boolean)annotationAttributes.get("value");
        }
    }

    @BooleanConditional(true)
    @Configuration
    static class Config3{
        @Bean
        MyBean myBean(){
            return new MyBean();
        }
    }
    @BooleanConditional(false)
    @Configuration
    static class Config4{
        @Bean
        MyBean myBean(){
            return new MyBean();
        }
    }

    //    @Conditional(TrueCondition.class)
    //테스트 하고 싶은 조건을 담은 어노테이션으로 대체
    @TrueConditional
    @Configuration
    static class Config1{
        @Bean
        MyBean myBean(){
            return new MyBean();
        }
    }

//    @Conditional(FalseCondition.class)
//테스트 하고 싶은 조건을 담은 어노테이션으로 대체
    @FalseConditional
    @Configuration
    static class Config2{
        @Bean
        MyBean myBean(){
            return new MyBean();
        }
    }
    static class TrueCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }
    static class FalseCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }
    static class MyBean{}
}
