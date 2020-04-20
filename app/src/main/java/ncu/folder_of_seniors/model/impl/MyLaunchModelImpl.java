package ncu.folder_of_seniors.model.impl;

import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

public interface MyLaunchModelImpl {
    void getData(User user, FirstFLisentener lisentener);
    void deleteData(Resource resource, RegisterLisentener lisentener);
}
