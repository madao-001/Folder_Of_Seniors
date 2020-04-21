package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ThirdFLisentener;
import ncu.folder_of_seniors.model.impl.MyLaunchModelImpl;
import ncu.folder_of_seniors.model.impl.ThirdFModelImpl;
import ncu.folder_of_seniors.module.entity.Conversation;
import ncu.folder_of_seniors.module.entity.PrivateConversation;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

public class ThirdFModel extends BaseModel implements ThirdFModelImpl {


    @Override
    public void getData(ThirdFLisentener lisentener) {
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        //查询全部会话
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if(list!=null && list.size()>0){
            for (BmobIMConversation item:list){
                switch (item.getConversationType()){
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
            lisentener.onSeccess(conversationList);
        }else
            lisentener.onFails("目前没有会话");
    }

}

