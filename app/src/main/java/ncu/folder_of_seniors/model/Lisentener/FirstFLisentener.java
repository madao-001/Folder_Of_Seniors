package ncu.folder_of_seniors.model.Lisentener;


import java.util.List;
import ncu.folder_of_seniors.module.entity.Resource;

public interface FirstFLisentener {

     void onSeccess(List<Resource> list);

     void onFails(String msg);

}
