package ncu.folder_of_seniors.module.ui.activity;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.LaunchView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.LaunchPresenter;
import ncu.folder_of_seniors.utils.ToastEx;
import ncu.folder_of_seniors.utils.Utils;
import ncu.folder_of_seniors.utils.Verify;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;
import static com.yalantis.ucrop.util.FileUtils.getPath;
import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.module.ui.fragment.FouthFragment.FILE_REQUEST_CODE;

public class LaunchActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, LaunchView {

    @BindView(R.id.launch_bt_launch) Button bt_launch;
    @BindView(R.id.launch_et_title_launch) EditText et_title_launch;
    @BindView(R.id.launch_et_content_launch) EditText et_content_launch;
    @BindView(R.id.launch_iv_photo_launch) ImageView iv_photo_launch;
    @BindView(R.id.launch_btn_pickFile) Button btn_pickFile;
    @BindView(R.id.launch_et_price_launch) EditText et_price_launch;
    @BindView(R.id.launch_l_photo) LinearLayout l_photo;
    @BindView(R.id.launch_spinner_launch) Spinner spinner_launch;
    @BindView(R.id.simple_toolbar) SimpleToolBar simpleToolBar;
    @InjectPresenter private LaunchPresenter mPresenter;

    private int index;
    private int a = 0;
    private String[] list;
    private String mtitle;
    private String mcontent;
    private String mFilePath;
    private int mprice;
    private String mfenlei="程序员";
    private List<LocalMedia> selectList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initViews() {
        simpleToolBar.setLeftTitleDrawable(R.drawable.icon_back);
        simpleToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner_launch.setOnItemSelectedListener(this);
        switch (index){
            case 0:
                simpleToolBar.setMainTitle("发布资源");
                break;
            case 1:
                simpleToolBar.setMainTitle("发布资源");
                break;
            case 2:
                simpleToolBar.setMainTitle("拍卖资源");
                break;
            case 3:
                simpleToolBar.setMainTitle("免费送资源");
                break;
        }
    }

    @Override
    protected void initData() {
        Intent i = getIntent();
        index = Integer.parseInt(i.getStringExtra("index"));
        list = new String[]{"程序员","英语","数学","政治","生活","其他"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_launch.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.launch_btn_pickFile,R.id.launch_bt_launch,R.id.launch_iv_photo_launch})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.launch_iv_photo_launch:
                toPictures();
                break;
            case R.id.launch_btn_pickFile:
                toPickFile();
                break;
            case R.id.launch_bt_launch:
                if(mFilePath!=null){
                    Resource resource = new Resource();
                    resource.setCreator(clientUser);
                    if(Verify.isStrEmpty(et_title_launch.getText().toString())){
                        Toast.makeText(getContext(),"请添加标题!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(Verify.isStrEmpty(et_content_launch.getText().toString())){
                        Toast.makeText(getContext(),"请添加资源详情!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(Verify.isStrEmpty(et_price_launch.getText().toString())){
                        Toast.makeText(getContext(),"请添加所需积分!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!Verify.isPositiveInt(et_price_launch.getText().toString().trim())){
                        Toast.makeText(getContext(),"请输入正确的积分!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(selectList==null){
                        Toast.makeText(getContext(),"请至少选择一张图片！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Integer a = Integer.parseInt(et_price_launch.getText().toString().trim());
                    if(a>10){
                        Toast.makeText(getContext(),"资源定价暂时不能超过10",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resource.setDesc(et_content_launch.getText().toString());
                    resource.setTitle(et_title_launch.getText().toString());
                    resource.setPrice(Integer.parseInt(et_price_launch.getText().toString()));
                    resource.setType(mfenlei);
                    resource.setGrade(0.0);
                    resource.setBuyNo(0);
                    resource.setStarNo(0);
                    resource.setSchool(clientUser.getSchool());
                    resource.setLikes(0);
                    mPresenter.launch(resource);
                }else {
                    Toast.makeText(getContext(),"请选择文件上传!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (list[position]){
            case "程序员":
                mfenlei = "程序员";
                break;
            case "英语":
                mfenlei = "英语";
                break;
            case "数学":
                mfenlei = "数学";
                break;
            case "政治":
                mfenlei = "政治";
                break;
            case "生活":
                mfenlei = "生活";
                break;
            case "其他":
                mfenlei = "其他";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void toPictures() {
        PictureSelector.create(LaunchActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compress(true)// 是否压缩 true or false
                .compressMode(LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressMaxKB(50)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void toPickFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent,FILE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    a = 5;
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    ImageView[] imageViews = new ImageView[selectList.size()];
                   for (int i = 0; i < imageViews.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(Utils.dip2px(this,100), Utils.dip2px(this,100)));
                        Glide.with(this).load(selectList.get(i).getPath()).into(imageView);
                        //转换成字符流并添加到，集合
                        imageViews[i] = imageView;
                        String s = selectList.get(i).getPath();
                        l_photo.addView(imageView);
                    }

                    break;
                case FILE_REQUEST_CODE:
                    Uri uri = data.getData(); // 获取用户选择文件的URI
                    if (uri != null) {
                        String path = getPath(this, uri);
                        if (path != null) {
                            File file = new File(path);
                            if (file.exists()) {
                                String upLoadFilePath = file.toString();
                                String upLoadFileName = file.getName();
                                btn_pickFile.setText("已选择文件："+upLoadFileName);
                                mFilePath = upLoadFilePath;
                            }
                        }
                    }

                    break;
            }
        }
    }

    @Override
    public String getFilePath() {
        return mFilePath;
    }

    @Override
    public List<LocalMedia> getPicPath() {
        return selectList;
    }

    @Override
    public void onLoading() {
        showLoadingDialog();
    }

    @Override
    public void showSuccessMessage(String msg) {
        dismissLoadingDialog();
        ToastEx.init(getContext(), ToastEx.Type.SUCCESS,msg,Toast.LENGTH_LONG,new Point(0,0)).show();
        finish();
    }

    @Override
    public void showErrorMessage(String msg) {
        dismissLoadingDialog();
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg,Toast.LENGTH_LONG,new Point(0,0)).show();
    }

}
