package ncu.folder_of_seniors.model.impl;


import cn.bmob.v3.BmobUser;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;

public interface RegisterModelImpl {
    void uploadIcon(String path,RegisterLisentener lisentener);
    void registerByEmail(BmobUser user,RegisterLisentener lisentener);
    void registerByPhone(BmobUser user,String smsCode,RegisterLisentener lisentener);
    void requestSMSCode(String phoneNo,RegisterLisentener lisentener);
    void verifySmsCode(String phoneNo,String smsCode,BaseLisentener lisentener);
}
