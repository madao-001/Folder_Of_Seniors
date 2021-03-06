package ncu.folder_of_seniors.module.ui.activity;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.adapter.MyLaunchAdapter;
import ncu.folder_of_seniors.module.ui.view.MyLaunchView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.MyLaunchPresenter;

public class MyLaunchActivity extends BaseActivity implements MyLaunchView {

    private MyLaunchAdapter myLaunchAdapter;
    private List<Resource> resourceList=new ArrayList<Resource>();
    private int CurrentSel;


    @BindView(R.id.my_launch_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.my_launch_rl) RecyclerView re_recycleview;
    @BindView(R.id.simple_toolbar) SimpleToolBar toolBar;
    @InjectPresenter MyLaunchPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_launch;
    }

    @Override
    protected void initViews() {
        toolBar.setMainTitle("我发布的");
        toolBar.setLeftTitleDrawable(R.drawable.icon_back);
        toolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        re_recycleview.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);

        myLaunchAdapter = new MyLaunchAdapter(MyLaunchActivity.this, resourceList);

        registerForContextMenu(re_recycleview);
        myLaunchAdapter.setOnItemClickListener(new MyLaunchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Resource resource = resourceList.get(position);
                //Toast.makeText(mContext,"name is "+sell.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyLaunchActivity.this, ResourceDetailsActivity.class);
                intent.putExtra("objectId",resource.getObjectId());
                startActivity(intent);
                //Toast.makeText(MyLaunchActivity.this, "click " + sellList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        myLaunchAdapter.setOnItemLongClickListener(new MyLaunchAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                CurrentSel=position;
                re_recycleview.showContextMenu();
                //Toast.makeText(MyLaunchActivity.this,"long click "+sellList.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        //设置Adapter
        re_recycleview.setAdapter(myLaunchAdapter);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);   //设置下拉刷新进度条的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();   //进行刷新操作
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.showData();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,0,0, R.string.delete_release);
        super.onCreateContextMenu(menu,v,menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                //执行删除发布操作
                Resource resource = resourceList.get(CurrentSel);
                mPresenter.deleteData(resource);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void showData(List<Resource> list) {
        resourceList.clear();
        resourceList.addAll(list);
        myLaunchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

