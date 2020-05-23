package ncu.folder_of_seniors.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.Reviews;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;

import static ncu.folder_of_seniors.app.MyApplication.resources;
import static ncu.folder_of_seniors.app.MyApplication.reviews;
import static ncu.folder_of_seniors.app.MyApplication.users;

public class UserCFRecommend {
    /**
     * 在给定username的情况下，计算其他用户和它的距离并排序
     * @param user
     * @return
     */
    public static Map<Double, String> computeNearestNeighbor(User user) {
        Map<Double, String> distances = new TreeMap<>();
        ArrayList<Reviews> reviews1 = new ArrayList<>();
        ArrayList<Reviews> reviews2 = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user1 = users.get(i);
            reviews1.clear();
            reviews2.clear();
            if (!user1.getObjectId().equals(user.getObjectId())) {
                for(Reviews review:reviews){
                    if(review.getUser().getObjectId().equals(user.getObjectId()))
                        reviews1.add(review);
                    else if(review.getUser().getObjectId().equals(user1.getObjectId()))
                        reviews2.add(review);
                }
                if(reviews1.size()==0)
                    return null;
                if(reviews2.size()==0)
                    continue;
                double distance = pearson_dis(reviews1, reviews2);
                distances.put(distance, user1.getObjectId());
            }
        }
        Log.e("UserCF:","distance => " + distances);
        return distances;
    }

    /**
     * 计算2个打分序列间的pearson距离
     *
     * @param reviews1
     * @param reviews2
     * @return
     */
    public static double pearson_dis(List<Reviews> reviews1, List<Reviews> reviews2) {
        int sum_xy = 0;
        int sum_x = 0;
        int sum_y = 0;
        double sum_x2 = 0;
        double sum_y2 = 0;
        int n = 0;
        for (int i = 0; i < reviews1.size(); i++) {
            Reviews key1 = reviews1.get(i);
            for (int j = 0; j < reviews2.size(); j++) {
                Reviews key2 = reviews2.get(j);
                if (key1.getResource().getObjectId().equals(key2.getResource().getObjectId())) {
                    n += 1;
                    int x = key1.getGrade();
                    int y = key2.getGrade();
                    sum_xy += x * y;
                    sum_x += x;
                    sum_y += y;
                    sum_x2 += Math.pow(x, 2);
                    sum_y2 += Math.pow(y, 2);
                }

            }
        }
        double denominator = Math.sqrt(sum_x2 - Math.pow(sum_x, 2) / n) * Math.sqrt(sum_y2 - Math.pow(sum_y, 2) / n);
        if (denominator == 0||n == 0) {
            return 0;
        } else {
            return (sum_xy - (sum_x * sum_y) / n) / denominator;
        }
    }

    public static List<Resource> recommend(User user) {
        ArrayList<Resource> recomLists = new ArrayList<>();      //生成的推荐结果
        //找到最近邻
        if(computeNearestNeighbor(user)!=null&&computeNearestNeighbor(user).size()!=0){
            Map<Double, String> distances = computeNearestNeighbor(user);
            String nearest = distances.values().iterator().next();
            Log.e("UserCF:","nearest -> " + nearest);
            List<Reviews> recommendations = new ArrayList<>();

            //找到最近邻看过，但是我们没看过的电影，计算推荐
            List<Reviews> neighborRatings = new ArrayList<>();
            List<Reviews> userRatings = new ArrayList<>();
            for(Reviews review:reviews){
                if(review.getUser().getObjectId().equals(nearest))
                    neighborRatings.add(review);
                else if(review.getUser().getObjectId().equals(user.getObjectId()))
                    userRatings.add(review);
            }
            Log.e("UserCF:","neighborRatings -> " + neighborRatings);
            Log.e("UserCF:","userRatings -> " + userRatings);

            for (Reviews review1 : neighborRatings) {
                Boolean isExit = false;
                for(Reviews review2:userRatings){
                    if(review2.getResource().getObjectId().equals(review1.getResource().getObjectId()))
                        isExit = true;
                }
                if(!isExit)
                    recommendations.add(review1);
            }
            Collections.sort(recommendations);

            for(Reviews review:recommendations){
                for(Resource resource:resources){
                    if(review.getResource().getObjectId().equals(resource.getObjectId())){
                        recomLists.add(resource);
                        break;
                    }
                }
            }
            if(recomLists.size()<3){
                //推荐数量不满3个, 补足喜欢数最高的文章
                while (recomLists.size()<3) {
                    for (Resource resource : resources) {
                        Boolean isExit = false;
                        for (Resource resource1 : recomLists) {
                            if (resource.getObjectId().equals(resource1.getObjectId()))
                                isExit = true;
                        }
                        if (!isExit){
                            recomLists.add(resource);
                            break;
                        }
                    }
                }
            }
        }else {
            while (recomLists.size()<3) {
                for (Resource resource : resources) {
                    Boolean isExit = false;
                    for (Resource resource1 : recomLists) {
                        if (resource.getObjectId().equals(resource1.getObjectId()))
                            isExit = true;
                    }
                    if (!isExit){
                        recomLists.add(resource);
                        break;
                    }
                }
            }
        }
        Log.e("UserCF:","recomLists -> " + recomLists.toString());
        return recomLists;
    }

}
