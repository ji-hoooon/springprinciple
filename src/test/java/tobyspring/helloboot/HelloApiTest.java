package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

//지정한 포트를 이용해 서버를 띄우고 테스트하도록 하는 어노테이션
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HelloApiTest {
    @Test
    void helloApi(){
        //http localhost:8080/hello?name=Spring
        //HTTPie를 이용

        //요청을 보내기 위한 클래스 이용해 테스트
//        RestTemplate : 200번이 아닌 다른 상태코드를 반환할 경우 예외가 발생한다.
        //TestRestTemplate의 경우 응답자체를 반환하는 클래스로 테스트하기에 적합하다.
        TestRestTemplate rest = new TestRestTemplate();

        //쿼리스트링을 직접 작성할 경우
//        rest.getForEntity("http://localhost:8080/hello?name=Spring");
        //변수를 바꿔가며 테스트할 경우 - 치환자 이용
        //: url, 바인딩을 위한 바디의 응답 타입 (컨버터를 이용할 경우 컨버팅), 치환자에 들어갈 실제 파라미터
//        rest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");
        //웹 응답의 모든 요소를 가지고 있는 객체 타입으로 변수를 받기 위해 ResponseEntity 이용
        //: introduce local variable - option+enter
        ResponseEntity<String> res =
                rest.getForEntity("http://localhost:9090/app/hello?name={name}", String.class, "Spring");


        //응답 검증 3가지
        //: 계속해서 사용하는 AssertJ의 Assertions 객체를 static import (option+enter의 옵션 이용)
        //(1) 응답 코드
//        Assertions.assertThat(res.getStatusCode()).isEqualTo(200);
        //res.getStatusCode()의 타입이 Integer가 아니므로, 동작하지 않는다.

        //양쪽 리턴타입을 정확히 파악하고 비교를 수행해야한다.
        //: res.getStatusCode()의 타입은 Enum이므로, Enum과 비교를 수행
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        //(2) 헤더의 컨텐트 타입
        //: 같은 키를 가진 헤더가 여러개 있다고 가정하므로, getHeader()의 리턴타입은 List<>
//        Assertions.assertThat(res.getHeaders().getFirst("Content-Type"));
        //가급적이면 Enum으로 비교하는 것을 권장한다 - 에러 방지
        //: TEXT_PLAIN이 아닌 TEXT_PLAIN_VALUE와 비교를 해야한다. - HttpHeaders.CONENT_TYPE의 리턴타입이 String이기 때문에
//        Assertions.assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.TEXT_PLAIN_VALUE);
        //한글이외의 데이터의 경우 UTF-8이 아닌 인코딩 정보까지 딸려오기 때문에 테스트 실패 -> 앞의 정보를 가지고만 테스트하도록 변경
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);

        //(3) 바디의 내용
        assertThat(res.getBody()).isEqualTo("*Hello Spring*");
    }

    @Test
    void failHelloApi(){
        //http localhost:8080/hello?name=Spring
        //HTTPie를 이용

        //요청을 보내기 위한 클래스 이용해 테스트
//        RestTemplate : 200번이 아닌 다른 상태코드를 반환할 경우 예외가 발생한다.
        //TestRestTemplate의 경우 응답자체를 반환하는 클래스로 테스트하기에 적합하다.
        TestRestTemplate rest = new TestRestTemplate();

        //쿼리스트링을 직접 작성할 경우
//        rest.getForEntity("http://localhost:8080/hello?name=Spring");
        //변수를 바꿔가며 테스트할 경우 - 치환자 이용
        //: url, 바인딩을 위한 바디의 응답 타입 (컨버터를 이용할 경우 컨버팅), 치환자에 들어갈 실제 파라미터
//        rest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");
        //웹 응답의 모든 요소를 가지고 있는 객체 타입으로 변수를 받기 위해 ResponseEntity 이용
        //: introduce local variable - option+enter
        ResponseEntity<String> res =
                rest.getForEntity("http://localhost:9090/app/hello?name=", String.class);


        //응답 검증 3가지
        //: 계속해서 사용하는 AssertJ의 Assertions 객체를 static import (option+enter의 옵션 이용)
        //(1) 응답 코드
//        Assertions.assertThat(res.getStatusCode()).isEqualTo(200);
        //res.getStatusCode()의 타입이 Integer가 아니므로, 동작하지 않는다.

        //양쪽 리턴타입을 정확히 파악하고 비교를 수행해야한다. -> 현재 에러는 사용자 입력 에러이기 때문에
        // Controller에서 ResponseEntity를 통해 상태코드 변경을 해야한다.
        //: res.getStatusCode()의 타입은 Enum이므로, Enum과 비교를 수행
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        //(2) 헤더의 컨텐트 타입
        //: 같은 키를 가진 헤더가 여러개 있다고 가정하므로, getHeader()의 리턴타입은 List<>
//        Assertions.assertThat(res.getHeaders().getFirst("Content-Type"));
        //가급적이면 Enum으로 비교하는 것을 권장한다 - 에러 방지
        //: TEXT_PLAIN이 아닌 TEXT_PLAIN_VALUE와 비교를 해야한다. - HttpHeaders.CONENT_TYPE의 리턴타입이 String이기 때문에
//        Assertions.assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.TEXT_PLAIN_VALUE);
        //한글이외의 데이터의 경우 UTF-8이 아닌 인코딩 정보까지 딸려오기 때문에 테스트 실패 -> 앞의 정보를 가지고만 테스트하도록 변경
//        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);

        //(3) 바디의 내용 - 응답한 컨텐트 타입에 따라 실제 발생한 예외의 내용을 전달하는지 체크해야한다.
//        assertThat(res.getBody()).isEqualTo("Null");
//        assertThat(res.getBody()).isEqualTo("Empty");
    }
}
