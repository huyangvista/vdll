package vdll.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Hocean on 2017/2/10.
 */
public class BaseXmlFragment {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        get();
    }
    public static void get() throws ParserConfigurationException, IOException, SAXException {
        //1.创建解析工厂：
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.指定DocumentBuilder
        DocumentBuilder db = factory.newDocumentBuilder();
        //3.从文件构造一个Document,因为XML文件中已经指定了编码，所以这里不必了
        Document xmlDoc = db.parse(new File("src/asses/fragment.txt"));

        // 上面三步可以得到映射了指定的XML文件的Document,之后，通过这个Document可以来操作XML。

        //得到Document的根
        Element root = xmlDoc.getDocumentElement();

        //得到根后通过getTagName可以得到根节点名及其他一系列操作。

        //一.解析
        //获得XML某个元素的值：
        NodeList list = root.getElementsByTagName("item");
        //NodeList对象存储的是指定元素的值的列表，
        //我们可以通过遍历来得到指定元素的各个值：
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i); //得到"page"的第i+1组标签
            String id = element.getAttribute("name");  //获得ID属性

            System.out.println(id);
//            //得到标签title的列表
//            NodeList titleList = element.getElementsByTagName("val");
//            //得到"title"的第1组标签，事实上也只有一组标签
//            Element titleElement = (Element) titleList.item(0);
//            //获得title元素的第一个值
//            String title = titleElement.getFirstChild().getNodeValue();
        }
    }
}
