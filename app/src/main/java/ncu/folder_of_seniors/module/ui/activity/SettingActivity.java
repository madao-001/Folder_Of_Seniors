package ncu.folder_of_seniors.module.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.module.entity.MessageEvent;
import ncu.folder_of_seniors.module.ui.fragment.FouthFragment;
import ncu.folder_of_seniors.module.ui.fragment.SecondFragment;
import ncu.folder_of_seniors.module.ui.fragment.ThirdFragment;
import ncu.folder_of_seniors.module.ui.view.FouthFView;
import ncu.folder_of_seniors.module.ui.view.SettingView;
import ncu.folder_of_seniors.module.ui.widget.SelectPopupWindow;
import ncu.folder_of_seniors.presenter.FouthFPresenter;
import ncu.folder_of_seniors.presenter.SettingPresenter;
import ncu.folder_of_seniors.utils.ToastEx;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.app.MyApplication.fetchUserInfo;
import static ncu.folder_of_seniors.utils.StaticClass.IS_LOGIN;

public class SettingActivity extends BaseActivity implements SettingView, FouthFView {

    @BindView(R.id.setting_tv_un_login) TextView tv_un_login;
    @BindView(R.id.setting_tv_username2) TextView tv_username;
    @BindView(R.id.setting_tv_age2) TextView tv_age;
    @BindView(R.id.setting_rl_username) RelativeLayout rl_username;
    @BindView(R.id.setting_rl_age) RelativeLayout rl_age;
    @BindView(R.id.setting_rl_icon) RelativeLayout rl_icon;
    @BindView(R.id.setting_iv_icon) ImageView iv_icon;
    @BindView(R.id.setting_rl_school) RelativeLayout rl_school;
    @BindView(R.id.setting_tv_school) TextView tv_school;
    @InjectPresenter private SettingPresenter mPresenter;
    @InjectPresenter private FouthFPresenter mPresenter2;

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

    }

    @Override
    protected void initData() {
        tv_username.setText(clientUser.getUsername());
        tv_age.setText(clientUser.getAge()+"");
        tv_school.setText(clientUser.getSchool());
        mPresenter2.showIcon();
    }

    @OnClick({R.id.setting_tv_un_login,R.id.setting_rl_username,
                R.id.setting_rl_age,R.id.setting_rl_icon,
            R.id.setting_rl_school})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.setting_rl_username:
                intent = new Intent(this, ChangeActivity.class);
                intent.putExtra("type","username");
                startActivity(intent);
                break;
            case R.id.setting_rl_age:
                intent = new Intent(this, ChangeActivity.class);
                intent.putExtra("type","age");
                startActivity(intent);
                break;
            case R.id.setting_rl_school:
                intent = new Intent(this, ChangeActivity.class);
                intent.putExtra("type","school");
                startActivity(intent);
                break;
            case R.id.setting_rl_icon:
                intent = new Intent(this, SelectPopupWindow.class);
                Bundle bundle = new Bundle();
                bundle.putString("btn1", "拍照");
                bundle.putString("btn2", "图库");
                bundle.putString("btn3", "取消");
                intent.putExtra("tag", bundle);
                startActivityForResult(intent,POPWINDOW_REQUEST_CODE);
                break;
            case R.id.setting_tv_un_login:
                mPresenter.logOut();
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
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
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

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.e("startPhotoZoom","uri == null");
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            iv_icon.setImageBitmap(bitmap);
            //保存
            mPresenter2.savePic(bitmap);
            Log.e("savePic","start");
        }
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void showData(int num1, int num2, int num3) {

    }

    @Override
    public void showIcon(String picPath) {
        if(picPath.equals("")||picPath == null){

        }else {
            Glide.with(getContext()).load(picPath).into(iv_icon);
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
    public void showSuccessMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.SUCCESS,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
        IS_LOGIN = false;
        BmobIM.getInstance().disConnect();
        onChangeDataInUI(FouthFragment.class.getName());
        onChangeDataInUI(SecondFragment.class.getName());
        onChangeDataInUI(ThirdFragment.class.getName());
        finish();
    }

    @Override
    public void showErrorMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }

    @Override
    public void getPicPath(String picPath) {
        mPresenter2.updateIcon(picPath);
        Log.e("savePic","start4"+picPath);
    }
}
