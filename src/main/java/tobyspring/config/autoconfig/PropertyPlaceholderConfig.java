package tobyspring.config.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class PropertyPlaceholderConfig {
    @Bean
    //환경 객체로 추상화된 각종 프로퍼티 소스로부 치환자에 값을 지정해주는 스프링에서 제공하는 클래스
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
