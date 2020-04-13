package ncu.folder_of_seniors.module.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;


import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseFragment;
import ncu.folder_of_seniors.base.BaseView;

public class FouthFragment extends BaseFragment implements BaseView {
    private View view;
    private Context context;
//    private LoginFacade loginFacade;
//
//    /**
//     * 退出
//     */
//    @BindView(R.id.btnLogout) Button btnLogout;
//    @OnClick(R.id.btnLogout)
//    public void logout(View view){
//        switch (view.getId()){
//            case R.id.btnLogout:
//                loginFacade.logout(new OnNetResultListener() {
//                    @Override
//                    public void onSuccess(String result) {
//                        //TODO:添加一个弹出对话框，询问是否确定退出
//                        ClientApplication.exit();
//                    }
//                    @Override
//                    public void onFault(String errorMsg) {
//                        //TODO:添加一个弹出对话框，询问是否确定退出
//                        ClientApplication.exit();
//                    }
//                });
//
//        }
//    }

    public FouthFragment() {
    }

    public static FouthFragment newInstance(String param1, String param2) {
        FouthFragment fragment = new FouthFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fourth;
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
        initData();
    }

    public void onButtonPressed(Uri uri) {
    }

    private void initData() {

    }

    private void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
