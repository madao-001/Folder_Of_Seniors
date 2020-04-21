package ncu.folder_of_seniors.model.Lisentener;


import java.util.List;

import ncu.folder_of_seniors.module.entity.Conversation;
import ncu.folder_of_seniors.module.entity.Resource;

public interface ThirdFLisentener {

     void onSeccess(List<Conversation> list);

     void onFails(String msg);

}
