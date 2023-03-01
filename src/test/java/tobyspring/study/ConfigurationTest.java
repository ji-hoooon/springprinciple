package tobyspring.study;

import net.bytebuddy.build.ToStringPlugin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationTest {
    //configuration 클래스의 특징은 빈 어노테이션이 붙은 메서드를 가지고 있어서,
    //자바 코드에 의해 빈 오브젝트를 생성하고, 다른 오브젝트와의 관계를 설정한다.
    @Test
    void configuration1(){
        //isSameAs의 경우 주소값까지 똑같지 않은지 비교하는 메서드
        assertThat(new Common()).isNotSameAs(new Common());

        //MyConfig을 이용해 비교할 경우 common은 서로 다른 오브젝트
        MyConfig myConfig = new MyConfig();
        Bean1 bean1=myConfig.bean1();
        Bean2 bean2=myConfig.bean2();

        assertThat(bean1.common).isNotSameAs(bean2.common);
    }
    @Test
    void configuration2(){

        //MyConfig 클래스를 스프링 컨테이너의 구성정보를 사용할 경우 common은 서로 정확하게 일치하는 오브젝트
        //구성정보 클래스를 스프링 컨테이너에 등록하는 과정
        //(1) 스프링 컨테이너 생성
        //(2) 스프링 컨테이너에 구성정보 클래스 정보를 등록
        //(3) refresh로 초기화
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        //등록한 구성정보를 이용해 빈으로 등록한 빈을 얻어서 비교
        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);
        assertThat(bean1.common).isSameAs(bean2.common);
    }

    //Bean1 -> Common, Bean2 -> Common
    @Configuration
    static class MyConfig{
        @Bean
        Common common(){
            return new Common();
        };

        @Bean
        Bean1 bean1(){
            return new Bean1(common());
        }
        @Bean
        Bean2 bean2(){
            return new Bean2(common());
        }
        //-> common 팩토리 메서드는 두 번 실행 되므로, 서로 다른 common 오브젝트가 2개 생성
    }

    static class Common{

    }

    static class Bean1{
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }
    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    //프록시패턴을 이용해서 접근방식을 제어한다.
    //: 기존의 클래스를 이용해 확장을 통해 해당 클래스를 대체하는 목적
    static class MyConfigProxy extends MyConfig{
        private Common common;
        @Override
        Common common() {
            //필드인 common이 널인지 체크하고, 널이면 조상클래스의 생성자를 이용해 생성한다.
            if(this.common==null)
                this.common=super.common();

            //필드인 common이 널이 아니면, 해당 필드를 그대로 리턴한다.
            return this.common;
        }
    }

    //프록시패턴을 이용한다면, 생성자를 두 번 호출한다고 해도 같은 객체를 리턴하게 된다.
    //: null이 아니면 해당 필드를 리턴하고, null이면 생성자를 이용해 초기화하기 때문에
    @Test
    void proxyCommonMethod(){
        MyConfigProxy myConfigProxy = new MyConfigProxy();

        //등록한 구성정보를 이용해 빈으로 등록한 빈을 얻어서 비교
        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();
        assertThat(bean1.common).isSameAs(bean2.common);
    }
}
