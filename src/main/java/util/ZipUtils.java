package util;

import java.io.*;

/**
 * Created by wangshunyao on 2017/5/4.
 */
public class ZipUtils {
    public static FileOutputStream fileOutputStream = null;

    public static void writeFiles(File file, byte[] data) {
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println(file.getName() + "没有找到该文件或目录");
        } catch (IOException e) {
            System.out.println(file.getName() + "IO异常");
        }
    }

    public static File buildUnPath(String path) {
        String[] pathDir = path.split("/");
        if (pathDir.length != 1) {
            new File(path.substring(0, path.lastIndexOf("/"))).mkdirs();
            if (path.contains("\\"))
                new File(path.substring(0, path.lastIndexOf("\\"))).mkdirs();
        }
        File file = new File(path);
        return file;
    }

    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
           // if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                byte[] b = new byte[1000];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                bos.close();
                fis.close();
           /* }else{
                for(File file1 :file.listFiles()){
                    getBytes(file1);
                }
            }*/
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static byte[] getBytes(SequenceInputStream sequenceInputStream) {
        byte[] buffer = null;
        try {
            byte[] b = new byte[1000];
            int n;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            while ((n = sequenceInputStream.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static boolean exitsFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }
}
