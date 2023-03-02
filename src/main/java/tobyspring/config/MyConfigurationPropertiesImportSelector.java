package tobyspring.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

//실제로 임포트할 클래스 이름을 리턴해주는 클래스
public class MyConfigurationPropertiesImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //EnableMyConfigurationProperties의 어노테이션 속성중 value에서 가져온다.
        MultiValueMap<String, Object> attrs = importingClassMetadata.getAllAnnotationAttributes(EnableMyConfigurationProperties.class.getName());
        //리스트로 가져오기 때문에 하나만 가져오도록 getFirst()메서드로 가져와 클래스 타입으로 가져오도록 캐스팅한다.
        Class propertyClass = (Class) attrs.getFirst("value");
        //클래스 타입의 이름을 리턴
        return new String[]{propertyClass.getName()};
    }
}
