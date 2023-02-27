package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloServiceTest {
    @Test
    void simpleHelloService(){
        SimpleHelloService helloService = new SimpleHelloService();

        String ret= helloService.sayHello("Test");

        //Live Templates을 이용해 asj 만들기
        assertThat(ret).isEqualTo("Hello Test");
    }
}
