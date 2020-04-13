package ncu.folder_of_seniors.module.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;


import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseFragment;


public class SecondFragment extends BaseFragment {
    private View view;
    private Context context;

//    @BindView(R.id.ll_second_lgsctj) LinearLayout ll_second_lgsctj;
//    @BindView(R.id.ll_second_lgtj) LinearLayout ll_second_lgtj;
//    @BindView(R.id.ll_second_lkxxtj) LinearLayout ll_second_lkxxtj;
//    @BindView(R.id.ll_second_rzltj) LinearLayout ll_second_rzltj;
//    @BindView(R.id.ll_second_scltj) LinearLayout ll_second_scltj;
    public SecondFragment() {
    }

    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void init() {

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
    }

//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_second;
//    }
//
//    @Override
//    protected void init() {
//
//    }
//
//    @OnClick({R.id.ll_second_lgsctj,R.id.ll_second_lgtj,
//            R.id.ll_second_lkxxtj,R.id.ll_second_rzltj,
//            R.id.ll_second_scltj,R.id.ll_second_rtstj})
//    public void onViewClick(View v){
//        Intent i = new Intent();
//        switch (v.getId()){
//            case R.id.ll_second_lgsctj:
//                i.setClass(getActivity(), StatHotelUploadActivity.class);
//                startActivity(i);
//                break;
//            case R.id.ll_second_lgtj:
//                i.setClass(getActivity(), StatHotelActivity.class);
//                startActivity(i);
//                break;
//            case R.id.ll_second_lkxxtj:
//                i.setClass(getActivity(), StatTouristActivity.class);
//                startActivity(i);
//                break;
//            case R.id.ll_second_rzltj:
//                i.setClass(getActivity(), StatCheckInRateActivity.class);
//                startActivity(i);
//                break;
//            case R.id.ll_second_scltj:
//                i.setClass(getActivity(), StatUploadRateActivity.class);
//                startActivity(i);
//                break;
//            case R.id.ll_second_rtstj:
//                i.setClass(getActivity(), StatCheckInOutActivity.class);
//                startActivity(i);
//                break;
//        }
//    }
}
