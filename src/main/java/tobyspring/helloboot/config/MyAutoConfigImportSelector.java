package tobyspring.helloboot.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//EnableMyAutoConfiguration 어노테이션에 직접 하드 코딩해서 임포트하도록 설정한 부분을 변경
public class MyAutoConfigImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "tobyspring.helloboot.config.autoconfig.DispatcherServletConfig",
                "tobyspring.helloboot.config.autoconfig.TomcatWebServerConfig"
        };
    }
}
