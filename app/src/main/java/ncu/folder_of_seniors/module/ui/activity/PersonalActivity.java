package ncu.folder_of_seniors.module.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.adapter.PersonalAdapter;
import ncu.folder_of_seniors.module.ui.view.PersonalView;
import ncu.folder_of_seniors.presenter.PersonalPresenter;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class PersonalActivity extends BaseActivity implements PersonalView {

    @BindView(R.id.personal_iv_head) ImageView iv_head;
    @BindView(R.id.personal_iv_following) ImageView iv_following;
    @BindView(R.id.personal_tv_username) TextView tv_username;
    @BindView(R.id.personal_tv_school_and_sex) TextView tv_school_and_sex;
    @BindView(R.id.personal_tv_like) TextView tv_like;
    @BindView(R.id.personal_tv_following) TextView tv_following;
    @BindView(R.id.personal_tv_follower) TextView tv_follower;
    @BindView(R.id.personal_tv_createdTime) TextView tv_createdTime;
    @BindView(R.id.personal_rv) RecyclerView personal_rv;
    @BindView(R.id.personal_isOnline) TextView tv_isOnline;
    @InjectPresenter private PersonalPresenter mPresenter;

    private User person;
    private String personId;
    private Integer followersNo;
    private Integer followingNo;
    private Boolean isFollowing;
    private List<Resource> resourcesList=new ArrayList<Resource>();
    private PersonalAdapter personalAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        personal_rv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        personalAdapter = new PersonalAdapter(PersonalActivity.this, resourcesList);
        //设置Adapter
        personal_rv.setAdapter(personalAdapter);
        mPresenter.getData(personId);
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        //此时得到的objectid是卖方的objectid
        personId=intent.getStringExtra("objectId");
        mPresenter.getData(personId);
    }

    @Override
    public void showUserInfo(User user) {
        this.person = user;
        tv_username.setText(person.getUsername());
        if(person.isOnline()){
            tv_isOnline.setText("我在线上");
        }else {
            tv_isOnline.setText("暂时下线");
        }
        tv_createdTime.setText("该用户注册于"+person.getCreatedAt());
        if (person.isSex()){
            tv_school_and_sex.setText("来自"+person.getSchool()+"的男生");
        }else {
            tv_school_and_sex.setText("来自"+person.getSchool()+"的女生");
        }
        if(person.getIcon()!=null){
            Glide.with(getContext()).load(person.getIcon()).into(iv_head);
        }
    }

    @Override
    public void showFollow(Integer num1, Integer num2, Boolean isFollow) {
        this.isFollowing = isFollow;
        this.followingNo = num1;
        this.followersNo = num2;
        if(isFollowing)
            iv_following.setImageDrawable(getResources().getDrawable(R.drawable.yiguanzhu));
        else
            iv_following.setImageDrawable(getResources().getDrawable(R.drawable.guanzhu));
        tv_follower.setText(followersNo+"粉丝");
        tv_following.setText(followingNo+"关注");
    }

    @Override
    public void showResourceList(List<Resource> list) {
        resourcesList.clear();
        resourcesList.addAll(list);
        personalAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.personal_iv_following)
    public void onClick(View v){
        if(v.getId() == R.id.personal_iv_following){
            if(clientUser!=null){
                if(!personId.equals(clientUser.getObjectId())){
                    if(isFollowing)
                        mPresenter.removeFollowing(person);
                    else
                        mPresenter.addFollowing(person);
                }else
                    Toast.makeText(getContext(),"不能关注自己！", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChangeFollow(Boolean isAdd) {
        if(isAdd){
            isFollowing = true;
            iv_following.setImageDrawable(getResources().getDrawable(R.drawable.yiguanzhu));
        }else {
            isFollowing = false;
            iv_following.setImageDrawable(getResources().getDrawable(R.drawable.guanzhu));
        }

    }

    @Override
    public void showLikesNo(Integer num) {
        tv_like.setText(num+"超赞");
    }
}

