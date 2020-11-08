package xuaiai.connection;


import java.io.IOException;
import java.io.InputStream;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class ConnectDB {

  /**
   * 不使用 xml 获取 SqlSessionFactory
   * @param driverName 驱动名称
   * @param url 连接地址
   * @param user 数据库用户名
   * @param password 数据库密码
   * @return SqlSessionFactory 实例
   */
  public SqlSessionFactory getSqlSessionFactory(String driverName, String url, String user, String password) {
    // 1. 创建数据源
    DataSource dataSource = new PooledDataSource(driverName, url, url, password);
    // 2. 创建事务
    JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();
    // 3. 创建环境
    Environment development = new Environment("development", transactionFactory, dataSource);
    // 4. 创建配置
    Configuration configuration = new Configuration(development);
    // 5. 添加 mapper
    configuration.addMapper(StudentMapper.class);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    return sqlSessionFactory;
  }

  public SqlSessionFactory getSqlSessionFactory(String resource) throws IOException {
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    return sqlSessionFactory;
  }

}
