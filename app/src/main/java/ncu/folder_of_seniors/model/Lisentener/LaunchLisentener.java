package ncu.folder_of_seniors.model.Lisentener;


import cn.bmob.v3.datatype.BmobFile;

public interface LaunchLisentener {

     void onSeccess(BmobFile file);

     void onFails(String msg);

}
