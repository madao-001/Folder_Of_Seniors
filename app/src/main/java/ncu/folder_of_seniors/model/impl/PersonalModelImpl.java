package ncu.folder_of_seniors.model.impl;


import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener2;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.module.entity.User;

public interface PersonalModelImpl {
    void findUser(String userid, PersonalLisentener lisentener);
    void findResourceList(User user, FirstFLisentener lisentener);
    void findFollow(User user, PersonalLisentener2 lisentener);
    void addFollowing(User person,RegisterLisentener lisentener);
    void addFollowers(User person,RegisterLisentener lisentener);
    void removeFollowing(User person,RegisterLisentener lisentener);
    void removeFollowers(User person,RegisterLisentener lisentener);
}
