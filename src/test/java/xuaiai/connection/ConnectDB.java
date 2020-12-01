package xuaiai.connection;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * 每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。
 *
 * SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得。
 * 而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或
 * 一个预先配置的 Configuration 实例来构建出 SqlSessionFactory 实例。
 * 因此 sqlSessionFactory 有两种构建方式：
 *
 * ①使用xml创建 SqlSessionFactory ② 不使用 xml 创建SqlSessionFactory。
 * @author: 啦儿啦
 * @date: 20201119
 * @description: 创建 SqlSessionFactory 的两种方法
 */
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

  /**
   * 使用 xml 创建 SqlSessionFactory
   * @param resource: xml 文件路径
   * @return: SqlSessionFactory 实例
   */
  public SqlSessionFactory getSqlSessionFactory(String resource) throws IOException {
    Properties properties = new Properties();
    properties.setProperty("111Name","111Value");
    properties.setProperty("222Name","222Value");
    properties.setProperty("333Name","333Value");
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
    return sqlSessionFactory;
  }

}
