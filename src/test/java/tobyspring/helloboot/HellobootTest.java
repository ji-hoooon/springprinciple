package tobyspring.helloboot;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//테스트를 위한 합성 어노테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

//JUnit5을 확장하는 어노테이션
@ExtendWith(SpringExtension.class)
//빈들을 로딩할 정보를 가져오는 어노테이션 추가 모든 빈 구성정보를 읽어오도록 run메서드를 가진 클래스 전달
@ContextConfiguration(classes = HellobootApplication.class)
//외부설정을 위한 자동구성편에서도 얘기했듯, application.properties를 이용한 등록은
//스프링 부트 초기화 과정에서 추가해줘야한다.
@TestPropertySource("classpath:/application.properties")
//새로운 트랜잭션 시작하게 하는 선언형 어노테이션
@Transactional
public @interface HellobootTest {
}
