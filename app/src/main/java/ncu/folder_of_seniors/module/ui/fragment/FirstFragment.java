package ncu.folder_of_seniors.module.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseFragment;
import ncu.folder_of_seniors.module.entity.Resource;


public class FirstFragment extends BaseFragment {
    private Context context;

    @BindView(R.id.f1_iv_search) ImageView iv_search;
    @BindView(R.id.f1_img_btn_it) ImageView ll_jwlkcx;
    @BindView(R.id.f1_img_btn_english) ImageView ll_rzhycx;
    @BindView(R.id.f1_img_btn_math) ImageView ll_wzhycx;
    @BindView(R.id.f1_img_btn_politics) ImageView ll_lgcx;
    @BindView(R.id.f1_img_btn_life) ImageView ll_yazlgcx;
    @BindView(R.id.f1_img_btn_other) ImageView ll_wbzxxcx;
    @BindView(R.id.f1_resource_lv) RecyclerView resource_lv;
    @BindView(R.id.f1_swipe_refresh) SwipeRefreshLayout swipeRefresh;

    private List<Resource> mList = new ArrayList<>();
    private Intent intent;

    public FirstFragment() { }

    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
    }

//    @Override
////    public int getLayoutId() {
////        return R.layout.fragment_first;
////    }
////
////    @Override
////    public void init() {
////        publicFacade=new PublicFacadeImpl(this.getContext());
////    }
////
////    public void onButtonPressed(Uri uri) {
////    }
////
////    private void initData() {
////        bindViewPager();
////    }
////
////    @OnClick({R.id.ll_first_jnlkcx,R.id.ll_first_jwlkcx,
////              R.id.ll_first_rzhycx,R.id.ll_first_wzhycx,
////              R.id.ll_first_lgcx,R.id.ll_first_yazlgcx,
////              R.id.ll_first_wbzxxcx,R.id.ll_first_jnbjcx,
////              R.id.ll_first_jnbjtj,R.id.ll_first_cjgl,
////              R.id.ll_first_bkgl,R.id.ll_first_zdrycx,
////              R.id.ll_first_zdryyj,R.id.ll_first_yzdzcx})
////    public void onViewClick(View v){
////        Intent i = new Intent();
////        switch (v.getId()){
////            case R.id.ll_first_jnlkcx:
////                i.setClass(getActivity(), ChTouristQueryActivity.class);
////                startActivity(i);
////                break;
////            case R.id.ll_first_jwlkcx:
////                i.setClass(getActivity(), FgTouristQueryActivity.class);
////                startActivity(i);
////                break;
////            case R.id.ll_first_rzhycx:
////                i.setClass(getActivity(), WitnessQueryActivity.class);
////                startActivity(i);
////                break;
////            case R.id.ll_first_wzhycx:
////                i.setClass(getActivity(), NoLicenseQueryActivity.class);
////                startActivity(i);
////                break;
////
////            case R.id.ll_first_lgcx:
////                i.setClass(getActivity(), HotelQueryActivity.class);
////                startActivity(i);
////                break;
////            case R.id.ll_first_yazlgcx:
////                i.setClass(getActivity(), HasInstalledHotelQueryActivity.class);
////                startActivity(i);
////                break;
////            case R.id.ll_first_wbzxxcx:
////                i.setClass(getActivity(), MissHotelQueryActivity.class);
////                startActivity(i);
////                break;
////            case R.id.ll_first_jnbjcx:
////                i.setClass(getActivity(), AlarmsQueryActivity.class);
////                startActivity(i);
////                break;
////
////            case R.id.ll_first_jnbjtj:
////                i.setClass(getActivity(), StatAlarmsActivity.class);
////                startActivity(i);
////                break;
//////            case R.id.ll_first_cjgl:
//////                i.putExtra("type","cjgl");
//////                startActivity(i);
//////                break;
//////            case R.id.ll_first_bkgl:
//////                i.putExtra("type","bkgl");
//////                startActivity(i);
//////                break;
////            case R.id.ll_first_zdrycx:
////                i.setClass(getActivity(), KeyPersonQueryActivity.class);
////                startActivity(i);
////                break;
////
//////            case R.id.ll_first_zdryyj:
//////                i.putExtra("type","zdryyj");
//////                startActivity(i);
//////                break;
////            case R.id.ll_first_yzdzcx:
////                i.setClass(getActivity(), MoreLicenseQueryActivity.class);
////                startActivity(i);
////                break;
////        }
////    }
////
////    private void bindViewPager() {
////
////        imgUrls.clear();
////        imgUrls.add("http://www.jxga.gov.cn/d/file/news/gonganyaowen/20190718/7e2ff462d07d46e3ea7a692316a20ee8.jpg");
////        imgUrls.add("http://www.jxga.gov.cn/d/file/news/gonganyaowen/20190524/fbdce3e9e8f49fb1bf9b595facab9a01.jpg");
////        imgUrls.add("http://www.jxga.gov.cn/d/file/news/gonganyaowen/20190611/fe21281d963f7ac035e58e5ca44edd93.jpg");
////        autoViewPager.setUpWithImages(imgUrls);
////    }
}
