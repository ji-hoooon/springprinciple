package tobyspring.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

//EnableMyAutoConfiguration 어노테이션에 직접 하드 코딩해서 임포트하도록 설정한 부분을 변경
public class MyAutoConfigImportSelector implements DeferredImportSelector{
//public class MyAutoConfigImportSelector implements DeferredImportSelector, BeanClassLoaderAware {
    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //직접 클래스를 임포트하는 대신 클래스 이름을 명시하도록 변경
//        return new String[]{
//                "tobyspring.autoconfig.config.DispatcherServletConfig",
//                "tobyspring.autoconfig.config.TomcatWebServerConfig"
//        };

        //동적인 자동 구성 정보 등록을 위한 작업
        //(1) 자동 구성 대상 후보를 읽어온다.
        //option+enter로 로컬변수 생성
//        ImportCandidates candidates = ImportCandidates.load(MyAutoConfigImportSelector.class, classLoader);
        //ImportCandidates이 구현한 타입으로 변경
//        Iterable<String> candidates = ImportCandidates.load(MyAutoConfigImportSelector.class, classLoader);
        //애플리케이션의 클래스패스에서 리소스를 읽어올 경우, 클래스 로더를 사용한다.
        //-> 스프링 컨테이너가 빈을 생성하기 위해서 빈 클래스를 로딩할 때 클래스 로더를 사용하기 때문에 인자로 전달
        //-> 디스패처 서블릿이 애플리케이션 컨텍스트가 필요할때
        // ApplicationContextAware의 Setter 메서드로 컨테이너가 빈 초기화 하면서 빈 오브젝트 주입하는 방식과 유사하다.
        //여기서는 BeanClassLoaderAware의 Setter 메서드로 주입받는다. -> BeanClassLoaderAware를 구현

        //(2) 자동 구성 대상 후보를 원하는 타입으로 변환해서 리턴
        //String[] -> Iteralbe<String>으로 변환
//        return StreamSupport.stream(candidates.spliterator(),false).toArray(String[] :: new);


        List<String> autoConfigs = new ArrayList<>();
        //Iterable은 forEach에서 사용할 수 있기 때문에

        //자바5 EnhancedFor문
//        for(String candidate : ImportCandidates.load(MyAutoConfigImportSelector.class, classLoader)) {
//            autoConfigs.add(candidate);
//        }
        //자바8 forEach문
        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);

        //자바5부터 사용하는 고전적인 방법
//        return autoConfigs.toArray(new String[0]);
        //이후 스트림이 등장한 이후에 사용하는 방법 (typeSafety)
        return autoConfigs.stream().toArray(String[]::new);
        //Arrays 클래스의 메서드를 이용하는 방법
//        return Arrays.copyOf(autoConfigs.toArray(), autoConfigs.size(), String[].class);
    }
    //구현해서 Setter를 사용하는 대신 생성자를 이용해 주입
//    @Override
//    public void setBeanClassLoader(ClassLoader classLoader) {
//
//    }
}
