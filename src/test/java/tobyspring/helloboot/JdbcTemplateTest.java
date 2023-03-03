package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

@HellobootTest
//롤백 되는게 맞는지 확인하기 위한 어노테이션
//@Rollback(value = false)
public class JdbcTemplateTest {
    //테스트를 위한 의존성 주입
    @Autowired
    JdbcTemplate jdbcTemplate;

    //테스트 하기 전에 DB를 초기화하기 위한 어노테이션
    //: 인메모리 DB인 내장형 DB를 사용하므로, 애플리케이션 생명주기와 같다.
    //-> 빈 상태로 시작되는 DB에 데이터 초기화 작업 수행
    @BeforeEach
    void init(){
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

    @Test
    void insertAndQuery(){
        jdbcTemplate.update("insert into hello values (?,?)", "Toby", 3);
        jdbcTemplate.update("insert into hello values (?,?)", "Spring", 1);

        Long count=jdbcTemplate.queryForObject("select count(*) from hello", Long.class);

        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void insertAndQuery2(){
        jdbcTemplate.update("insert into hello values (?,?)", "Toby", 3);
        jdbcTemplate.update("insert into hello values (?,?)", "Spring", 1);

        Long count=jdbcTemplate.queryForObject("select count(*) from hello", Long.class);

        Assertions.assertThat(count).isEqualTo(2);
    }
}
