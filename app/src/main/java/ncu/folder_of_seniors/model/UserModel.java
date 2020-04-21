package ncu.folder_of_seniors.model;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ncu.folder_of_seniors.model.Lisentener.QueryUserListener;
import ncu.folder_of_seniors.model.Lisentener.UpdateCacheListener;
import ncu.folder_of_seniors.module.entity.User;

public class UserModel extends BaseModel {

    private static UserModel ourInstance = new UserModel();

    public static UserModel getInstance() {
        return ourInstance;
    }

    private UserModel() {
    }


    /**
     * 查询用户
     *
     * @param username
     * @param limit
     * @param listener
     */
    public void queryUsers(String username, final int limit, final FindListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        //去掉当前用户
        try {
            BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
            query.addWhereNotEqualTo("username", user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        query.addWhereContains("username", username);
        query.setLimit(limit);
        query.order("-createdAt");
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list, e);
                    } else {
                        listener.done(list, new BmobException(CODE_NULL, "查无此人"));
                    }
                } else {
                    listener.done(list, e);
                }
            }
        });
    }

    /**
     * 查询指定用户信息
     *
     * @param objectId
     * @param listener
     */
    public void queryUserInfo(String objectId, final QueryUserListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(
                new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {

                            if (list != null && list.size() > 0) {
                                listener.done(list.get(0), null);
                            } else {
                                listener.done(null, new BmobException(000, "查无此人"));
                            }
                        } else {
                            listener.done(null, e);
                        }
                    }
                });
    }

    /**
     * 更新用户资料和会话资料
     *
     * @param event
     * @param listener
     */
    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String username = info.getName();
        String avatar = info.getAvatar();
        String title = conversation.getConversationTitle();
        String icon = conversation.getConversationIcon();
        //SDK内部将新会话的会话标题用objectId表示，因此需要比对用户名和私聊会话标题，后续会根据会话类型进行判断
        if (!username.equals(title) || (avatar != null && !avatar.equals(icon))) {
            UserModel.getInstance().queryUserInfo(info.getUserId(), new QueryUserListener() {
                @Override
                public void done(User s, BmobException e) {
                    if (e == null) {
                        String name = s.getUsername();
                        String avatar = s.getIcon();
                        conversation.setConversationIcon(avatar);
                        conversation.setConversationTitle(name);
                        info.setName(name);
                        info.setAvatar(avatar);
                        //更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                        BmobIM.getInstance().updateUserInfo(info);
                        //更新会话资料-如果消息是暂态消息，则不更新会话资料
                        if (!msg.isTransient()) {
                            BmobIM.getInstance().updateConversation(conversation);
                        }
                    } else {
                        Log.e("",e.getMessage());
                    }
                    listener.done(null);
                }
            });
        } else {
            listener.done(null);
        }
    }

}
