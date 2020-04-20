package ncu.folder_of_seniors.model.Lisentener;


import java.util.List;

import ncu.folder_of_seniors.module.entity.User;

public interface FollowLisentener {

     void onSeccess(List<User> users);

     void onFails(String msg);

}
