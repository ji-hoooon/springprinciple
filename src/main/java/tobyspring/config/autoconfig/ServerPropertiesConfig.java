package tobyspring.config.autoconfig;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tobyspring.config.MyAutoConfiguration;

//프로퍼티 클래스가 늘어날 때마다 Config 클래스 작성해야하는 단점 존재
//@MyAutoConfiguration
public class ServerPropertiesConfig {
    //파라미터를 담은 클래스를 빈으로 등록하는 팩토리 메소드
    @Bean
    //Environment 객체를 파라미터로 전달해 하나씩 해당 객체에서 프로퍼티를 가져온다.
    public ServerProperties oldServerProperties(Environment env){
        ServerProperties properties = new ServerProperties();

        properties.setContextPath(env.getProperty("contextPath"));
        properties.setPort(Integer.valueOf(env.getProperty("port")));
        //기본값 설정 방법?

        return properties;
    }
    @Bean
    //Environment 객체를 파라미터로 전달해 binder 클래스로 프로퍼티들을 바이딩.
    public ServerProperties serverProperties(Environment env){
        return Binder.get(env).bind("", ServerProperties.class).get();
    }
}
