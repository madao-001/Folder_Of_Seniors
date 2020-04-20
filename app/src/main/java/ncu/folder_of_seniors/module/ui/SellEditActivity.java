package ncu.folder_of_seniors.module.ui;

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

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.SellEditView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.SellEditPresenter;
import ncu.folder_of_seniors.utils.ToastEx;
import ncu.folder_of_seniors.utils.Utils;
import ncu.folder_of_seniors.utils.Verify;

import static com.yalantis.ucrop.util.FileUtils.getPath;
import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.module.ui.fragment.FouthFragment.FILE_REQUEST_CODE;

public class SellEditActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, SellEditView {
    private Resource resource;
    private String mfenlei;
    private String[] list;
    private String objectId;

    @BindView(R.id.launch_bt_launch) Button bt_launch;
    @BindView(R.id.launch_et_title_launch) EditText et_title_launch;
    @BindView(R.id.launch_et_content_launch) EditText et_content_launch;
    @BindView(R.id.launch_iv_photo_launch) ImageView iv_photo_launch;
    @BindView(R.id.launch_btn_pickFile) Button btn_pickFile;
    @BindView(R.id.launch_et_price_launch) EditText et_price_launch;
    @BindView(R.id.launch_l_photo) LinearLayout l_photo;
    @BindView(R.id.launch_spinner_launch) Spinner spinner_launch;
    @BindView(R.id.simple_toolbar) SimpleToolBar simpleToolBar;
    @InjectPresenter
    private SellEditPresenter mPresenter;

    private String mFilePath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initViews() {
        simpleToolBar.setMainTitle("编辑资源信息");
        simpleToolBar.setLeftTitleDrawable(R.drawable.icon_back);
        simpleToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner_launch.setOnItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        if(resource==null){
            Intent intent=getIntent();
            objectId=intent.getStringExtra("objectId");

            list = new String[]{"程序员","英语","数学","政治","生活","其他"};
            bt_launch.setText("确认更改");
            ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_launch.setAdapter(adapter);
            mPresenter.getResource(objectId);
        }else {
            et_title_launch.setText(resource.getTitle());
            et_content_launch.setText(resource.getDesc());
            et_price_launch.setText(resource.getPrice()+"");
            mfenlei = resource.getType();
            for(int i = 0;i<list.length;i++){
                if(resource.getType().equals(list[i])){
                    spinner_launch.setSelection(i,true);
                }
            }
            btn_pickFile.setText("已选择文件："+resource.getFile().getFilename());
            mFilePath = resource.getFile().getFileUrl();
            ImageView[] imageViews = new ImageView[resource.getPhotos().size()];
            for (int i = 0; i < imageViews.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(Utils.dip2px(this,100), Utils.dip2px(this,100)));
                Glide.with(this).load(resource.getPhotos().get(i)).into(imageView);
                //转换成字符流并添加到，集合
                imageViews[i] = imageView;
                l_photo.addView(imageView);
            }
        }

    }


    @OnClick({R.id.launch_btn_pickFile,R.id.launch_bt_launch,R.id.launch_iv_photo_launch})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.launch_iv_photo_launch:
                Toast.makeText(getContext(),"暂时不能修改图片!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.launch_btn_pickFile:
                toPickFile();
                break;
            case R.id.launch_bt_launch:
                if(mFilePath!=null){
                    if(Verify.isStrEmpty(et_title_launch.getText().toString())){
                        Toast.makeText(getContext(),"请添加标题!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(Verify.isStrEmpty(et_content_launch.getText().toString())){
                        Toast.makeText(getContext(),"请添加资源详情!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(Verify.isStrEmpty(et_price_launch.getText().toString())){
                        Toast.makeText(getContext(),"请添加所需积分!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!Verify.isNum(et_price_launch.getText().toString().trim())){
                        Toast.makeText(getContext(),"请输入正确的积分!",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!Verify.isNum(et_price_launch.getText().toString().trim())){
                        Toast.makeText(getContext(),"请输入正确的积分!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resource.setValue("desc",et_content_launch.getText().toString());
                    resource.setValue("title",et_title_launch.getText().toString());
                    resource.setValue("price",Integer.parseInt(et_price_launch.getText().toString()));
                    resource.setValue("type",mfenlei);
                    resource.setValue("school",clientUser.getSchool());
                    mPresenter.updateResource(resource);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getFilePath() {
        return mFilePath;
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
    public void showResource(Resource resource) {
        this.resource = resource;
        initData();
    }

    @Override
    public void showErrorMessage(String msg) {
        dismissLoadingDialog();
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg,Toast.LENGTH_LONG,new Point(0,0)).show();
    }
}
