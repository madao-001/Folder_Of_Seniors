package ncu.folder_of_seniors.module.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
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
import de.hdodenhof.circleimageview.CircleImageView;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseFragment;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.ui.FollowListActivity;
import ncu.folder_of_seniors.module.ui.LoginActivity;
import ncu.folder_of_seniors.module.ui.MyBoughtActivity;
import ncu.folder_of_seniors.module.ui.MyLaunchActivity;
import ncu.folder_of_seniors.module.ui.MySelledActivity;
import ncu.folder_of_seniors.module.ui.MyStarActivity;
import ncu.folder_of_seniors.module.ui.RegisterActivity;
import ncu.folder_of_seniors.module.ui.SettingActivity;
import ncu.folder_of_seniors.module.ui.adapter.MySelledAdapter;
import ncu.folder_of_seniors.module.ui.view.FouthFView;
import ncu.folder_of_seniors.module.ui.widget.MyScrollView;
import ncu.folder_of_seniors.module.ui.widget.SelectPopupWindow;
import ncu.folder_of_seniors.presenter.FouthFPresenter;
import ncu.folder_of_seniors.utils.ToastEx;

import static android.app.Activity.RESULT_CANCELED;
import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.utils.StaticClass.IS_LOGIN;

public class FouthFragment extends BaseFragment implements FouthFView {
    private View view;
    private AlertDialog alertDialog;
    private File tempFile = null;
    @InjectPresenter private FouthFPresenter mPresenter;

    @BindView(R.id.f4_tv_register) TextView tv_register;
    @BindView(R.id.f4_tv_login) TextView tv_login;
    @BindView(R.id.f4_my_scroll_view) MyScrollView my_scroll_view;
    @BindView(R.id.f4_divider0) View divider0;
    @BindView(R.id.f4_iv_settings) ImageView iv_settings;
    @BindView(R.id.f4_iv_icon) CircleImageView iv_icon;
    @BindView(R.id.f4_tv_nickname) TextView tv_nickname;
    @BindView(R.id.f4_rl_un_login) RelativeLayout rl_un_login;
    @BindView(R.id.f4_rl_login) RelativeLayout rl_login;
    @BindView(R.id.f4_iv_following) ImageView iv_following;
    @BindView(R.id.f4_iv_followers) ImageView iv_followers;
    @BindView(R.id.f4_tv_following) TextView tv_following;
    @BindView(R.id.f4_my_star) TextView my_star;
    @BindView(R.id.f4_tv_followers) TextView tv_followers;
    @BindView(R.id.f4_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.f4_tv_points) TextView tv_points;
    @BindView(R.id.f4_add_points) TextView add_points;
    @BindView(R.id.f4_my_selled) TextView my_selled;
    @BindView(R.id.f4_my_launch) TextView my_launch;
    @BindView(R.id.f4_my_buy) TextView my_buy;
    @BindView(R.id.f4_security) TextView security;
    @BindView(R.id.f4_tv_setting) TextView tv_setting;

