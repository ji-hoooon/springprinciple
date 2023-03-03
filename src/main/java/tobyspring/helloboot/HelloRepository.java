package tobyspring.helloboot;


public interface HelloRepository {
    Hello findHello(String name);
    //없으면 null 리턴하도록
    Hello findHelloOld(String name);
    //없으면 예외를 발생하는 queryForObject메서드


    void increaseCount(String name);
    //데이터 가공하는 간단한 메서드로, 이름을 통해 해당 클래스의 카운트를 증가시키는 메서드

    //default 메서드로, 모든 리포지토리를 구현하는 클래스에 추가하는 메서드
    //: 디폴트 메서드를 이용해 인터페이스를 구현하는 클래스들이 구현해야할 메서드가 줄어들고
    // 인터페이스의 추상메서드를 재사용할 수 있다. -> Comparator 클래스에 디폴트 메서드, static 메서드 많음
    default int countOf(String name){
        Hello hello= findHello(name);
        return hello==null?0:hello.getCount();
    }
}
