package compress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by wangshunyao on 2017/5/5.
 */
public class CompressZipEntry {
    //The second method
    private static void startZip(ZipOutputStream zos, String oppositePath, String directory) throws IOException {
        File file = new File(directory);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File aFile = files[i];
                if (aFile.isDirectory()) {
                    String newOppositePath = oppositePath + aFile.getName() + File.separator;
                    ZipEntry entry = new ZipEntry(oppositePath + aFile.getName() + File.separator);
                    zos.putNextEntry(entry);
                    zos.closeEntry();
                    startZip(zos, newOppositePath, aFile.getPath());
                } else {
                    zipFile(zos, oppositePath, aFile);
                }
            }
        } else {
            zipFile(zos, oppositePath, file);
        }
    }

    private static void zipFile(ZipOutputStream zos, String oppositePath, File file) {
        InputStream is = null;
        try {
            ZipEntry entry = new ZipEntry(oppositePath + file.getName());
            zos.putNextEntry(entry);
            is = new FileInputStream(file);
            int length = 0;
            int bufferSize = 10240;
            byte[] buffer = new byte[bufferSize];
            while ((length = is.read(buffer, 0, bufferSize)) >= 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void zipFiles(String source, String zipFilePath) {
        ZipOutputStream zos = null;

        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
            startZip(zos, "", source);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
