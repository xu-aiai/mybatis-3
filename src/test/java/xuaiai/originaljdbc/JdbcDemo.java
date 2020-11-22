package xuaiai.originaljdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/**
 * JDBC:Java Databse Connectivity, Java 数据库连接
 * JDBC 允许用户访问任何形式的表格数据，尤其是存储在关系数据库中的数据。
 * 执行流程：
 *  ①连接数据源，如：数据库
 *  ②为数据库传递查询和更新指令
 *  ③处理数据库响应并返回结果集
 */
public class JdbcDemo {

  public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  public static final String URL = "jdbc:mysql://112.124.13.202:3306/test";
  public static final String USER = "root";
  public static final String PASSWORD = "950416.tj";

  @Test
  public void test() throws ClassNotFoundException, SQLException {
    // 加载驱动程序
    Class.forName(DRIVER);
    // 获取数据库连接
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    /**
     * 创建 Statement/PreparedStatement 对象， 操作数据库，实现 CRUD
     * Statement 会使数据库频繁编译 SQL，可能造成数据库缓冲区溢出。
     * PreparedStatement 可对 SQL 进行预编译，从而提高数据库的执行效率。
     * PreparedStatement 对于 SQL 中的参数，允许使用占位符的形式进行替换，简化 SQL 语句的编写。
     */

    Statement statement = connection.createStatement();
    statement.executeQuery("select s_id, s_name from Student");
    PreparedStatement preparedStatement = connection
      .prepareStatement("select s_id, s_name from Student where s_id = ?");
    // 设置参数
    preparedStatement.setString(1, "01");
    ResultSet resultSet = preparedStatement.executeQuery();
    // 如果有数据， rs.next 会返回 true
    while(resultSet.next()) {
      System.out.println("学号：" + resultSet.getString("S_id"));
      System.out.println("姓名：" + resultSet.getString("S_name"));
    }
  }
}
