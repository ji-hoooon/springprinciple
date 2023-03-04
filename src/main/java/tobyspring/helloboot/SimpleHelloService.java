package tobyspring.helloboot;

import org.springframework.stereotype.Service;

//@Component
@Service
public class SimpleHelloService implements HelloService {
    //의존성 주입
    private final HelloRepository helloRepository;

    //기본생성자가 아닌 파라미터를 넣을 경우, 영향을 받는 코드가 존재할 수 있다.
    //: SimpleHelloService가 helloRepository 빈에 의존하게 된다.
    public SimpleHelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @Override
    public String sayHello(String name){
        this.helloRepository.increaseCount(name);
        return "Hello "+name;
    }

    @Override
    public int countOf(String name) {
        return helloRepository.countOf(name);
    }
}
