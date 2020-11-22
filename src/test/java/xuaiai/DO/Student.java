package xuaiai.DO;

import java.util.Date;
import lombok.Data;

@Data
public class Student {

  /**
   * 学生学号
   */
  private String sId;
  /**
   * 学生姓名
   */
  private String sName;
  /**
   * 学生年龄
   */
  private Date sAge;
  /**
   * 学生性别
   */
  private String sSex;
}

