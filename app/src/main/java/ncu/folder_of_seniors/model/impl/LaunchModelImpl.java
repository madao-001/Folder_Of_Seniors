package ncu.folder_of_seniors.model.impl;


import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import ncu.folder_of_seniors.model.Lisentener.LaunchLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.module.entity.Resource;

public interface LaunchModelImpl {
    void launchResource(Resource resource,String[] filePaths, RegisterLisentener lisentener);
}
