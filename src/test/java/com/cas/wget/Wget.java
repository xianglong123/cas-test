package com.cas.wget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/3/25 5:42 下午
 * @desc
 */
public class Wget {


    public static void main(String[] args) {

        try {
            String cmdString = "/usr/bin/wget http://211.138.236.210:12580/ccaweb/jsp/mca/appRecomLstMng/images/20220308165330.jpg";
            System.out.println(cmdString);
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmdString);
            p.waitFor();
            p.getOutputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String s;
            while ((s = r.readLine()) != null) {
                System.out.println(s);
            }
            r.close();
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
    }
}
