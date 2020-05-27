package ncu.folder_of_seniors.model.impl;


import org.json.JSONException;

import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener2;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;

public interface ResourceDetailsModelImpl {
    void getResource(String objectId,ResouceDetailsLisentener lisentener);
    void getUserAction(Resource resource, User user, ResouceDetailsLisentener2 lisentener);
    void setUserAction(UserAction action,RegisterLisentener lisentener);
    void deleteUserAction(UserAction action,RegisterLisentener lisentener);
    void changeLike(Resource resource,Integer num,RegisterLisentener lisentener);
    void changeStar(Resource resource,Integer num,RegisterLisentener lisentener);
    void updatePoints(String userid,Resource resource, BaseLisentener lisentener);
    void updateResource(Resource resource,BaseLisentener lisentener);
}
