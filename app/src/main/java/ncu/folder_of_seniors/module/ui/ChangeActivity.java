package ncu.folder_of_seniors.module.ui;

import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.ui.view.ChangeView;
import ncu.folder_of_seniors.presenter.ChangePresenter;
import ncu.folder_of_seniors.utils.ToastEx;
import ncu.folder_of_seniors.utils.Verify;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.app.MyApplication.fetchUserInfo;

public class ChangeActivity extends BaseActivity implements ChangeView {

    @BindView(R.id.change_et) EditText et_change;
    @BindView(R.id.change_tv) TextView tv_change;
    @BindView(R.id.change_tv_hold) TextView tv_change_hold;
    @BindView(R.id.change_iv_back) ImageView iv_back;
    @InjectPresenter private ChangePresenter mPresenter;

    private String type;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_change;
    }

    @Override
    protected void initViews() {
        switch (type) {
            case "username":
                tv_change.setText("更改昵称");
                et_change.setText(clientUser.getUsername());
                break;
            case "age":
                tv_change.setText("更改年龄");
                et_change.setText(clientUser.getAge()+"");
                break;
            case "school":
                tv_change.setText("更改学校");
                et_change.setText(clientUser.getSchool());
                break;
        }
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }

    @OnClick({R.id.change_iv_back,R.id.change_tv_hold})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.change_iv_back:
                finish();
                break;
            case R.id.change_tv_hold:
                switch (type) {
                    case "username":
                        String data = et_change.getText().toString();
                        if(Verify.isStrEmpty(data)){
                            Toast.makeText(getContext(),"请输入更改后的数据！",Toast.LENGTH_SHORT).show();
                        }else if(!Verify.isUserName(data)){
                            Toast.makeText(getContext(),"用户名由大小写字母开头，且不少于六个字符！",Toast.LENGTH_SHORT).show();
                        }else {
                            mPresenter.changeData(data,type);
                        }
                        break;
                    case "age":
                        String data2 = et_change.getText().toString();
                        if(Verify.isStrEmpty(data2)){
                            Toast.makeText(getContext(),"请输入更改后的数据！",Toast.LENGTH_SHORT).show();
                        }else if(!Verify.isAge(data2)){
                            Toast.makeText(getContext(),"请输入正确的年龄！",Toast.LENGTH_SHORT).show();
                        }else {
                            mPresenter.changeData(data2,type);
                        }
                        break;
                    case "school":
                        String data3 = et_change.getText().toString();
                        if(Verify.isStrEmpty(data3)){
                            Toast.makeText(getContext(),"请输入更改后的数据！",Toast.LENGTH_SHORT).show();
                        }else if(!Verify.isChinese(data3)){
                            Toast.makeText(getContext(),"请输入正确的学校全称",Toast.LENGTH_SHORT).show();
                        }else {
                            mPresenter.changeData(data3,type);
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void showSuccessMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.SUCCESS,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
        fetchUserInfo();
        finish();
    }

    @Override
    public void showErrorMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }
}
