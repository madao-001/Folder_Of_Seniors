package ncu.folder_of_seniors.module.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseFragment;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.adapter.FirstFAdapter;
import ncu.folder_of_seniors.module.ui.view.SearchView;
import ncu.folder_of_seniors.presenter.SearchPresenter;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;


public class SecondFragment extends BaseFragment implements SearchView, FirstFAdapter.Callback {

    private List<Resource> mList = new ArrayList<>();
    private FirstFAdapter adapter;

    @BindView(R.id.f2_recyclerView)
    RecyclerView f2_recyclerView;
    @BindView(R.id.f2_swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @InjectPresenter
    SearchPresenter mPresenter;

    public SecondFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void init() {
        adapter = new FirstFAdapter(getActivity(), mList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        f2_recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置Adapter
        f2_recyclerView.setAdapter(adapter);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);   //设置下拉刷新进度条的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData2();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void initData2(){
        if(clientUser!=null){
            if(clientUser.getSchool()!=null)
                mPresenter.getData();
            else
                Toast.makeText(getContext(),"请先在个人信息中填写学校！", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(View v) {
    }

    @Override
    public String getType() {
        return "school";
    }

    @Override
    public String getKeyWord() {
        return clientUser.getSchool();
    }

    @Override
    public void showData(List<Resource> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData2();
                }
            }, 100);//该方法调用时onCreatView()还没执行，故0.1秒之后再执行数据更新操作
        }
    }
}
