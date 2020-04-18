package ncu.folder_of_seniors.model.Lisentener;


import ncu.folder_of_seniors.module.entity.Resource;

public interface ResouceDetailsLisentener2 {

     void onSeccess(Boolean isLike,Boolean isStar,Boolean isBuy);

     void onFails(String msg);

}
