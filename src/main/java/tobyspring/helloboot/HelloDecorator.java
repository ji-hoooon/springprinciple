package tobyspring.helloboot;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class HelloDecorator implements HelloService{
    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    //인터페이스를 구현하고, 구현한 내용에 로직 추가
    @Override
    public String sayHello(String name) {
        return "*"+helloService.sayHello(name)+"*";
    }
}
