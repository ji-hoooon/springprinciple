package tobyspring.helloboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//@HellobootTest
//DB 초기화 루틴을 수행하는 부트테스트 어노테이션
//: 빈을 모두 로딩하지만, 웹 환경 세팅 제외
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//모든 테스트를 다른 테스트에 영향없이 독립적으로 동작하게 하기위해
@Transactional
public class HelloRepositoryTest {
    //테스트를 위한 의존성 주입
    @Autowired
    HelloRepository helloRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //테스트 하기 전에 DB를 초기화하기 위한 어노테이션
    //: 인메모리 DB인 내장형 DB를 사용하므로, 애플리케이션 생명주기와 같다.
    //-> 빈 상태로 시작되는 DB에 데이터 초기화 작업 수행
//    @BeforeEach
//    void init(){
//        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
//    }

//    @Test
//    //찾는게 없을 때 null인지 테스트
//    //-> 실패 : queryForObject()메서드의 경우 없을 때 null이 아니라 예외발생
//    void findHelloFailedOld(){
//        assertThat(helloRepository.findHelloOld("Toby")).isNull();
//    }
    @Test
    //찾는게 없을 때 null인지 테스트
    //-> 실패 : queryForObject()메서드의 경우 없을 때 null이 아니라 예외발생
    //->  queryForObject()에 예외처리 수행코드 작성
    void findHelloFailed(){
        assertThat(helloRepository.findHello("Toby")).isNull();
    }

    @Test
    void increaseCount(){
        assertThat(helloRepository.countOf("Toby")).isEqualTo(0);

        helloRepository.increaseCount("Toby");
        assertThat(helloRepository.countOf("Toby")).isEqualTo(1);
        helloRepository.increaseCount("Toby");

        assertThat(helloRepository.countOf("Toby")).isEqualTo(2);
    }
}
