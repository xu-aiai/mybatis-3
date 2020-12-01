package xuaiai.xmlparse;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author: 啦儿啦
 * @date: 2020/11/28 21:39
 * @description:
 * Java DOM 解析 xml 的 Demo
 * 参考文档：
 * ① https://cloud.tencent.com/developer/article/1386061
 * ② http://www.51gjie.com/java/736.html
 */
public class XmlParseTest {

  @Test
  public void parseTest1Test() throws IOException, SAXException, ParserConfigurationException {
    String filePath = "D:\\repository\\Java\\mybatis-3\\src\\test\\java\\xuaiai\\xmlparse\\test1.xml";
    XmlParse xmlParse = new XmlParse();
    xmlParse.parseTest1(filePath);
  }

  @Test
  public void parseTest2Test() {
    String filePath = "D:\\repository\\Java\\mybatis-3\\src\\test\\java\\xuaiai\\xmlparse\\test2.xml";
    XmlParse xmlParse = new XmlParse();
    xmlParse.parseTest2(filePath);
  }

  @Test
  public void parseTest3Test() {
    String filePath = "D:\\repository\\Java\\mybatis-3\\src\\test\\java\\xuaiai\\xmlparse\\test2.xml";
    XmlParse xmlParse = new XmlParse();
    xmlParse.parseTest3(filePath);
  }

}
