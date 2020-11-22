package xuaiai.connection;

import java.util.List;
import xuaiai.DO.Student;

/**
 * 接口映射器
 * 映射器是一些绑定映射语句的接口。
 * 一个 Sql 语句 既可以通过 xml 定义，也可以通过注解定义。
 * MyBatis 提供的所有的特性都可以利用基于 XML 的映射语言来实现。
 * 一个 XML 文件中，可以定义无数个映射语句。
 */
public interface StudentMapper {

  Integer insertData(Student student);

  List<Student> selectStudentInfo();
}
