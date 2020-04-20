package ncu.folder_of_seniors.model.impl;


import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.Reviews;

public interface ReviewModelImpl {
    void launchReview(Reviews reviews, List<LocalMedia> list, RegisterLisentener lisentener);
    void getResource(String resourceId, ResouceDetailsLisentener lisentener);
}
