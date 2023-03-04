package tobyspring.helloboot;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

        //스프링 컨테이너를 띄우지 않고 코드상에서 단위 테스트를 수행하던 메서드지만, 현재는 DB에 연결된 상태
        //: 하지만 해당 메서드의 목적에 부합하도록 단순하게 helloRepository만 생성해서 전달하도록 변경
        SimpleHelloService helloService = new SimpleHelloService(helloRepositoryStub);

        String ret= helloService.sayHello("Test");

        //Live Templates을 이용해 asj 만들기
        assertThat(ret).isEqualTo("Hello Test");
    }

    private static HelloRepository helloRepositoryStub = new HelloRepository()  {
            @Override
            public Hello findHello(String name) {
                return null;
            }

            @Override
            public Hello findHelloOld(String name) {
                return null;
            }

            @Override
            public void increaseCount(String name) {

            }
        };

    @Test
    void helloDecorator(){
        HelloDecorator helloDecorator=new HelloDecorator(name -> name);


        String ret =helloDecorator.sayHello("Test");
        assertThat(ret).isEqualTo("*Test*");
    }
}
