package xuaiai.connection;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConnectDBTest {

  private static ConnectDB connectDB = new ConnectDB();

  private static SqlSessionFactory sqlSessionFactoryManualCreate;
  private static SqlSessionFactory sqlSessionFactoryXMLCreate;

  private static String resource = "xuaiai/connection/mybatis-config.xml";

  private static String driverName = "com.mysql.jdbc.Driver";
  private static String url = "jdbc:mysql://112.124.13.202:3306/test";
  private static String user = "root";
  private static String password = "950416.tj";

  @BeforeAll
  public static void setUp() throws IOException {
    sqlSessionFactoryManualCreate = connectDB.getSqlSessionFactory(driverName, url, user, password);
    sqlSessionFactoryXMLCreate = connectDB.getSqlSessionFactory(resource);
  }

  @Test
  public void connectDBTest() {
    System.out.println(sqlSessionFactoryManualCreate.toString());
    System.out.println(sqlSessionFactoryXMLCreate.toString());
  }
}
