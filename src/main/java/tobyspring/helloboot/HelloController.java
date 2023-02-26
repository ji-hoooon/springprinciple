package tobyspring.helloboot;

//컨트롤러 작성
//: 스프링 컨테이너 안에서 마치 웹 컨테이너 안에 있는 웹 컴포넌트처럼 웹의 요청을 받아 결과를 응답하는 역할 수행

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

////REST API
////: HTML을 통채로 응답하지 않고, 요청에 대한 응답을 바디에 원하는 타입으로 인코딩해서 전달하기 위한 API
//@RestController
//public class HelloController {
//
//    @GetMapping("/hello")
//    //컨트롤러 메서드의 지정한 파라미터들이 쿼리스트링으로 자동 추가
//    public String hello(String name){
//        return "Hello "+ name;  //바디에 전달되는 데이터
//    }
//    //작성한 API 테스트 방법
//    //: API의 경우엔 view를 응답하는 것이 아니라, 데이터를 응답한다.
//    //(1) 브라우저에서 요청
//    //: 요청과 응답에 대한 내부 처리에 대한 테스트가 불가능
//    //(2) 브라우저의 웹 개발자 도구 이용
//    //(3) postman, swagger UI와 같은 API 문서 작성 도구를 이용
//}

//컨트롤러의 역할
//(1) 유저의 요청 데이터의 유효성 검증
//(2) 검증된 요청을 비즈니스 로직으로 전달
//(3) 비즈니스 로직으로부터 돌려받은 결과를 유저가 원하는 형태로 가공해 응답한다.

//클래스 레벨에서 매핑 정보를 담고 있다 것을 명시하는 어노테이션
//@RequestMapping
////리턴하는 값이 뷰이름이 아니라 text/plain이라고 명시하는 어노테이션
//@ResponseBody
//@MyComponent
@RestController
public class HelloController {
    //재사용을 위해 변수로 선언 -> private final로 선언하면 재선언이 불가능 -> 생성자 파라미터로 받게 생성자 작성
    private final HelloService helloService;
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }
    @GetMapping("/hello")
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(String name){
//        return "Hello "+ name;  //바디에 전달되는 데이터
        //직접 서비스 객체 생성 -> 스프링 컨테이너가 HelloController 객체 생성시, 생성자 파라미터로 주입받도록 변경
//        SimpleHelloService helloService=new SimpleHelloService();
        //(1) 유효성 검증
        //기본적인 널체크 코드
//        if(name==null)
//        return helloService.sayHello(name);
        //유틸 클래스 Objects를 이용한 널체크 코드
        return helloService.sayHello(Objects.requireNonNull(name));
        //유틸 클래스 StringUtils를 이용한 널체크 코드
//        return helloService.sayHello(StringUtils.isEmpty(name)?"null":name);
    }

}