import net.sf.sevenzipjbinding.ArchiveFormat;
import server.SevenZipServer;

/**
 * Created by wangshunyao on 2017/5/4.
 */
public class Main {


    public static void main(String[] args){
        //解压文件
        String path = "H:/7zip_Idea.tar";
        String unzipPath = "H:/ceshi7zip";
        String compressPath = "H:/aaaa1/";
        SevenZipServer server = new SevenZipServer();

        System.out.println("---------------开始压缩---------------------");
        server.compressZIP7(unzipPath,path,ArchiveFormat.TAR);
       /* //server.compressZipEntry(unzipPath,path);
        System.out.println("---------------压缩完成---------------------");
        */
        System.out.println("---------------开始解压---------------------");
        server.extractZIP7(path,compressPath);
        System.out.println("---------------解压完成---------------------");
    }
}
