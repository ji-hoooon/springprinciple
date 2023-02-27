package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

public class HelloControllerTest {
    //단위테스트에서는 모듈 단위로 테스트를 수행한다.
    //한 모듈을 테스트하기 위해서는 모듈과 관련된 상위 모듈과 하위 모듈이 모두 존재해야하므로, 각자의 기능을 수행할 가상의 모듈을 단순히 구현한다.
    //따라서 상위 모듈의 경우 테스트 드라이버로, 가상의 모듈로 필요한 데이터를 인자로 넘겨주고, 그 결과 값을 받는 역할을 수행한다.
    //하위 모듈의 경우 테스트 스텁으로, 테스트할 모듈이 호출할 때 인자로 받은 값을 가지고 로직을 수행하고, 결과를 테스트할 모듈에 넘겨주는 역할을 수행한다

    @Test
    void helloController(){
//        HelloController helloController = new HelloController(new HelloService() {
//            @Override
//            public String sayHello(String name) {
//                return null;
//            }
//        });
        //메서드가 하나이므로 람다식으로 작성
        HelloController helloController = new HelloController(name -> name);

        String ret = helloController.hello("Test");

        Assertions.assertThat(ret).isEqualTo("Test");
    }

    @Test
    //예외가 발생할 경우 성공하는 테스트 메서드 작성 -> 인자가 잘못되었을 땐 IllegalArgumentException 예외 발생
    void failHelloController(){
        //메서드가 하나이므로 람다식으로 작성
        HelloController helloController = new HelloController(name -> name);

        //널이 들어올 경우
        Assertions.assertThatThrownBy(()-> {
            helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);
        //빈 문자열이 들어올 경우
        Assertions.assertThatThrownBy(()-> {
            helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
