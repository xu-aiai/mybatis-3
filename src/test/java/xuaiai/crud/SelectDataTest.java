package xuaiai.crud;

import java.io.IOException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import xuaiai.DO.Student;
import xuaiai.connection.ConnectDB;
import xuaiai.connection.StudentMapper;

public class SelectDataTest {

  private static ConnectDB connectDB = new ConnectDB();
  private static String resource = "xuaiai/connection/mybatis-config.xml";

  @Test
  public void selectData() throws IOException {
    SqlSessionFactory sqlSessionFactory = connectDB.getSqlSessionFactory(resource);

    // 从 SqlSessionFactory 中获取 SqlSession
    try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
       StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
       List<Student> students = mapper.selectStudentInfo();
       students.stream().forEach(x -> System.out.println(x));
    }
  }

}
