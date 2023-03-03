package tobyspring.helloboot;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

//스테레오 타입 어노테이션
@Repository
public class HelloRepositoryJdbc implements HelloRepository {
    //생성자를 통해서 db 접근을 위한 의존성 주입
    private final JdbcTemplate jdbcTemplate;


    public HelloRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Hello findHelloOld(String name) {
        //단일 컬럼을 조회할 때 : 타입 지정해서 가져옴
        // 여러 개 칼럼을 조회할 때 : rowMapper 인터페이스를 구현한 클래스 or 람다식을 이용해 가져온다.
//        return jdbcTemplate.queryForObject("select * from hello where name='" + name + "'",
//                new RowMapper<Hello>() { //functional 인터페이스를 나타냄 -> option+enter를 이용해 lamda로 변환
//                    @Override
//                    public Hello mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        return null;
//                    }
//                });

        //조회결과가 하나일 때 리턴
        // 없으면 -> ??
        return jdbcTemplate.queryForObject("select * from hello where name='" + name + "'",
                (rs, rowNum) -> new Hello(
                        rs.getString("name"), rs.getInt("count")
                ));

    }

    @Override
    public Hello findHello(String name) {
        //단일 컬럼을 조회할 때 : 타입 지정해서 가져옴
        // 여러 개 칼럼을 조회할 때 : rowMapper 인터페이스를 구현한 클래스 or 람다식을 이용해 가져온다.
//        return jdbcTemplate.queryForObject("select * from hello where name='" + name + "'",
//                new RowMapper<Hello>() { //functional 인터페이스를 나타냄 -> option+enter를 이용해 lamda로 변환
//                    @Override
//                    public Hello mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        return null;
//                    }
//                });

        //조회결과가 하나일 때 리턴
        // 없으면 -> 예외처리
        try {
            return jdbcTemplate.queryForObject("select * from hello where name='" + name + "'",
                    (rs, rowNum) -> new Hello(
                            rs.getString("name"), rs.getInt("count")
                    ));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    @Override
    public void increaseCount(String name) {
        Hello hello =findHello(name);
        //update메서드를 통해 데이터를 조작하는 쿼리 수행
        if(hello==null) jdbcTemplate.update("insert into hello values(?, ?)", name, 1);
        else jdbcTemplate.update("update hello set count = ? where name=?", hello.getCount()+1, name);
    }

}
