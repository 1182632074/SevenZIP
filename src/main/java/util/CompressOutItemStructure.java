package util;

import entity.Item;
import java.io.File;

/**
 * Created by wangshunyao on 2017/5/5.
 */
public class CompressOutItemStructure {
    static int fileNumber = 0;
    static Item[] items = null;
    static String parent = "";

    /**
     * file item format is fileName String,btye[] content
     * folder dir+"/"+fileName,byte[] content
     * @param compressDir
     * @return
     */
    public static Item[] create(String compressDir) {
        File file = new File(compressDir);
        parent = file.getParent() == null ? "":file.getParent();
        fileCounter(file);
        items = new Item[fileNumber];
        packet(file);
        return items;
    }

    public static void fileCounter(File file){
        if (!file.exists()){
            throw new RuntimeException("not found file "+file.getPath());
        }
        if (file.isFile()){
            fileNumber++;
        }else{
            for(File inFile : file.listFiles()){
                if (inFile.isFile()){
                    fileNumber++;
                }else{
                    fileCounter(inFile);
                }
            }
        }
    }
    static int tempFileIndex = 0;
    public static void packet(File file){
        if (!file.exists()){
            throw new RuntimeException("not found file "+file.getPath());
        }
        if (file.isFile()){
            items[tempFileIndex] = readFile(file);
            tempFileIndex++;
        }else{
            for(File file1 : file.listFiles()){
                if (file1.isFile()){
                    items[tempFileIndex] = readFile(file1);
                    tempFileIndex++;
                }else{
                    packet(file1);
                }
            }
        }
    }

    public static Item readFile(File file){
        String path = file.getPath();
        if (!("".equals(parent))){
            path = file.getPath().replace(parent,"");
        }
        return  new Item(path,ZipUtils.getBytes(file));
    }


}