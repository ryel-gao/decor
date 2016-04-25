package com.bluemobi.decor.portal.util;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by reny on 2015/9/22.
 */
public class ZipUtil {
    /**
     * 解压zip格式的压缩包
     *
     * @param filePath 压缩文件路径
     * @param outPath  输出路径
     * @return 解压成功或失败标志
     */
    public static Boolean unZip(String filePath, String outPath) {
        String unzipfile = filePath; // 解压缩的文件名
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(unzipfile));
            ZipEntry entry;
            // 创建文件夹
            while ((entry = zin.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(outPath, entry.getName());
                    if (!directory.exists()) {
                        if (!directory.mkdirs()) {
                            System.exit(0);
                        }
                    }
                    zin.closeEntry();
                } else {
                    File myFile = new File(entry.getName());
                    FileOutputStream fout = new FileOutputStream(outPath
                            + myFile.getPath());
                    DataOutputStream dout = new DataOutputStream(fout);
                    byte[] b = new byte[1024];
                    int len = 0;
                    while ((len = zin.read(b)) != -1) {
                        dout.write(b, 0, len);
                    }
                    dout.close();
                    fout.close();
                    zin.closeEntry();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
