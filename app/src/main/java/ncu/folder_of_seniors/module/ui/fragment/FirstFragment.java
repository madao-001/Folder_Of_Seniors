package ncu.folder_of_seniors.module.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseFragment;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.activity.SearchActivity;
import ncu.folder_of_seniors.module.ui.adapter.FirstFAdapter;
import ncu.folder_of_seniors.module.ui.view.FirstFView;
import ncu.folder_of_seniors.presenter.FirstFPresenter;


public class FirstFragment extends BaseFragment implements FirstFAdapter.Callback, FirstFView {

    @BindView(R.id.f1_iv_search) ImageView iv_search;
    @BindView(R.id.f1_img_btn_it) ImageView btn_it;
    @BindView(R.id.f1_img_btn_english) ImageView btn_english;
    @BindView(R.id.f1_img_btn_math) ImageView btn_math;
    @BindView(R.id.f1_img_btn_politics) ImageView btn_politics;
    @BindView(R.id.f1_img_btn_life) ImageView btn_life;
    @BindView(R.id.f1_img_btn_other) ImageView btn_other;
    @BindView(R.id.f1_resource_lv) RecyclerView lv_resource;
    @BindView(R.id.f1_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @InjectPresenter private FirstFPresenter mPresenter;

    private List<Resource> mList = new ArrayList<>();
    private Intent intent;
    private FirstFAdapter adapter;

    public FirstFragment() { }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void init() {
        adapter = new FirstFAdapter(getActivity(), mList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        lv_resource.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置Adapter
        lv_resource.setAdapter(adapter);
        //设置分隔线
        //resource_lv.addItemDecoration( new DividerGridItemDecoration(this ));

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
        mPresenter.getData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @OnClick({R.id.f1_iv_search,R.id.f1_img_btn_it,
            R.id.f1_img_btn_math,R.id.f1_img_btn_english,
            R.id.f1_img_btn_politics,R.id.f1_img_btn_life,
            R.id.f1_img_btn_other})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.f1_iv_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","search");
                startActivity(intent);
                break;
            case R.id.f1_img_btn_it:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","it");
                startActivity(intent);
                break;
            case R.id.f1_img_btn_math:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","math");
                startActivity(intent);
                break;
            case R.id.f1_img_btn_english:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","english");
                startActivity(intent);
                break;
            case R.id.f1_img_btn_politics:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","politics");
                startActivity(intent);
                break;
            case R.id.f1_img_btn_life:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","life");
                startActivity(intent);
                break;
            case R.id.f1_img_btn_other:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type","other");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void click(View v) {

    }

    @Override
    public void showData(List<Resource> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getActivity(), "数据获取失败:"+msg, Toast.LENGTH_SHORT).show();
    }
}
