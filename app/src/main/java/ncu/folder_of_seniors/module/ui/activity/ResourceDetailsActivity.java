package ncu.folder_of_seniors.module.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.InjectPresenter;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.view.ResourceDetailsView;
import ncu.folder_of_seniors.module.ui.widget.MyDialog;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.presenter.ResourceDetailsPresenter;
import ncu.folder_of_seniors.utils.ToastEx;
import ncu.folder_of_seniors.utils.Utils;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class ResourceDetailsActivity extends BaseActivity implements ResourceDetailsView {
    private String objectId;
    private Resource resource;
    private User seller;
    private int seller_point;
    private boolean isLike=false;
    private boolean isStar=false;
    private boolean isBuy=false;

    @BindView(R.id.simple_toolbar) SimpleToolBar simpleToolBar;
    @BindView(R.id.resource_like_click) ImageView iv_like;
    @BindView(R.id.resource_star_click) ImageView iv_star;
    @BindView(R.id.resource_message_click) ImageView iv_message;
    @BindView(R.id.resource_want_click) Button btn_want;
    @BindView(R.id.resource_photo) LinearLayout photo_list;
    @BindView(R.id.resource_seller_username) TextView tv_sellerName;
    @BindView(R.id.resource_time) TextView tv_time;
    @BindView(R.id.resource_price) TextView tv_price;
    @BindView(R.id.resource_content) TextView tv_content;
    @BindView(R.id.resource_seller_icon) ImageView iv_icon;
    @InjectPresenter
    private ResourceDetailsPresenter mPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_resource_details;
    }

    @Override
    protected void initViews() {
        //设置toolbar
        simpleToolBar.setLeftTitleDrawable(R.drawable.icon_back);
        simpleToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(resource!=null){
            simpleToolBar.setMainTitle(resource.getTitle());
            tv_price.setText(resource.getPrice()+"");
            tv_time.setText("发布于 "+resource.getCreatedAt());
            tv_content.setText(resource.getDesc());
            tv_sellerName.setText(seller.getUsername());
            Glide.with(getContext()).load(seller.getIcon()).into(iv_icon);
            if(resource.getPhotos().size()!=0) {
                int a = resource.getPhotos().size();
                photo_list.removeAllViews();
                for (int j = 0; j < a; j++) {
                                                /*
                                                动态添加会导致一个问题，重复添加，导致内存泄漏
                                                所以为了防止重复添加，现将所有的imageview移除，清空资源，再添加
                                                 */
                    final int num = j;
                    Glide.with(getContext()).asBitmap().load(resource.getPhotos().get(j)).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();

                            int width2 = Utils.getWidth(getContext())-36;
                            float height3 = ((float)width2/(float)width)*(float)height;
                            int height2 = (int)height3;

                            //Toast.makeText(SellDetailsActivity.this, width+","+height+"  "+width2+","+height2, Toast.LENGTH_SHORT).show();
                            final ImageView imageView = new ImageView(getContext());
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(width2,height2));
                            //imageView.setAdjustViewBounds(true);
                            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            Glide.with(getContext()).load(resource.getPhotos().get(num)).into(imageView);
                            photo_list.addView(imageView);
                        }
                    });
                }
            }
            if(isLike){
                iv_like.setImageResource(R.drawable.supportlater);
            }if(isStar){
                iv_star.setImageResource(R.drawable.collectionlater);
            }
            if(isBuy){
                btn_want.setText("已购买");
                btn_want.setBackgroundColor(btn_want.getResources().getColor(R.color.colorPrimary));
                btn_want.setTextColor(btn_want.getResources().getColor(R.color.text_gray));
            }
        }

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        mPresenter.getData(objectId);
    }

    @OnClick({R.id.resource_like_click,R.id.resource_star_click,
            R.id.resource_message_click,R.id.resource_want_click,
            R.id.resource_seller_icon})
    public void onClick(View v){
            switch (v.getId()){
                case R.id.resource_like_click:
                    if(clientUser!=null){
                        if(isLike){
                            Toast.makeText(getContext(),"取消了喜欢！", Toast.LENGTH_SHORT).show();
                            mPresenter.setUserAction(resource,clientUser,"like",false);
                        }else {
                            Toast.makeText(getContext(),"点击了喜欢！！", Toast.LENGTH_SHORT).show();
                            mPresenter.setUserAction(resource,clientUser,"like",true);
                        }
                    }else {
                        Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.resource_star_click:
                    if(clientUser!=null){
                        if(isStar){
                            Toast.makeText(getContext(),"取消了收藏！", Toast.LENGTH_SHORT).show();
                            mPresenter.setUserAction(resource,clientUser,"star",false);
                        }else {
                            Toast.makeText(getContext(),"点击了收藏！！", Toast.LENGTH_SHORT).show();
                            mPresenter.setUserAction(resource,clientUser,"star",true);
                        }
                    }else {
                        Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.resource_message_click:
                    if(clientUser!=null){
                        if(clientUser.getObjectId().equals(resource.getCreator().getObjectId())){
                            Toast.makeText(getContext(),"不可以和自己聊天！", Toast.LENGTH_SHORT).show();
                        }else {
                            BmobIMUserInfo info = new BmobIMUserInfo(resource.getCreator().getObjectId(), resource.getCreator().getUsername(), resource.getCreator().getIcon());
                            BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("c", conversationEntrance);
                            Intent intent = new Intent();
                            intent.setClass(getContext(), ChatActivity.class);
                            intent.putExtra("chat", bundle);
                            startActivity(intent);
                        }
                    }else {
                        Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.resource_want_click:
                    if(clientUser!=null){
                        if(seller.getObjectId().equals(clientUser.getObjectId())){
                            Toast.makeText(getContext(),"不可购买自己的发布！", Toast.LENGTH_SHORT).show();
                        }else {
                            if(isBuy){
                                Toast.makeText(getContext(),"不可重复购买！", Toast.LENGTH_SHORT).show();
                            }else {
                                buyOnClick();
                            }
                        }
                    }else {
                        Toast.makeText(getContext(),"请先登录！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.resource_seller_icon:
                    Toast.makeText(getContext(),"进入个人主页！！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResourceDetailsActivity.this, PersonalActivity.class);
                    intent.putExtra("objectId",seller.getObjectId());
                    startActivity(intent);
                    break;
            }
    }

    @Override
    public void showData(Resource resource, Boolean isLike, Boolean isStar, Boolean isBuy) {
        this.resource = resource;
        this.seller = resource.getCreator();
        this.isLike = isLike;
        this.isStar = isStar;
        this.isBuy = isBuy;
        initViews();
    }

    @Override
    public void showErrorMessage(String msg) {
        ToastEx.init(getContext(), ToastEx.Type.FAIL,msg, Toast.LENGTH_LONG,new Point(0,0)).show();
    }

    @Override
    public void showActionResult(String type, Boolean isAdd) {
        switch (type){
            case "like":
                if(isAdd){
                    isLike=true;
                    iv_like.setImageResource(R.drawable.supportlater);
                }else{
                    isLike=false;
                    iv_like.setImageResource(R.drawable.supportbefore);
                }
                break;
            case "star":
                if(isAdd){
                    isStar=true;
                    iv_star.setImageResource(R.drawable.collectionlater);
                }else{
                    isStar=false;
                    iv_star.setImageResource(R.drawable.collectionbefore);
                }
                break;
            case "buy":
                isBuy=true;
                btn_want.setText("已购买");
                btn_want.setBackgroundColor(btn_want.getResources().getColor(R.color.colorPrimary));
                btn_want.setTextColor(btn_want.getResources().getColor(R.color.text_gray));
                break;
        }
    }

    public void buyOnClick(){
        new MyDialog(ResourceDetailsActivity.this).builder().setTitle("购买资源")
                .setMsg("购买该资源后将扣除"+resource.getPrice()+"点积分，确定购买？")
                .setPositiveButton("确认购买", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clientUser.getPoints()<resource.getPrice())
                            Toast.makeText(getContext(),"积分不足！", Toast.LENGTH_SHORT).show();
                        else
                            mPresenter.setUserAction(resource,clientUser,"buy",true);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }
}
