package ncu.folder_of_seniors.module.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseFragment;
import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Conversation;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.event.RefreshEvent;
import ncu.folder_of_seniors.module.ui.adapter.FirstFAdapter;
import ncu.folder_of_seniors.module.ui.adapter.ThirdFAdapter;
import ncu.folder_of_seniors.module.ui.view.ThirdFView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.SearchPresenter;
import ncu.folder_of_seniors.presenter.ThirdFPresenter;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class ThirdFragment extends BaseFragment implements ThirdFView {

    private List<Conversation> mList = new ArrayList<>();
    private ThirdFAdapter adapter;
    private int CurrentSel;

    @BindView(R.id.f3_recyclerView) RecyclerView f3_recyclerView;
    @BindView(R.id.f3_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @InjectPresenter ThirdFPresenter mPresenter;

    public ThirdFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_third;
    }

    @Override
    protected void init() {
        adapter = new ThirdFAdapter(getActivity(),mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        f3_recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置Adapter
        f3_recyclerView.setAdapter(adapter);
        registerForContextMenu(f3_recyclerView);
        adapter.setOnItemClickListener(new ThirdFAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.getItem(position).onClick(getActivity());
            }
        });
        adapter.setOnItemLongClickListener(new ThirdFAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                CurrentSel=position;
                f3_recyclerView.showContextMenu();
            }
        });

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,0,0, R.string.delete_conversation);
        super.onCreateContextMenu(menu,v,menuInfo);
    }

    @Override
    protected void initData() {

    }

    public void initData2(){
        if(clientUser!=null){
            mPresenter.showData();
        }else
            Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                adapter.getItem(CurrentSel).onLongClick(getActivity());
                adapter.remove(CurrentSel);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
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

    @Override
    public void showData(List<Conversation> conversationList) {
        mList.clear();
        Collections.sort(conversationList);
        mList.addAll(conversationList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData2();
    }

    /**注册自定义消息接收事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(RefreshEvent event){
        Log.e("ThirdFragment","---会话页接收到自定义消息---");
        initData2();
    }

    /**注册离线消息接收事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event){
        //重新刷新列表
        initData2();
    }

    /**注册消息接收事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event){
        //重新获取本地消息并刷新列表
        initData2();
    }
}
