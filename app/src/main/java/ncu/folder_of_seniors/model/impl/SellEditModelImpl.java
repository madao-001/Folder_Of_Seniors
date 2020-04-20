package ncu.folder_of_seniors.model.impl;


import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.module.entity.Resource;

public interface SellEditModelImpl {
    void getResource(String resourceId, ResouceDetailsLisentener lisentener);
    void udpateResource(Resource resource,String filePaths, RegisterLisentener lisentener);
}
