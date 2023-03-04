package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;
//@HellobootTest
//DB 초기화 루틴을 수행하는 부트테스트 어노테이션
//: 빈을 모두 로딩하지만, 웹 환경 세팅 제외
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//모든 테스트를 다른 테스트에 영향없이 독립적으로 동작하게 하기위해
@Transactional
public class HelloServiceCountTest {
    //테스트를 위한 의존성 주입
    //HelloService를 사용하는 클라이언트 -> HelloService를 의존하는 HelloController
    //: HelloController 입장에서 테스트하도록 코드를 작성해
    //HelloService를 구현한 다른 서비스의 테스트에서도 사용가능하므로 인터페이스 타입을 의존성 주입 받는다.

    @Autowired
    HelloService helloService;
    @Autowired
    HelloRepository helloRepository;
    @Test
    //DB 테스트 방법
    //(1) JdbcTemplate 이용
    //(2) Repository의 작성해둔 테스트 이용
    void sayHelloIncreaseCount(){
//        helloService.sayHello("Toby");
//        Assertions.assertThat(helloRepository.countOf("Toby")).isEqualTo(1);
//
//        helloService.sayHello("Toby");
//        Assertions.assertThat(helloRepository.countOf("Toby")).isEqualTo(2);
        //스트림을 이용한 여러번 테스트 수행
        IntStream.rangeClosed(1,10).forEach(count-> {
            helloService.sayHello("Toby");
            Assertions.assertThat(helloRepository.countOf("Toby")).isEqualTo(count);
        });
    }


}
