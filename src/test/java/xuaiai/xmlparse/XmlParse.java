package xuaiai.xmlparse;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


@Log4j2
public class XmlParse {

  public void parseTest1(String filePath) throws ParserConfigurationException, IOException, SAXException {
    File file = new File(filePath);
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.parse(file);
    NodeList nodeList = document.getElementsByTagName("action");
    for (int i = 0, n = nodeList.getLength(); i < n; i++) {
      System.out.println("result:" + document.getElementsByTagName("result").item(i).getFirstChild().getNodeName());
      System.out.println("result:" + document.getElementsByTagName("result").item(i).getFirstChild().getNodeValue());
    }
  }


  public void parseTest2(String filePath) {
    // 获取 DOM 解析器的工厂实例
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    try {
      // 从 DOM 工厂中获取 DOM 解析器
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      // 把需要解析的 xml 文档读入 DOM 解析器
      Document document = documentBuilder.parse(filePath);
      System.out.println("处理该文档的 DomImplementation 对象 =" + document.getImplementation());
      System.out.println("========================================");
      // 得到文档名称为 Student 的元素的节点列表
      NodeList studentNodeList = document.getElementsByTagName("Student");
      // 遍历该集合，获取集合中的元素及其子元素的指
      for (int i = 0, n = studentNodeList.getLength(); i < n; i++) {
        Element item = (Element) studentNodeList.item(i);
        System.out.println("Name: " + item.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue());
        System.out.println("Num: " + item.getElementsByTagName("Num").item(0).getFirstChild().getNodeValue());
        System.out.println("Classes: " + item.getElementsByTagName("Classes").item(0).getFirstChild().getNodeValue());
        System.out.println("Address: " + item.getElementsByTagName("Address").item(0).getFirstChild().getNodeValue());
        System.out.println("Tei: " + item.getElementsByTagName("Tel").item(0).getFirstChild().getNodeValue());
        System.out.println("========================================");
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
  }

 /**
  * @param filePath: xml 路径
  * @return: void
  * @author: 啦儿啦
  * @date:
  * @description:
  * Java 使用 DOM 解析 XML 文档，获取文档中所有节点的属性值以及子节点值。
  * ① Element 类型节点的 getNodeValue() 值为空，Text 类型节点的 getNodeValue() 值为文本内容
  * ② 一个节点中如：<1></1> 中一共有一个子节点; <1><2></2></1>一共有三个节点; 依次类推，<1><2></2>...<n></n></1>....节点的子节点个数为 2n + 1。
  */
  public void parseTest3(String filePath) {
    // 获取 DOM 解析器的工厂实例
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    try {
      // 从 DOM 工厂中获取 DOM 解析器
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      // 把需要解析的 xml 文档读入 DOM 解析器
      Document document = documentBuilder.parse(filePath);
      System.out.println("处理该文档的 DomImplementation 对象 =" + document.getImplementation());
      System.out.println("========================================");
      // 得到文档名称为 Student 的元素的节点列表
      NodeList studentNodeList = document.getElementsByTagName("Student");
      System.out.println("一共有：" + studentNodeList.getLength() + "名学生");
      // 遍历该集合，获取集合中的元素及其子元素的指
      for (int i = 0, n = studentNodeList.getLength(); i < n; i++) {
        System.out.println("第" + (i + 1) + "学生的信息");
        Element item = (Element) studentNodeList.item(i);
        System.out.println("第一个属性节点值:" + item.getFirstChild().getNodeValue());
        // 获取节点的所有属性集合
        NamedNodeMap attributes = item.getAttributes();
        // 遍历节点所有的属性
        System.out.println("第" + (i + 1) + "学生共有" + attributes.getLength() + "个属性");
        for (int j = 0; j < attributes.getLength(); j++) {
          Node attr = attributes.item(j);
          System.out.println("属性名：" + attr.getNodeName());
          System.out.println("属性值：" + attr.getNodeValue());
        }
        NodeList childNodes = item.getChildNodes();
        System.out.println("第" + (i + 1) + "学生共有" + childNodes.getLength() + "个子节点");
        for (int k = 0; k < childNodes.getLength(); k ++) {
          Node child = childNodes.item(k);
          // 区分出 text 类型的 node 以及 element 类型的 node
          if (child.getNodeType() == Node.ELEMENT_NODE) {
            // 获取 element 类型节点的节点名
            System.out.println("第" + (k + 1) + "个节点的节点名: " + child.getNodeName());
            // 获取 element 类型节点的节点值
            System.out.println("第" + (k + 1) + "个节点的节点值: " + child.getFirstChild().getNodeValue());
          }
          if (child.getNodeType() == Node.TEXT_NODE) {
            // 获取 element 类型节点的节点名
            System.out.println("第" + (k + 1) + "个节点的节点名: " + child.getNodeName());
            // 获取 element 类型节点的节点值
            System.out.println("第" + (k + 1) + "个节点的节点值: " + child.getNodeValue());
          }
        }
        System.out.println("========================================");
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
  }

}
