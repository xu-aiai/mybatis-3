package xuaiai.crud;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Date;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import xuaiai.DO.Student;
import xuaiai.connection.ConnectDB;
import xuaiai.connection.StudentMapper;


public class InsertDataTest {

  private static ConnectDB connectDB = new ConnectDB();
  private static String resource = "xuaiai/connection/mybatis-config.xml";

  /**
   * 单元测试的时候，方法不能带参数，不能有返回值
   * @throws IOException
   */
  @Test
  public void insertData() throws IOException {

    Student student1 = new Student();
    student1.setSId("182235020");
    student1.setSName("许浩东");
    student1.setSAge(new Date(19951016));
    student1.setSSex("1");

    Student student2 = new Student();
    student2.setSId("182235011");
    student2.setSName("王凌志");
    student2.setSAge(new Date(19941220));
    student2.setSSex("1");

    SqlSessionFactory sqlSessionFactory = connectDB.getSqlSessionFactory(resource);

    /**
     * 从 SqlSessionFactory 中获取 SqlSession
     * 通过 SqlSession，我们可以通过这个接口来执行命令，获取映射器示例和管理事务。
     * 默认的 openSession() 方法没有参数，它创建出来的 SqlSession 具有特性如下：
     *  ①事务作用域将会开启，不自动提交事务
     *  ②将由当前环境配置的 DataSource 实例中获取 Connection 对象。
     *  ③事务隔离级别将会使用驱动或者数据源的默认配置
     *  ④预处理语句不会被复用，也不会批量处理更新。
     */
    try(SqlSession sqlSession = sqlSessionFactory.openSession()) {

      // ① 通过 XML 映射器文件的全限定名来调用映射语句
      Integer insertCount1 = sqlSession.insert(
        "xuaiai.connection.StudentMapper.insertData", student1);
      System.out.println(JSON.toJSONString(insertCount1));

      // ② 从 SqlSession 中获取映射器接口的实例，并将映射的 SQL 语句匹配到对应的名称，参数和返回类型的方法上。
       StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
       Integer insertCount2 = mapper.insertData(student2);
       System.out.println(insertCount2);

       sqlSession.commit();
    }
  }
}
