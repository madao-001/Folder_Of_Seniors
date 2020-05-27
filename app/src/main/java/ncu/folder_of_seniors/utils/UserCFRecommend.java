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
    static int K = 5;
    /**
     * 在给定userid的情况下，计算其他用户和它的余弦相似度并排序
     * @param user
     * @return Map<Double, String> Neighbors
     */
    public static Map<Double, String> computeNearestNeighbor(User user) {
        /**
         * 目标用户和其他用户的余弦相似度图
         * @param distance
         * @param userid
         */
        Map<Double, String> distances = new TreeMap<>();
        /**
         * 目标用户的评价图
         * @param itemid
         * @param grade
         */
        Map<String, Double> reviews_userA = new HashMap<>();
        /**
         * 其他用户的评价图
         * @param itemid
         * @param grade
         */
        Map<String, Double> reviews_userB = new HashMap<>();
        /**
         * 所有用户的平均评分图
         * @param userid
         * param {grade,num}
         */
        Map<String, Object> reviwsAve = new HashMap<>();
        //获取所有用户的平均评分图
        for(Reviews review:reviews){
            if(reviwsAve.get(review.getUser().getObjectId())==null){
                List<Integer> list = new ArrayList<Integer>();
                list.add(review.getGrade());
                list.add(1);
                reviwsAve.put(review.getUser().getObjectId(), list);
            }else{
                List<Integer> list = (List<Integer>)reviwsAve.get(review.getUser().getObjectId());
                list.set(0,review.getGrade()+list.get(0));
                list.set(1,list.get(1)+1);
                reviwsAve.put(review.getUser().getObjectId(), list);
            }
        }
        for (int i = 0; i < users.size(); i++) {
            User user1 = users.get(i);
            reviews_userA.clear();//目标用户评分矩阵
            reviews_userB.clear();//其他用户评分矩阵
            if (!user1.getObjectId().equals(user.getObjectId())) {
                for(Reviews review:reviews){
                    if(review.getUser().getObjectId().equals(user.getObjectId()))
                        reviews_userA.put(review.getResource().getObjectId(),review.getGrade()*1.0);
                    else if(review.getUser().getObjectId().equals(user1.getObjectId()))
                        reviews_userB.put(review.getResource().getObjectId(),review.getGrade()*1.0);
                }
                if(reviews_userA.size()==0||reviews_userB.size()==0)
                    continue;
                boolean is_intersect=false;
                for(String itemid : reviews_userA.keySet()){
                    if(reviews_userB.get(itemid)!=null){
                        //判断两个用户的评分集合是否有交集
                        is_intersect=true;
                    }
                    if(reviews_userB.get(itemid)==null){
                        //矩阵填充
                        double grade_pre = 0;
                        double grade_ave = 0;
                        List<Integer> list = (List<Integer>)reviwsAve.get(user.getObjectId());
                        grade_ave = list.get(0)/list.get(1);
                        int num = 0;
                        double grade_sum = 0;
                        for(Reviews review:reviews){
                            if(review.getResource().getObjectId().equals(itemid)){
                                num++;
                                double grade = review.getGrade();
                                List<Integer> list1 = (List<Integer>)reviwsAve.get(review.getUser().getObjectId());
                                double ave_grade = list.get(0)/list.get(1);
                                grade_sum += grade - ave_grade;
                            }
                        }
                        grade_pre = grade_ave + grade_sum/num;
                        reviews_userB.put(itemid, grade_pre);
                    }
                }
                for(String itemid : reviews_userB.keySet()){
                    if(reviews_userA.get(itemid)==null){
                        //矩阵填充
                        double grade_pre = 0;
                        double grade_ave = 0;
                        List<Integer> list = (List<Integer>)reviwsAve.get(user.getObjectId());
                        grade_ave = list.get(0)/list.get(1);
                        int num = 0;
                        double grade_sum = 0;
                        for(Reviews review:reviews){
                            if(review.getResource().getObjectId().equals(itemid)){
                                num++;
                                double grade = review.getGrade();
                                List<Integer> list1 = (List<Integer>)reviwsAve.get(review.getUser().getObjectId());
                                double ave_grade = list.get(0)/list.get(1);
                                grade_sum += grade - ave_grade;
                            }
                        }
                        grade_pre = grade_ave + grade_sum/num;
                        reviews_userA.put(itemid, grade_pre);
                    }
                }
                if(!is_intersect)
                    continue;
                double distance = cos_dis(reviews_userA, reviews_userB);
                distances.put(distance, user1.getObjectId());
            }
        }
        Log.e("UserCF:","distance => " + distances);
        return distances;
    }

    /**
     * 计算2个用户评分矩阵间的余弦相似度
     *
     * @param mapA
     * @param mapB
     * @return
     */
    public static double cos_dis(Map<String, Double> mapA, Map<String, Double> mapB) {
        int sum_xy = 0;
        double sum_x2 = 0;
        double sum_y2 = 0;
        int n = 0;
        for (Map.Entry<String, Double> entryA : mapA.entrySet()) {
            for (Map.Entry<String, Double> entryB : mapB.entrySet()) {
                if(entryA.getKey()==entryB.getKey()){
                    n += 1;
                    double x = entryA.getValue();
                    double y = entryB.getValue();
                    sum_xy += x * y;
                    sum_x2 += Math.pow(x, 2);
                    sum_y2 += Math.pow(y, 2);
                }
            }
        }
        double denominator = sum_xy / (Math.sqrt(sum_x2) * Math.sqrt(sum_y2));
        if (denominator == 0||n == 0) {
            return 0;
        } else {
            return denominator;
        }
    }

    public static List<Resource> recommend(User user) {
        //存放预测评分列表
        Map<String, Object> recommendMap = new HashMap<>();
        ArrayList<Resource> recomLists = new ArrayList<>();      //生成的推荐结果
        //找到最近邻
        if(computeNearestNeighbor(user)!=null&&computeNearestNeighbor(user).size()!=0){
            Map<Double, String> distances = computeNearestNeighbor(user);
            //找到最近邻买过，但是该用户没买过的资源，计算评分预测
            List<Reviews> userRatings = new ArrayList<>();
            for(Reviews review:reviews){
                if(review.getUser().getObjectId().equals(user.getObjectId()))
                    userRatings.add(review);
                else {
                    for(Map.Entry<Double, String> entry : distances.entrySet()){
                        if(--K<0)
                            break;
                        if(review.getUser().getObjectId().equals(entry.getValue())){
                            if(recommendMap.get(review.getResource().getObjectId())==null){
                                List<Double> doubleList = new ArrayList<>();
                                double grade = 0;
                                doubleList.add(0,entry.getKey());//存放相似度的和
                                for(Reviews review2:reviews){
                                    if(review2.getUser().getObjectId().equals(entry.getValue())&&review2.getResource().getObjectId().equals(review.getResource().getObjectId())){
                                        grade = review2.getGrade();
                                    }
                                }
                                grade = grade*entry.getKey();
                                doubleList.add(1,grade);//存放预测评分的和
                                recommendMap.put(entry.getValue(),doubleList);
                            }else {
                                List<Double> doubleList = (List<Double>)recommendMap.get(review.getResource().getObjectId());
                                double grade = 0;
                                doubleList.set(0,entry.getKey()+doubleList.get(0));//存放相似度的和
                                for(Reviews review2:reviews){
                                    if(review2.getUser().getObjectId().equals(entry.getValue())&&review2.getResource().getObjectId().equals(review.getResource().getObjectId())){
                                        grade = review2.getGrade();
                                    }
                                }
                                grade = grade*entry.getKey();
                                doubleList.add(1,grade+doubleList.get(1));//存放预测评分的和
                                recommendMap.put(entry.getValue(),doubleList);
                            }
                        }
                    }
                }
            }
            for(Map.Entry<String, Object> entry : recommendMap.entrySet()){
                for(Reviews review:userRatings){
                    if(entry.getKey().equals(review.getResource().getObjectId())){
                        recommendMap.remove(entry.getKey());
                    }
                }
            }
            Map<Double,String> recommendations = new TreeMap<>();
            for(Map.Entry<String, Object> entry : recommendMap.entrySet()){
                List<Double> list = (List<Double>)recommendMap.get(entry.getKey());
                Double score = list.get(1)/list.get(0);
                recommendations.put(score,entry.getKey());
            }

            for(Map.Entry<Double, String> entry : recommendations.entrySet()){
                for(Resource resource:resources){
                    if(entry.getValue().equals(resource.getObjectId())){
                        resource.setGrade(entry.getKey());
                        recomLists.add(resource);
                        break;
                    }
                }
            }
            if(recomLists.size()<5){
                //推荐数量不满3个, 补足热度最高的文章
                while (recomLists.size()<5) {
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
            while (recomLists.size()<5) {
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
        //Log.e("UserCF:","recomLists -> " + recomLists.toString());
        return recomLists;
    }
}