    private float alphaHeight = 0;//透明度渐变的高度
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    public static final int FILE_REQUEST_CODE = 106;
    public static final int POPWINDOW_REQUEST_CODE=103;
    public static final int LOGIN_REQUEST_CODE=104;
    public static final int REGISTER_REQUEST_CODE=105;
    public FouthFragment() { }

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
        if(IS_LOGIN){
            rl_login.setVisibility(View.VISIBLE);
            rl_un_login.setVisibility(View.GONE);
            iv_settings.setVisibility(View.VISIBLE);

        }else {
            rl_un_login.setVisibility(View.VISIBLE);
            rl_login.setVisibility(View.GONE);
            iv_settings.setVisibility(View.GONE);
        }
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);   //设置下拉刷新进度条的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                initData();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        if(IS_LOGIN){
            tv_nickname.setText(clientUser.getUsername());
            tv_points.setText(clientUser.getPoints()+"积分");
            mPresenter.showData();
            mPresenter.showIcon();
        }else {
            tv_points.setText("积分");
            tv_following.setText("关注");
            tv_followers.setText("粉丝");
        }
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

    @OnClick({R.id.f4_iv_settings,R.id.f4_tv_login,
            R.id.f4_tv_register,R.id.f4_my_scroll_view,
            R.id.f4_iv_icon,R.id.f4_iv_followers,
            R.id.f4_iv_following,R.id.f4_my_launch,
            R.id.f4_my_buy,R.id.f4_my_selled,
            R.id.f4_my_star,R.id.f4_security,
            R.id.f4_tv_setting,R.id.f4_add_points})
    public void onViewClick(View v){
        Intent i = new Intent();
        switch (v.getId()){
            case R.id.f4_iv_settings:
                i.setClass(getActivity(), SettingActivity.class);
                startActivity(i);
                break;
            case R.id.f4_tv_login:
                i.setClass(getActivity(), LoginActivity.class);
                startActivity(i);
                break;
            case R.id.f4_tv_register:
                i.setClass(getActivity(), SelectPopupWindow.class);
                Bundle bundle = new Bundle();
                bundle.putString("btn1", "手机号注册");
                bundle.putString("btn2", "邮箱注册");
                bundle.putString("btn3", "取消");
                i.putExtra("tag", bundle);
                startActivityForResult(i,REGISTER_REQUEST_CODE);
                break;
            case R.id.f4_my_scroll_view:
//                i.setClass(getActivity(), NoLicenseQueryActivity.class);
                startActivity(i);
                break;

            case R.id.f4_iv_icon:
                i.setClass(getActivity(), SelectPopupWindow.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("btn1", "图库");
                bundle2.putString("btn2", "拍照");
                bundle2.putString("btn3", "取消");
                i.putExtra("tag", bundle2);
                startActivityForResult(i,POPWINDOW_REQUEST_CODE);
                break;
            case R.id.f4_iv_followers:
                if(IS_LOGIN){
                    i.setClass(getActivity(), FollowListActivity.class);
                    i.putExtra("isFollowing",false);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.f4_iv_following:
                if(IS_LOGIN){
                    i.setClass(getActivity(), FollowListActivity.class);
                    i.putExtra("isFollowing",true);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.f4_my_launch:
                if(IS_LOGIN){
                    i.setClass(getActivity(), MyLaunchActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.f4_my_buy:
                if(IS_LOGIN){
                    i.setClass(getActivity(), MyBoughtActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.f4_my_selled:
                if(IS_LOGIN){
                    i.setClass(getActivity(), MySelledActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.f4_security:
                Toast.makeText(getContext(),"该功能还在开发中，尽请期待！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.f4_my_star:
                if(IS_LOGIN){
                    i.setClass(getActivity(), MyStarActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.f4_tv_setting:
                Toast.makeText(getContext(),"该功能还在开发中，尽请期待！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.f4_add_points:
////                i.setClass(getActivity(), MoreLicenseQueryActivity.class);
//                startActivity(i);
                break;
        }
    }

    public void showLoadingDialog() {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });
        alertDialog.show();
        alertDialog.setContentView(R.layout.loading_alert);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
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
                                intent.setClass(getActivity(),RegisterActivity.class);
                                startActivity(intent);
                                break;
                            case "邮箱注册":
                                intent = new Intent();
                                bundle = new Bundle();
                                bundle.putString("type", "邮箱注册");
                                intent.putExtra("tag", bundle);
                                intent.setClass(getActivity(),RegisterActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case POPWINDOW_REQUEST_CODE:
                    String returnedData2 = data.getStringExtra("data_return");
                    Intent intent2;
                    Bundle bundle2;
                    if (returnedData2 != null) {
                        switch (returnedData2) {
                            case "图库":
                                toPicture();
                                break;
                            case "拍照":
                                toCamera();
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
                    break;
                default:
            }
        }
    }


    @Override
    public void showData(int num1, int num2,int num3) {
        tv_following.setText(num1+"关注");
        tv_followers.setText(num2+"粉丝");
        tv_points.setText(num3+"积分");
    }

    @Override
    public void showIcon(String picPath) {
        if(picPath.equals("")||picPath == null){

        }else {
            Glide.with(getContext()).load(picPath).into(iv_icon);
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
            mPresenter.savePic(bitmap);
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
    public void showErrorMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }

    @Override
    public void getPicPath(String picPath) {
        mPresenter.updateIcon(picPath);
        Log.e("savePic","start4"+picPath);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        init();
    }
}
