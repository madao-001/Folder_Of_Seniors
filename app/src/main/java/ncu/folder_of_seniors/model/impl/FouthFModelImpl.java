package ncu.folder_of_seniors.model.impl;


import ncu.folder_of_seniors.model.FouthFModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;

public interface FouthFModelImpl {
    void showData(FouthFLisentener lisentener);
    void addFollowing(RegisterLisentener lisentener);
    void addFollowers(RegisterLisentener lisentener);
}
