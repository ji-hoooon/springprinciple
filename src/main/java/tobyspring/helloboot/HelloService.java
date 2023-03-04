package tobyspring.helloboot;

public interface HelloService {
    String sayHello(String name);

    //람다식 코드를 해결하기 위한 디폴트메서드화
    //-> 구현 안하면 0을 리턴
//    int countOf(String name);
    default int countOf(String name){
        return 0;
    }
}
