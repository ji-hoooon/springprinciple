package tobyspring.config.autoconfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import tobyspring.config.MyAutoConfiguration;
import tobyspring.config.MyConfigurationProperties;

@MyAutoConfiguration
public class PropertyPostProcessorConfig {
    @Bean
    //익명 클래스를 이용해 postProcessAfterInitialization 오버라이딩
    //: 모든 빈 오브젝트 초기화 끝난 다음에 수행하는 프로세스
    BeanPostProcessor propertyPostProcessor(Environment env){
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                //@MyConfigurationProperties 붙은 클래스에서만 수행하는 메서드
                //: 리플렉션 API를 이용해 특정 어노테이션이 붙은 클래스를 찾아주는 유틸리티 클래스인 AnnotationUtils의 findAnnotation 메서드를 이용
                MyConfigurationProperties annotation = AnnotationUtils.findAnnotation(bean.getClass(), MyConfigurationProperties.class);
                //해당 어노테이션이 아니면 바로 리턴
                if(annotation==null) return bean;

                //해당 어노테이션이 붙은 클래스는 모두 프로퍼티 주입이 필요한 클래스이므로 프로퍼티 주입 수행


                //바인딩시 예외처리까지 수행하는 메서드 :bindOrCreate() 바인드하려고 했는데 존재하지 않으면 새로 만들어서 리턴
                return Binder.get(env).bindOrCreate("", bean.getClass());
            }
        };
    }
}
