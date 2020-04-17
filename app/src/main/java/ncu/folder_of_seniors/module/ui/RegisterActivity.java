package ncu.folder_of_seniors.module.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.view.RegisterView;
import ncu.folder_of_seniors.presenter.RegisterPresenter;
import ncu.folder_of_seniors.utils.ToastEx;
import ncu.folder_of_seniors.utils.Verify;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.app.MyApplication.fetchUserInfo;
import static ncu.folder_of_seniors.utils.StaticClass.IS_LOGIN;

public class RegisterActivity extends BaseActivity implements RegisterView {

    @InjectPresenter
    private RegisterPresenter mPresenter;
    private String type;
    private Boolean sex=true;
    private boolean c=false;

    @BindView(R.id.register_toolbar4) Toolbar toolbar;
    @BindView(R.id.register_tv_info) TextView mTvInfo;
    @BindView(R.id.register_et_no) EditText no;
    @BindView(R.id.register_et_school) EditText et_school;
    @BindView(R.id.register_et_password) EditText password;
    @BindView(R.id.register_et_password2) EditText password2;
    @BindView(R.id.register_et_age) EditText Age;
    @BindView(R.id.register_et_SMScode) EditText smsCode;
    @BindView(R.id.register_et_name) EditText name;
    @BindView(R.id.register_radioButton_male) RadioButton male;
    @BindView(R.id.register_radioButton_female) RadioButton female;
    @BindView(R.id.register_btn_registered) Button register;
    @BindView(R.id.register_btn_sendSmscode) Button send;
    @BindView(R.id.register_ll_SMScode) LinearLayout ll_SMScode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    sex = true;
                }else{
                    sex = false;
                }
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    sex = false;
                }else{
                    sex = true;
                }
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("tag");
        type = bundle.getString("type");
        if(type.equals("手机号注册")){
            no.setHint("请输入手机号");
            ll_SMScode.setVisibility(View.VISIBLE);
        }else if(type.equals("邮箱注册")){
            no.setHint("请输入邮箱地址");
            ll_SMScode.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public String getType() {
        return type;
    }

    @Override
    public BmobUser getUser() {
        String phoneNo = no.getText().toString();
        String pwd= password.getText().toString();
        Integer age = Integer.parseInt(Age.getText().toString());
        String username = name.getText().toString();
        String mail = no.getText().toString();
        String school = et_school.getText().toString();
        User user = new User();
        if(type.equals("手机号注册")){
            user.setUsername(username);
            user.setPassword(pwd);
            user.setMobilePhoneNumber(phoneNo);
            user.setAge(age);
            user.setOnline(true);
            user.setSchool(school);
            user.setSex(sex);
            user.setPoints(0);
        }else if(type.equals("邮箱注册")){
            user.setUsername(username);
            user.setPassword(pwd);
            user.setEmail(mail);
            user.setAge(age);
            user.setOnline(true);
            user.setSchool(school);
            user.setSex(sex);
            user.setPoints(0);
        }
        return user;
    }

    @Override
    public void onRegisterSeccess(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.SUCCESS,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
        fetchUserInfo();
        finish();
    }

    @Override
    public void onRegisterFails(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }

    @Override
    public void onSmsSendSeccess(String msg) {
        mTvInfo.append(msg);
    }

    @Override
    public void onSmsSendFails(String msg) {
        mTvInfo.append(msg);
    }

    @OnClick({R.id.register_btn_sendSmscode,R.id.register_btn_registered})
    public void onViewClick(View v){
        switch (v.getId()) {
            case R.id.register_btn_sendSmscode:
                if (TextUtils.isEmpty(no.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "请输入手机号码！",
                            Toast.LENGTH_SHORT).show();
                }else if(!Verify.isMobile(no.getText().toString())){
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号码！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    mPresenter.sendSmsCode(no.getText().toString());
                }
                break;
            case R.id.register_btn_registered:
                String phoneNo = no.getText().toString();
                String pwd= password.getText().toString();
                String pwd2 = password2.getText().toString();
                String age = Age.getText().toString();
                String username = name.getText().toString();
                String smscode = smsCode.getText().toString();
                String school = et_school.getText().toString();
                String mail = no.getText().toString();
                if(type.equals("手机号注册")){
                    String msg = mPresenter.userVerifyWithPhone(username,pwd,pwd2,age,sex,phoneNo,smscode,school);
                    if(msg.equals("SUCCESS")){
                        mPresenter.registerWithPhone(smscode);
                    }else {
                        Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_SHORT).show();
                    }
                }else if(type.equals("邮箱注册")){
                    String msg = mPresenter.userVerifyWithMail(username,pwd,pwd2,age,sex,mail,school);
                    if(msg.equals("SUCCESS")){
                        mPresenter.registerWithEmail();
                    }else {
                        Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
