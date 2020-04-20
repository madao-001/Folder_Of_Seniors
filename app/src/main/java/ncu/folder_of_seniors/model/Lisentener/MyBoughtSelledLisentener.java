package ncu.folder_of_seniors.model.Lisentener;


import java.util.List;

import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.UserAction;

public interface MyBoughtSelledLisentener {

     void onSeccess(List<UserAction> list);

     void onProgress(List<UserAction> list);

     void onFails(String msg);

}
