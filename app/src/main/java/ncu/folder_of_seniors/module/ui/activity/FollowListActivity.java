package ncu.folder_of_seniors.module.ui.activity;

import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.adapter.FollowAdapter;
import ncu.folder_of_seniors.module.ui.view.FollowView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.FollowPresenter;
import ncu.folder_of_seniors.utils.ToastEx;

public class FollowListActivity extends BaseActivity implements FollowView {

    private FollowAdapter followAdapter;
    private Boolean isFollowing=false;
    private List<User> userlist;

    @BindView(R.id.simple_toolbar) SimpleToolBar simpleToolBar;
    @BindView(R.id.follow_lv) ListView follow_lv;
    @InjectPresenter FollowPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow;
    }

    @Override
    protected void initViews() {
        if(isFollowing){
            simpleToolBar.setMainTitle("我的关注");
        }else {
            simpleToolBar.setMainTitle("我的粉丝");
        }
        simpleToolBar.setLeftTitleDrawable(R.drawable.icon_back);
        simpleToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //重写菜单
        registerForContextMenu(follow_lv);

        //短按出菜单
        follow_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent();
                String objectid=userlist.get(position).getObjectId();
                intent1.putExtra("objectId",objectid);
                intent1.setClass(FollowListActivity.this, PersonalActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        isFollowing=intent.getBooleanExtra("isFollowing",false);
        if(isFollowing)
            mPresenter.showData("following");
        else
            mPresenter.showData("followers");
    }

    @Override
    public void showData(List<User> list) {
        userlist = list;
        followAdapter = new FollowAdapter(FollowListActivity.this, userlist);
        follow_lv.setAdapter(followAdapter);
        //followAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }
}
