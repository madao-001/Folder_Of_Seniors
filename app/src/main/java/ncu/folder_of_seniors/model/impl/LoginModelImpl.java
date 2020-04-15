package ncu.folder_of_seniors.model.impl;


import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;

public interface LoginModelImpl {
    void login(String username, String password,String type, BaseLisentener lisentener);
}
