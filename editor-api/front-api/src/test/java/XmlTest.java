import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;

/**
 * @program: odf-editor-system
 * @description:
 * @author: xjr
 * @create: 2020-08-03 18:26
 **/

public class XmlTest {

    public static void main(String[] args) {
        Document document= DocumentHelper.createDocument();
        document.addComment("nihao");
        Element element=document.addElement("OdfBody");
        element.setText("nihao");

        element.addAttribute("CompetitionCode","${odfBody.competitionCode}");
        writeXmlFile(document,"d:/test.xml");

    }

    /**
     Document dom = DocumentHelper.createDocument();//添加节点用addElement，添加节点属性用addAttribute,未节点赋值用setText
     */
    public static void writeXmlFile(Document dom,String fileurl){
        //设置生成xml格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码格式
        format.setEncoding("UTF-8");
        File file = new File(fileurl);
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream(file),format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.setEscapeText(false); //关闭字符串中xml特殊字符转义
        try {
            writer.write(dom);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
