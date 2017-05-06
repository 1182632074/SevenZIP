package compress;

import entity.Item;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.OutItemFactory;
import net.sf.sevenzipjbinding.util.ByteArrayStream;

/**
 * Created by wangshunyao on 2017/5/4.
 */
public class Zip7CompressCallBack implements IOutCreateCallback<IOutItemBase>  {

    private Item[] items;
    public Zip7CompressCallBack(){}

    public Zip7CompressCallBack(Item[] items) {
        this.items = items;
    }

    public void setOperationResult(boolean operationResultOk)
            throws SevenZipException {
        // Track each operation result here
    }

    public void setTotal(long total) throws SevenZipException {
        // Track operation progress here
    }

    public void setCompleted(long complete) throws SevenZipException {
        // Track operation progress here
    }

    public IOutItemBase getItemInformation(int index,
                                          OutItemFactory<IOutItemBase> outItemFactory) {
        IOutItemBase item = outItemFactory.createOutItem();
        String format = item.getArchiveFormat().getMethodName();

        if (items[index].getContent() == null) {
            if(format.equals("Tar")){
                ((IOutItemTar)item).setPropertyIsDir(true);
            }else if(format.equals("Zip")){
                ((IOutItemZip)item).setPropertyIsDir(true);
            }
        } else {
            item.setDataSize((long) items[index].getContent().length);
        }
        if(format.equals("Tar")){
            ((IOutItemTar)item).setPropertyPath(items[index].getPath());
        }else if(format.equals("Zip")){
            ((IOutItemZip)item).setPropertyPath(items[index].getPath());
        }
        return item;
    }

    public ISequentialInStream getStream(int i) throws SevenZipException {
        if (items[i].getContent() == null) {
            return null;
        }
        return new ByteArrayStream(items[i].getContent(), true);
    }
}
