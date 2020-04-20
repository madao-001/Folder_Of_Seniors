package ncu.folder_of_seniors.module.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.UserAction;
import ncu.folder_of_seniors.module.ui.adapter.MyBoughtAdapter;
import ncu.folder_of_seniors.module.ui.view.MyBoughtSelledView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.MyBoughtSelledPresenter;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class MyBoughtActivity extends BaseActivity implements MyBoughtAdapter.Callback, AdapterView.OnItemClickListener, MyBoughtSelledView {

    private List<UserAction> mList = new ArrayList<>();
    private MyBoughtAdapter adapter;

    @BindView(R.id.simple_toolbar) SimpleToolBar toolBar;
    @BindView(R.id.my_bought_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.my_bought_rl) RecyclerView sellLv;
    @InjectPresenter MyBoughtSelledPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bought;
    }

    @Override
    protected void initViews() {
        toolBar.setMainTitle("我买到的");
        toolBar.setLeftTitleDrawable(R.drawable.icon_back);
        toolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new MyBoughtAdapter(this, mList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        sellLv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置Adapter
        sellLv.setAdapter(adapter);
        //设置分隔线
        //sellLv.addItemDecoration( new DividerGridItemDecoration(this ));

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
        mPresenter.getData("buy",clientUser);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Sell sell = mList.get(position);
//        Toast.makeText(getActivity(),"name is"+sell.getTitle(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(View v) {

    }

    @Override
    public void showData(List<UserAction> userActionList) {
        mList.clear();
        mList.addAll(userActionList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
