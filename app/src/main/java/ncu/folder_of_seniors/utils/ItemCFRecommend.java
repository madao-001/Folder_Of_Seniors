package ncu.folder_of_seniors.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;
import ncu.folder_of_seniors.module.entity.Res;

import static ncu.folder_of_seniors.app.MyApplication.users;
import static ncu.folder_of_seniors.app.MyApplication.userActions;
import static ncu.folder_of_seniors.app.MyApplication.resources;

public class ItemCFRecommend {
    //通过计算余弦相似度并取TopN, 返回为uid的用户生成的5个推荐文章
    public static ArrayList<Resource> recommend(User user){
        Log.e("MyApplication:","开始执行算法");
        ArrayList<Res> resArrayList = new ArrayList<>();
        int[][] curMatrix = new int[resources.size()+5][resources.size()+5];   //当前矩阵
        int[][] comMatrix = new int[resources.size()+5][resources.size()+5];   //共现矩阵
        int[] N = new int[resources.size()+5];                              //喜欢每个物品的人数

        for(User user1: users){
            if(user1.getObjectId()==user.getObjectId()) continue;                    //当前用户则跳过
            ArrayList<Resource> likeLists = new ArrayList<>();
            for(UserAction userAction:userActions){
                if(userAction.getActionType().equals("like")&&userAction.getUser().getObjectId().equals(user1.getObjectId())){
                    likeLists.add(userAction.getResource());
                }
            }

            for(int i = 0; i < resources.size(); i++){
                N[i] = resources.get(i).getLikes();
                Res res = new Res();
                res.setId(resources.get(i).getObjectId());
                res.setNum(i);
                resArrayList.add(res);
                for(int j = 0; j < resources.size(); j++)
                    curMatrix[i][j] = 0;                               //清空矩阵
            }

            for(int i = 0; i < likeLists.size(); i++){
                String rid1 = likeLists.get(i).getObjectId();
                for(int j = i+1; j < likeLists.size(); j++){
                    String rid2 = likeLists.get(j).getObjectId();
                    int n1=0;
                    int n2=0;
                    for(Res res:resArrayList){
                        if(res.getId().equals(rid1))
                            n1 = res.getNum();
                        else if(res.getId().equals(rid2))
                            n2 = res.getNum();
                    }
                    ++curMatrix[n1][n2];
                    ++curMatrix[n2][n1]; //两两加一
                }
            }
            //累加所有矩阵, 得到共现矩阵
            for(int i = 0; i < resources.size(); i++){
                for(int j = 0; j < resources.size(); j++){
                    String rid1 = resources.get(i).getObjectId(), rid2 = resources.get(j).getObjectId();
                    int n1=0;
                    int n2=0;
                    for(Res res:resArrayList){
                        if(res.getId().equals(rid1))
                            n1 = res.getNum();
                        else if(res.getId().equals(rid2))
                            n2 = res.getNum();
                    }
                    comMatrix[n1][n2] += curMatrix[n1][n2];
                    comMatrix[n1][n2] += curMatrix[n1][n2];
                }
            }
        }


        TreeSet<Resource> preList = new TreeSet<Resource>(new Comparator<Resource>() {
            @Override
            public int compare(Resource r1, Resource r2) {
                if(r1.getSimilarity()!=r2.getSimilarity())
                    return (int) (r1.getSimilarity()-r2.getSimilarity())*100;
                else
                    return r1.getLikes()-r2.getLikes();
            }
        }); //预处理的列表

        ArrayList<Resource> likeLists = new ArrayList<>(); //当前用户喜欢的论文列表
        for(UserAction userAction:userActions){
            if(userAction.getActionType().equals("like")&&userAction.getUser().getObjectId().equals(user.getObjectId())){
                likeLists.add(userAction.getResource());
            }
        }
        boolean[] used = new boolean[resources.size()+5];  //判重数组
        for(Resource like: likeLists){
            int Nij = 0;                         //既喜欢i又喜欢j的人数
            double Sij;                          //相似度
            Resource tmp;                           //当前的论文

            String i = like.getObjectId();
            for(Resource resource: resources){
                if(like.getObjectId().equals(resource.getObjectId())) continue;
                String j = resource.getObjectId();
                int n1=0;
                int n2=0;
                for(Res res:resArrayList){
                    if(res.getId().equals(i))
                        n1 = res.getNum();
                    else if(res.getId().equals(j))
                        n2 = res.getNum();
                }
                Nij = comMatrix[n1][n2];
                Sij = (double)Nij/Math.sqrt(N[n1]*N[n2]); //计算余弦相似度

                tmp = resource;
                tmp.setSimilarity(Sij);
                int n=0;
                for(Res res:resArrayList){
                    if(res.getId().equals(tmp.getObjectId()))
                        n = res.getNum();
                }
                if(used[n]) continue;
                preList.add(tmp);
                used[n] = true;
            }
        }

        ArrayList<Resource> recomLists = new ArrayList<>();      //生成的推荐结果
        for(int i = 0; preList.size()>0 && i<5; i++){
            recomLists.add(preList.pollLast());
            //preList.pollLast();
        }
        if(recomLists.size()<3){
            Log.e("MyApplication:","生成的推荐资源数量:"+recomLists.size()+"，Title为"+recomLists.get(0).getTitle()+"，相似度为"+recomLists.get(0).getSimilarity());
            //推荐数量不满5个, 补足喜欢数最高的文章
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
        Log.e("MyApplication:","生成的推荐资源数量:"+recomLists.size()+"，Title为"+recomLists.get(1).getTitle());
        return recomLists;
    }

}
