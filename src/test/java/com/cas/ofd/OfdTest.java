//package com.cas.ofd;
//
//import org.dom4j.*;
//import org.dom4j.tree.DefaultElement;
//import org.junit.Test;
//import org.ofdrw.layout.OFDDoc;
//import org.ofdrw.layout.element.Paragraph;
//import org.ofdrw.reader.OFDReader;
//import org.ofdrw.reader.ResourceManage;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class OfdTest {
//
//    @Test
//    public void createOfd() {
//            Path path = Paths.get("/Users/xianglong/IdeaProjects/cas-test2/HelloWorld.ofd");
//            try (OFDDoc ofdDoc = new OFDDoc(path)) {
//                Paragraph p = new Paragraph("你好呀，OFD Reader&Writer！");
//                ofdDoc.add(p);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        System.out.println("生成文档位置: " + path.toAbsolutePath());
//    }
//
//    @Test
//    public void analysisOfd() throws IOException, DocumentException {
//        OFDReader reader = new OFDReader("/Users/xianglong/IdeaProjects/cas-test2/src/test/java/com/cas/ofd/test.ofd");
//        ResourceManage rm = new ResourceManage(reader);
//        // 根据分页数量自行修改
//        Document document = reader.getPage(1).getDocument();
//        List<Node> nodes = document.selectNodes("//ofd:PageBlock");
//        for (Node node : nodes) {
//            String xmlText = node.asXML();
//            Element element = DocumentHelper.parseText(xmlText).getRootElement();
//            // element.content().get(8).getStringValue()
//            List<Node> content = element.content();
//            for (Node c : content) {
//                if (c instanceof DefaultElement) {
//                    String stringValue = c.getStringValue();
//                    System.out.println("ID：" + ((DefaultElement)c).attribute("ID").getValue() + "  长度(UTF-8)：" + stringValue.getBytes(StandardCharsets.UTF_8).length + "   值：" + stringValue);
//                }
//            }
//            System.out.println("aa");
//        }
//    }
//
//}
