package ncu.folder_of_seniors.model.impl;


import cn.bmob.v3.datatype.BmobFile;
import ncu.folder_of_seniors.model.FouthFModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener2;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;

public interface FouthFModelImpl {
    void showData(FouthFLisentener lisentener);
    void showIcon(FouthFLisentener2 lisentener);
    void downloadFile(BmobFile file,RegisterLisentener lisentener);
    void uploadFile(BmobFile file,RegisterLisentener lisentener);
}
