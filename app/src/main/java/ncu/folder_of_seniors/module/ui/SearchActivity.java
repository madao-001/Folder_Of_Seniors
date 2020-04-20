package ncu.folder_of_seniors.module.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.adapter.FirstFAdapter;
import ncu.folder_of_seniors.module.ui.view.SearchView;
import ncu.folder_of_seniors.presenter.SearchPresenter;
import ncu.folder_of_seniors.utils.ToastEx;
import ncu.folder_of_seniors.utils.Verify;

public class SearchActivity extends BaseActivity implements FirstFAdapter.Callback, SearchView {

    @BindView(R.id.search_et_search) EditText et_search;
    @BindView(R.id.search_tv_search) TextView tv_search;
    @BindView(R.id.search_searchlist) RecyclerView lv_resource;
    @BindView(R.id.search_iv_back_search) ImageView iv_back;
    @BindView(R.id.search_swipe_refresh_search) SwipeRefreshLayout swipeRefresh;
    @InjectPresenter private SearchPresenter mPresenter;

    private List<Resource> mList = new ArrayList<>();
    private FirstFAdapter adapter;
    private String type;
    private String search;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews() {
        adapter = new FirstFAdapter(this, mList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        lv_resource.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置Adapter
        lv_resource.setAdapter(adapter);

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
        Intent intent=getIntent();
        //此时得到的objectid是卖主的objectid
        type=intent.getStringExtra("type");
        mPresenter.getData();
    }

    @OnClick({R.id.search_iv_back_search,R.id.search_tv_search})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.search_iv_back_search:
                finish();
                break;
            case R.id.search_tv_search:
                if(!Verify.isStrEmpty(et_search.getText().toString())){
                    search = et_search.getText().toString();
                    mPresenter.getData();
                }
                break;
        }
    }

    @Override
    public void click(View v) {

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getKeyWord() {
        return search;
    }

    @Override
    public void showData(List<Resource> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
        mList.clear();
        adapter.notifyDataSetChanged();
    }
}
