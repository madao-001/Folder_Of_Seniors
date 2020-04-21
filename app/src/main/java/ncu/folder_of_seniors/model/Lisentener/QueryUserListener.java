package ncu.folder_of_seniors.model.Lisentener;

import cn.bmob.newim.listener.BmobListener1;
import cn.bmob.v3.exception.BmobException;
import ncu.folder_of_seniors.module.entity.User;

public abstract class QueryUserListener extends BmobListener1<User> {

    public abstract void done(User s, BmobException e);

    @Override
    protected void postDone(User o, BmobException e) {
        done(o, e);
    }
}
