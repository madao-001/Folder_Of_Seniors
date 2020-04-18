package ncu.folder_of_seniors.model.Lisentener;


import java.util.List;

import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

public interface PersonalLisentener {

     void onSeccess(User user);

     void onFails(String msg);

}
