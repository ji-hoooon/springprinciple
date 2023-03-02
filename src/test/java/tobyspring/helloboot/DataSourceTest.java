package tobyspring.helloboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//단위 테스트가 아닌
//스프링 컨테이너를 띄워서 빈 구성정보를 넣고, 빈을 가져와서 테스트

//JUnit5을 확장하는 어노테이션
@ExtendWith(SpringExtension.class)
//빈들을 로딩할 정보를 가져오는 어노테이션 추가 모든 빈 구성정보를 읽어오도록 run메서드를 가진 클래스 전달
@ContextConfiguration(classes = HellobootApplication.class)
//외부설정을 위한 자동구성편에서도 얘기했듯, application.properties를 이용한 등록은
//스프링 부트 초기화 과정에서 추가해줘야한다.
@TestPropertySource("classpath:/application.properties")
public class DataSourceTest {
    //필드 주입을 위한 어노테이션
    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.close();
    }


}
