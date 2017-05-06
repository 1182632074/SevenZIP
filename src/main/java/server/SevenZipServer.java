package server;

import compress.Zip7CompressCallBack;
import compress.CompressZipEntry;
import entity.Item;
import extact.Zip7ExtractCallback;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.impl.RandomAccessFileOutStream;
import org.apache.log4j.Logger;
import util.CompressOutItemStructure;

import static util.ZipUtils.*;

import java.io.*;

/**
 * Created by wangshunyao on 2017/5/4.
 */
public class SevenZipServer {
    Logger logger = Logger.getLogger(SevenZipServer.class);

    /**
     *This method USES zip7 to decompress the file, need the parameter decompression file, unzip the path
     * Unpack The supported format is zip, rar,tar
     * @param zipFile
     * @param unpackPath
     */
    public boolean extractZIP7(String zipFile,String unpackPath ){
        IInArchive archive = null;
        RandomAccessFile randomAccessFile = null;
        boolean success = false;
        try {
            randomAccessFile = new RandomAccessFile(zipFile, "rw");
            archive = SevenZip.openInArchive(null,
                    new RandomAccessFileInStream(
                            randomAccessFile));
            int[] in = new int[archive.getNumberOfItems()];
            for(int i=0;i<in.length;i++){
                in[i] = i;
            }
            archive.extract(in, false, new Zip7ExtractCallback(archive, unpackPath));
            success = true;
        }catch (FileNotFoundException e){
            logger.error(zipFile+"-FileNotFoundException occurs: ");
            e.printStackTrace();
        }catch (SevenZipException e){
            logger.error("SevenZipException occurs: ");
            e.printStackTrace();
        }finally {
            try {
                archive.close();
                randomAccessFile.close();
            }catch (IOException e){

            }
        }
        return success;
    }

    /**
     * Use the Java zip stream to compress the file
     * The supported format is zip, rar,tar
     * @param source
     * @param zipFilePath
     */
    public void compressZipEntry(String source,String zipFilePath){
        if (!exitsFile(source)){
            throw new RuntimeException("not found "+source+" file");
        }
        CompressZipEntry.zipFiles(source,zipFilePath);
    }

    /**
     * supper tar zip
     * @param filename Compressed file name and path
     * @param compressDir  Wait for compressed files or folder paths
     * @param format The format of the compressed
     */
    public boolean compressZIP7(String compressDir ,String filename,ArchiveFormat format) {
        Item[] items = CompressOutItemStructure.create(compressDir);
        boolean success = false;
        RandomAccessFile raf = null;
        IOutCreateArchive outArchive = null;
        try {
            raf = new RandomAccessFile(filename, "rw");
            outArchive = SevenZip.openOutArchive(format);
            outArchive.createArchive(new RandomAccessFileOutStream(raf),
                    items.length, new Zip7CompressCallBack(items));
            success = true;
        } catch (SevenZipException e) {
            logger.error(format.getMethodName()+"-Error occurs:");
            e.printStackTraceExtended();
        } catch (Exception e) {
            logger.error("Error occurs: " + e);
        } finally {
            if (outArchive != null) {
                try {
                    outArchive.close();
                } catch (IOException e) {
                    logger.error("Error closing archive: " + e);
                    success = false;
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    logger.error("Error closing file: " + e);
                    success = false;
                }
            }
        }
        if (success) {
            System.out.println("Compression operation succeeded");
        }
        return success;
    }
}
