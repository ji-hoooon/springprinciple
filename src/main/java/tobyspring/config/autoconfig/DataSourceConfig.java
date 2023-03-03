package tobyspring.config.autoconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.EnableMyConfigurationProperties;
import tobyspring.config.MyAutoConfiguration;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Driver;

//자동 구성을 위한 어노테이션
@MyAutoConfiguration
//JdbcOperations 클래스가 존재하는지 체크
//: spring-jdbc 라이브러리에 존재하는 클래스
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
//자동 구성 클래스가 @Conditional 조건이 충족할 때만 프로퍼티 파일이 빈으로 등록될 수 있도록 하는 어노테이션
@EnableMyConfigurationProperties(MyDataSourceProperties.class)
//@Import를 메타 어노테이션하는 어노테이션으로
//구성정보를 가진 클래스를 임포트하는 역할 수행
@EnableTransactionManagement
public class DataSourceConfig {
    //Hikari 관련 클래스 존재할 때만 사용하도록 변경
    @Bean
    @ConditionalMyOnClass("com.zaxxer.hikari.HikariDataSource")
    //커스텀 빈을 만들 경우를 위해 해당 빈을 못찾을 경우도 추가해둔다.
    @ConditionalOnMissingBean
    DataSource hikariDataSource(MyDataSourceProperties properties){
//        implementation('com.zaxxer:HikariCP')
        HikariDataSource dataSource = new HikariDataSource();

        //Hikari의 DataSource의 경우 클래스 이름으로 전달 가능
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        return dataSource;
    }


    //빈 생성
    @Bean
    @ConditionalOnMissingBean
    DataSource dataSource(MyDataSourceProperties properties) throws ClassNotFoundException {
        //커넥션 풀이 존재하지 않아 매번 커넥션 객체를 생성한다.
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        //프로퍼티 클래스를 만들어서 DB연결에 필요한 프로퍼티를 제공하는 방식으로 설정
        //:DriverClass()에 클래스 이름을 문자열로 전달하고 싶지만, 클래스 타입으로 받기 때문에
        //클래스 이름으로 클래스 오브젝트를 가져오는 Class.forName() 메서드 이용
//        dataSource.setDriverClass(Class.forName(properties.getDriverClassName()));
        //:예외처리가 필요한 메서드
//        dataSource.setDriverClass(Class.forName(properties.getDriverClassName()));
        //: 타입을 드라이버 타입의 Class 타입으로 캐스팅
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(properties.getDriverClassName()));
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        return dataSource;
    }

    @Bean
    //파라미터로 데이터 소스를 받는다.
    //커스텀 빈을 만들 경우를 위해 해당 빈을 못찾을 경우도 추가해둔다.
    @ConditionalOnMissingBean
    //스프링이 제공하는 어노테이션으로
    //해당 빈 메서드 실행시,
    // 스프링 컨테이너의 빈 구성정보 파라미터로 전달한 타입의 빈이 한개만 등록되어있을 경우 해당 빈을 가져와서 사용한다.
    @ConditionalOnSingleCandidate(DataSource.class)
    JdbcTemplate jdbcTemplate(DataSource dataSource){
        //복잡한 기능을 위해서는 프로퍼티 클래스를 추가해 설정해도 되지만, 일반적으로는 오브젝트 만드는 것으로 충분
        return new JdbcTemplate(dataSource);
    }
    @Bean
    //파라미터로 데이터 소스를 받는다.
    //커스텀 빈을 만들 경우를 위해 해당 빈을 못찾을 경우도 추가해둔다.
    @ConditionalOnMissingBean
    //스프링이 제공하는 어노테이션으로
    //해당 빈 메서드 실행시,
    // 스프링 컨테이너의 빈 구성정보 파라미터로 전달한 타입의 빈이 한개만 등록되어있을 경우 해당 빈을 가져와서 사용한다.
    @ConditionalOnSingleCandidate(DataSource.class)
    //일반적으로 @Transactional이라는 어노테이션을 이용해서 선언적인 방법으로 사용하는 빈으로,
    //직접 사용할 경우 PlatformTransactionManager라는 인터페이스로 주입해서 사용한다.
    JdbcTransactionManager jdbcTransactionManager(DataSource dataSource){
        //복잡한 기능을 위해서는 프로퍼티 클래스를 추가해 설정해도 되지만, 일반적으로는 오브젝트 만드는 것으로 충분
        return new JdbcTransactionManager(dataSource);
    }

}
