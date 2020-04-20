package ncu.folder_of_seniors.module.ui;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.adapter.CollectionAdapter;
import ncu.folder_of_seniors.module.ui.view.MyStarView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.MyStarPresenter;

public class MyStarActivity extends BaseActivity implements MyStarView {
    private User userInfo;
    private List<Resource> resourceList=new ArrayList<Resource>();
    private CollectionAdapter collectionAdapter;
    private int CurrentSel;

    @BindView(R.id.my_collection_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.simple_toolbar) SimpleToolBar toolBar;
    @BindView(R.id.collection_recycleview) RecyclerView recyclerView;
    @InjectPresenter MyStarPresenter mPresenter;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,0,0, R.string.delete_collect);
        super.onCreateContextMenu(menu,v,menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                //执行删除收藏操作
                Resource resource=resourceList.get(CurrentSel);
                mPresenter.removeStar(resource);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initViews() {
        toolBar.setMainTitle("我收藏的");
        toolBar.setLeftTitleDrawable(R.drawable.icon_back);
        toolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);

        collectionAdapter= new CollectionAdapter(MyStarActivity.this, resourceList);

        registerForContextMenu(recyclerView);
        collectionAdapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Resource resource = resourceList.get(position);
                //Toast.makeText(mContext,"name is "+sell.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyStarActivity.this, ResourceDetailsActivity.class);
                intent.putExtra("objectId",resource.getObjectId());
                startActivity(intent);
                //Toast.makeText(MyStarActivity.this, "click " + sellList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        collectionAdapter.setOnItemLongClickListener(new CollectionAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                CurrentSel=position;
                recyclerView.showContextMenu();
                //Toast.makeText(MyStarActivity.this,"long click "+sellList.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);   //设置下拉刷新进度条的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();   //进行刷新操作
                swipeRefresh.setRefreshing(false);
            }
        });


        //设置Adapter
        recyclerView.setAdapter(collectionAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.showData();
    }

    @Override
    public void showData(List<Resource> list) {
        resourceList.clear();
        resourceList.addAll(list);
        collectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
