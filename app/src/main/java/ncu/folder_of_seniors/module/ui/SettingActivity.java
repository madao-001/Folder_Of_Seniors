package ncu.folder_of_seniors.module.ui;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.ui.view.SettingView;
import ncu.folder_of_seniors.module.ui.widget.SelectPopupWindow;
import ncu.folder_of_seniors.presenter.SettingPresenter;
import ncu.folder_of_seniors.utils.ToastEx;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.utils.StaticClass.IS_LOGIN;

public class SettingActivity extends BaseActivity implements SettingView {

    @BindView(R.id.setting_tv_un_login) TextView tv_un_login;
    @BindView(R.id.setting_tv_username2) TextView tv_username;
    @BindView(R.id.setting_tv_age2) TextView tv_age;
    @BindView(R.id.setting_rl_username) RelativeLayout rl_username;
    @BindView(R.id.setting_rl_age) RelativeLayout rl_age;
    @BindView(R.id.setting_rl_icon) RelativeLayout rl_icon;
    @BindView(R.id.setting_iv_icon) ImageView iv_icon;
    @InjectPresenter
    private SettingPresenter mPresenter;
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    public static final int POPWINDOW_REQUEST_CODE=103;
    private File tempFile = null;
    private Boolean b;
    private Intent intent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        tv_username.setText(clientUser.getUsername());
        tv_age.setText(clientUser.getAge()+"");
//        if (user.getImage()!=null){
//            UtilTools.getImage(context,iv_header,user.getImage());
//        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.setting_tv_un_login,R.id.setting_rl_username,
                R.id.setting_rl_age,R.id.setting_rl_icon})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.setting_rl_username:
                intent = new Intent(this, ChangeActivity.class);
                intent.putExtra("type","nickName");
                startActivity(intent);
                break;
            case R.id.setting_rl_age:
                intent = new Intent(this, ChangeActivity.class);
                intent.putExtra("type","age");
                startActivity(intent);
                break;
            case R.id.setting_rl_icon:
                intent.setClass(getContext(), SelectPopupWindow.class);
                Bundle bundle = new Bundle();
                bundle.putString("btn1", "拍照");
                bundle.putString("btn2", "相册");
                bundle.putString("btn3", "取消");
                intent.putExtra("tag", bundle);
                startActivityForResult(intent,POPWINDOW_REQUEST_CODE);
                break;
            case R.id.setting_tv_un_login:
                mPresenter.logOut();
                IS_LOGIN = false;
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case POPWINDOW_REQUEST_CODE:
                    String returnedData = data.getStringExtra("data_return");
                    if (returnedData != null) {
                        switch (returnedData) {
                            case "拍照":
                                toCamera();
                                break;
                            case "图库":
                                toPicture();
                                break;
                            default:
                                break;
                        }
                    }
                    break;
//                case IMAGE_REQUEST_CODE:
//                    startPhotoZoom(data.getData());
//                    break;
//                //相机数据
//                case CAMERA_REQUEST_CODE:
//                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
//                    startPhotoZoom(Uri.fromFile(tempFile));
//                    break;
//                case RESULT_REQUEST_CODE:
//                    //有可能点击舍弃
//                    if (data != null) {
//                        //拿到图片设置
//                        setImageToView(data);
//                        //既然已经设置了图片，我们原先的就应该删除
//                        if (tempFile != null) {
//                            tempFile.delete();
//                        }
//                    }
                default:
            }
        }
    }

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
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
    protected void onResume() {
        super.onResume();
        initViews();
        initData();
    }

    @Override
    public void showSuccessMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.SUCCESS,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }

    @Override
    public void showErrorMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }
}
