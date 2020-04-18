package ncu.folder_of_seniors.module.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Trace;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

import cn.bmob.v3.BmobUser;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.model.RegisterModel;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.view.LoginView;
import ncu.folder_of_seniors.module.ui.widget.SelectPopupWindow;
import ncu.folder_of_seniors.presenter.LoginPresenter;
import ncu.folder_of_seniors.presenter.impl.LoginPresenterImpl;
import ncu.folder_of_seniors.utils.ToastEx;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.app.MyApplication.fetchUserInfo;
import static ncu.folder_of_seniors.module.ui.fragment.FouthFragment.REGISTER_REQUEST_CODE;
import static ncu.folder_of_seniors.utils.StaticClass.IS_LOGIN;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.login_et_no) EditText et_no;
    @BindView(R.id.login_et_password) EditText et_password;
    @BindView(R.id.login_btnLogin) Button login;
    @BindView(R.id.login_btn_registered) Button register;
    @BindView(R.id.login_tv_forget) TextView forget;
    @InjectPresenter private LoginPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initData() { }

    @OnClick({R.id.login_btnLogin,R.id.login_btn_registered,
            R.id.login_tv_forget})
    public void onViewClick(View v){
        Intent i = new Intent();
        switch (v.getId()){
            case R.id.login_btnLogin:
                mPresenter.login();
                break;
            case R.id.login_btn_registered:
                i.setClass(getContext(), SelectPopupWindow.class);
                Bundle bundle = new Bundle();
                bundle.putString("btn1", "手机号注册");
                bundle.putString("btn2", "邮箱注册");
                bundle.putString("btn3", "取消");
                i.putExtra("tag", bundle);
                startActivityForResult(i,REGISTER_REQUEST_CODE);
                break;
            case R.id.login_tv_forget:
//                i.setClass(getContext(), WitnessQueryActivity.class);
//                startActivity(i);
                break;
        }
    }

    @Override
    public String getUserName() {
        return et_no.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void onLoginSeccess(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.SUCCESS,msg,Toast.LENGTH_LONG,new Point(0,0)).show();
        fetchUserInfo();
        finish();
    }

    @Override
    public void onLoginFails(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg,Toast.LENGTH_LONG,new Point(0,0)).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case REGISTER_REQUEST_CODE:
                    String returnedData = data.getStringExtra("data_return");
                    Intent intent;
                    Bundle bundle;
                    if (returnedData != null) {
                        switch (returnedData) {
                            case "手机号注册":
                                intent = new Intent();
                                bundle = new Bundle();
                                bundle.putString("type", "手机号注册");
                                intent.putExtra("tag", bundle);
                                intent.setClass(getContext(),RegisterActivity.class);
                                startActivity(intent);
                                break;
                            case "邮箱注册":
                                intent = new Intent();
                                bundle = new Bundle();
                                bundle.putString("type", "邮箱注册");
                                intent.putExtra("tag", bundle);
                                intent.setClass(getContext(),RegisterActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
            }
        }
    }
}
