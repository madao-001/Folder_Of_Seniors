package ncu.folder_of_seniors.model.Lisentener;


import java.util.List;

import ncu.folder_of_seniors.module.entity.Resource;

public interface ResouceDetailsLisentener {

     void onSeccess(Resource resource);

     void onFails(String msg);

}
