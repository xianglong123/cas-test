package com.cas.odf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Ofd {

    public static void main(String[] args) throws Exception {

        String zipfile_dir = "/Users/xianglong/Desktop/OFD研究/ofd文件/系统架构图.ofd";//根据实际调用的文件修改
        try {
            readZipFile(zipfile_dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readZipFile(String file) throws Exception {

        readZipFile(new File(file));
    }

    //根据ofd的压缩格式解包
    private static void readZipFile(File file) throws Exception {
        ZipFile zf = new ZipFile(file, StandardCharsets.UTF_8);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zis = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                //解包读取相关文件信息
                if (ze.getName().endsWith("OFD.xml")) {
                    // System.err.println("file - " + ze.getName() + " : " + ze.getSize() + " bytes");
                    if (ze.getSize() > 0) {
                        File new_xml_file = null;
                        BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), StandardCharsets.UTF_8));
                        if ((ze.getName().trim().lastIndexOf("/")) != -1) {
                            new_xml_file = new File(ze.getName().substring(ze.getName().trim().lastIndexOf("/") + 1));
                            System.out.println(new_xml_file);
                        } else {
                            new_xml_file = new File(ze.getName());
                        }
                        // 读取指定的文件流并创建临时文件
                        FileOutputStream out = new FileOutputStream(new_xml_file);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                        String line;
                        while ((line = br.readLine()) != null) {
                        // System.out.println(line);
                            bw.write(line);
                        }
                        br.close();
                        bw.close();
                        // 定义xml文件识别器并输出指定节点的数据
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(new_xml_file);
                        NodeList cmdList = document.getElementsByTagName("ofd:CustomData");
                        for (int i = 0; i < cmdList.getLength(); i++) {
                            Node name = cmdList.item(i);
                            Element ele = (Element) name;
                            System.out.println(ele.getAttribute("Name") + ":" + name.getTextContent());
                        }
                        // 删除临时文件
                        new_xml_file.delete();
                    }
                }
            }
        }
        zis.closeEntry();
        zis.close();
        zf.close();
    }
}