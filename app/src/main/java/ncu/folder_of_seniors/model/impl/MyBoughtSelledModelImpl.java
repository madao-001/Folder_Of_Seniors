package ncu.folder_of_seniors.model.impl;

import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.MyBoughtSelledLisentener;
import ncu.folder_of_seniors.module.entity.User;

public interface MyBoughtSelledModelImpl {
    void getData(String type, User user, MyBoughtSelledLisentener lisentener);
}
