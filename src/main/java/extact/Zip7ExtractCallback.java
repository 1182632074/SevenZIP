package extact;

import net.sf.sevenzipjbinding.*;

import java.io.File;

import static util.ZipUtils.buildUnPath;
import static util.ZipUtils.writeFiles;

/**
 * Created by wangshunyao on 2017/5/4.
 */
public class Zip7ExtractCallback implements IArchiveExtractCallback {
    private int index;
    private String unzipPath;
    private IInArchive inArchive;

    public Zip7ExtractCallback(IInArchive inArchive, String unzipPath) {
        this.inArchive = inArchive;
        this.unzipPath = unzipPath;
    }

    public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
        this.index = index;
        final String path = ((String) inArchive.getProperty(index, PropID.PATH));
        final boolean isFolder = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
        return new ISequentialOutStream() {
            public int write(byte[] data) throws SevenZipException {
                try {
                    if (!isFolder) {
                        String path1 = unzipPath+path;
                        File file = buildUnPath(path1);
                        writeFiles(file, data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return data.length;
            }
        };
    }

    public void prepareOperation(ExtractAskMode arg0) throws SevenZipException {
    }

    public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
        String path = (String) inArchive.getProperty(index, PropID.PATH);
        boolean isFolder = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
        if (!isFolder) {
            if (extractOperationResult != ExtractOperationResult.OK) {
                StringBuilder sb = new StringBuilder();
                sb.append("解压").append(unzipPath).append("包的").append(path).append("文件");
                sb.append("失败！");
            }
        }

    }

    public void setTotal(long l) throws SevenZipException {

    }

    public void setCompleted(long l) throws SevenZipException {

    }


}
