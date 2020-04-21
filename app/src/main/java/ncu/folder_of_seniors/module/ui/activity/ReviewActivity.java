package ncu.folder_of_seniors.module.ui.activity;

import android.content.Intent;
import android.graphics.Point;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.Reviews;
import ncu.folder_of_seniors.module.ui.view.ReviewView;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.ReviewPresenter;
import ncu.folder_of_seniors.utils.ToastEx;
import ncu.folder_of_seniors.utils.Utils;
import ncu.folder_of_seniors.utils.Verify;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;
import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class ReviewActivity extends BaseActivity implements ReviewView {
    private Resource resource;
    private String objectId;
    private Integer grade=0;
    private int a = 0;
    private Reviews reviews=new Reviews();
    private List<LocalMedia> selectList = new ArrayList<>();

    @BindView(R.id.review_iv_picture) ImageView iv_picture;
    @BindView(R.id.review_iv_photo_launch) ImageView iv_photo_launch;
    @BindView(R.id.review_iv_star1) ImageView iv_star1;
    @BindView(R.id.review_iv_star2) ImageView iv_star2;
    @BindView(R.id.review_iv_star3) ImageView iv_star3;
    @BindView(R.id.review_iv_star4) ImageView iv_star4;
    @BindView(R.id.review_iv_star5) ImageView iv_star5;
    @BindView(R.id.review_et_content) EditText et_cotent;
    @BindView(R.id.review_bt_launch) Button bt_launch;
    @BindView(R.id.review_ll_photo) LinearLayout ll_photo;
    @BindView(R.id.simple_toolbar) SimpleToolBar simpleToolBar;
    @InjectPresenter
    private ReviewPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_review;
    }

    @Override
    protected void initViews() {
        simpleToolBar.setMainTitle("评价晒单");
        simpleToolBar.setLeftTitleDrawable(R.drawable.icon_back);
        simpleToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        objectId=intent.getStringExtra("objectId");
        mPresenter.getResource(objectId);
    }


    @OnClick({R.id.review_iv_star1,R.id.review_iv_star2,
            R.id.review_iv_star3,R.id.review_iv_star4,
            R.id.review_iv_star5,R.id.review_iv_photo_launch,
            R.id.review_bt_launch})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.review_iv_photo_launch:
                toPictures();
                break;
            case R.id.review_iv_star1:
                onStarClick(1);
                break;
            case R.id.review_iv_star2:
                onStarClick(2);
                break;
            case R.id.review_iv_star3:
                onStarClick(3);
                break;
            case R.id.review_iv_star4:
                onStarClick(4);
                break;
            case R.id.review_iv_star5:
                onStarClick(5);
                break;
            case R.id.review_bt_launch:
                if(Verify.isStrEmpty(et_cotent.getText().toString()))
                    reviews.setDesc("");
                else
                    reviews.setDesc(et_cotent.getText().toString());
                reviews.setUser(clientUser);
                reviews.setResource(resource);
                reviews.setGrade(grade);
                mPresenter.launch(reviews);
        }
    }

    private void toPictures() {
        PictureSelector.create(ReviewActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compress(true)// 是否压缩 true or false
                .compressMode(LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressMaxKB(50)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                .forResult(PictureConfig.CHOOSE_REQUEST);
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
                        ll_photo.addView(imageView);
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

    public void onStarClick(int num){
        switch (num){
            case 1:
                iv_star1.setImageResource(R.mipmap.star_on);
                iv_star2.setImageResource(R.mipmap.star_off);
                iv_star3.setImageResource(R.mipmap.star_off);
                iv_star4.setImageResource(R.mipmap.star_off);
                iv_star5.setImageResource(R.mipmap.star_off);
                grade = 2;
                break;
            case 2:
                iv_star1.setImageResource(R.mipmap.star_on);
                iv_star2.setImageResource(R.mipmap.star_on);
                iv_star3.setImageResource(R.mipmap.star_off);
                iv_star4.setImageResource(R.mipmap.star_off);
                iv_star5.setImageResource(R.mipmap.star_off);
                grade = 4;
                break;
            case 3:
                iv_star1.setImageResource(R.mipmap.star_on);
                iv_star2.setImageResource(R.mipmap.star_on);
                iv_star3.setImageResource(R.mipmap.star_on);
                iv_star4.setImageResource(R.mipmap.star_off);
                iv_star5.setImageResource(R.mipmap.star_off);
                grade = 6;
                break;
            case 4:
                iv_star1.setImageResource(R.mipmap.star_on);
                iv_star2.setImageResource(R.mipmap.star_on);
                iv_star3.setImageResource(R.mipmap.star_on);
                iv_star4.setImageResource(R.mipmap.star_on);
                iv_star5.setImageResource(R.mipmap.star_off);
                grade = 8;
                break;
            case 5:
                iv_star1.setImageResource(R.mipmap.star_on);
                iv_star2.setImageResource(R.mipmap.star_on);
                iv_star3.setImageResource(R.mipmap.star_on);
                iv_star4.setImageResource(R.mipmap.star_on);
                iv_star5.setImageResource(R.mipmap.star_on);
                grade = 10;
                break;
        }
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
    public void showResource(Resource resource) {
        this.resource = resource;
        Glide.with(this).load(resource.getPhotos().get(0)).into(iv_picture);
    }

    @Override
    public void showErrorMessage(String msg) {
        dismissLoadingDialog();
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg,Toast.LENGTH_LONG,new Point(0,0)).show();
    }
}
