package ncu.folder_of_seniors.module.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

public class ResourceDetailsActivity extends BaseActivity implements View.OnClickListener{
    private String objectId;
    private Resource sell;
    private User seller;
    private String sellerid;
    private int seller_jifen;
    private ImageView back,icon,like,message,star;
    private boolean isLike=false;
    private boolean isStar=false;
    private boolean isBuy=false;
    private Button want;
    private LinearLayout photo_list;
    private TextView title,sellerName,sellTime,sellPrice,sellContent;

//    public static void show(Context context) {
//        Intent intent = new Intent(context, ReLo.class);
//        context.startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_selldetails);
//
//        Intent intent = getIntent();
//        objectId = intent.getStringExtra("objectId");
//
//        initView();
//        initData();
//
//    }

//    public void initView(){
//        back = (ImageView)findViewById(R.id.bar_back);
//        like = (ImageView)findViewById(R.id.tab_like_click);
//        message = (ImageView)findViewById(R.id.tab_message_click);
//        star = (ImageView)findViewById(R.id.tab_star_click);
//        want = (Button)findViewById(R.id.tab_want_click);
//        photo_list = (LinearLayout)findViewById(R.id.photo);
//        title = (TextView) findViewById(R.id.bar_tiitle);
//        sellerName = (TextView) findViewById(R.id.seller_username);
//        sellTime = (TextView)findViewById(R.id.sell_time);
//        sellPrice = (TextView)findViewById(R.id.sell_price);
//        sellContent = (TextView)findViewById(R.id.sell_content);
//        icon = (ImageView)findViewById(R.id.seller_icon);
//
//        like.setOnClickListener(this);
//        message.setOnClickListener(this);
//        star.setOnClickListener(this);
//        want.setOnClickListener(this);
//        back.setOnClickListener(this);
//        icon.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.tab_like_click:
//                if(isLike){
//                    Toast.makeText(SellDetailsActivity.this,"喜欢不可取消！", Toast.LENGTH_SHORT).show();
//                }else {
//                    //Toast.makeText(SellDetailsActivity.this,"点击了喜欢！！", Toast.LENGTH_SHORT).show();
//                    if(userinfo!=null){
//                        likeOnClick(userinfo,sell);
//                    }else {
//                        Toast.makeText(SellDetailsActivity.this,"请先登录！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//            case R.id.tab_message_click:
//                if(userinfo!=null){
//                    Toast.makeText(SellDetailsActivity.this,"进入聊天页面！！", Toast.LENGTH_SHORT).show();
//                    Intent intent1 = new Intent(SellDetailsActivity.this, ChatActivity.class);
//                    intent1.putExtra("sellerid",sell.getSellerId());
//                    startActivity(intent1);
//                }else {
//                    Toast.makeText(SellDetailsActivity.this,"请先登录！", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.tab_star_click:
//                if(isStar){
//                    Toast.makeText(SellDetailsActivity.this,"取消了收藏！", Toast.LENGTH_SHORT).show();
//                    if(userinfo!=null){
//                        starOnClick(userinfo,sell,0);
//                    }else {
//                        Toast.makeText(SellDetailsActivity.this,"请先登录！", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    //Toast.makeText(SellDetailsActivity.this,"点击了收藏！！", Toast.LENGTH_SHORT).show();
//                    if(userinfo!=null){
//                        starOnClick(userinfo,sell,1);
//                    }else {
//                        Toast.makeText(SellDetailsActivity.this,"请先登录！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//            case R.id.tab_want_click:
//                if(userinfo!=null){
//                    if(sell.getSellerId().equals(userinfo.getObjectId())){
//                        Toast.makeText(SellDetailsActivity.this,"不可购买自己的发布！", Toast.LENGTH_SHORT).show();
//                    }else {
//                        if(isBuy){
//                            Toast.makeText(SellDetailsActivity.this,"不可重复购买！", Toast.LENGTH_SHORT).show();
//                        }else {
//                            buyOnClick();
//                        }
//                    }
//                }else {
//                    Toast.makeText(SellDetailsActivity.this,"请先登录！", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.bar_back:
//                Toast.makeText(SellDetailsActivity.this,"点击了返回！！", Toast.LENGTH_SHORT).show();
//                finish();
//                break;
//            case R.id.seller_icon:
//                Toast.makeText(SellDetailsActivity.this,"进入个人主页！！", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SellDetailsActivity.this, PersonalActivity.class);
//                intent.putExtra("objectId",sell.getSellerId());
//                startActivity(intent);
//                break;
//        }
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resource_details;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

//    public void initData(){
//        BmobQuery<Sell> test = new BmobQuery<Sell>();
//        test.addWhereEqualTo("objectId", objectId);
//        test.findObjects(new FindListener<Sell>() {
//            @Override
//            public void done(List<Sell> list, BmobException e) {
//                if(e==null){
//                    sell = list.get(0);
//                    if(sell!=null){
//                        sellerid = sell.getSellerId();
//                        BmobQuery<User> query = new BmobQuery<User>();
//                        query.addWhereEqualTo("objectId",sellerid);
//                        query.findObjects(new FindListener<User>() {
//                            @Override
//                            public void done(List<User> list, BmobException e) {
//                                if(e==null){
//                                    if(list.size()!=0){
//                                        seller=list.get(0);
//                                        if (seller!=null){
//                                            seller_jifen = seller.getJifen();
//                                            sellerName.setText(seller.getUsername());
////                                            UtilTools.getImage(SellDetailsActivity.this,icon,seller.getImage());
//                                        }
//                                    }
//                                }
//                            }
//                        });
//
//                        title.setText(sell.getTitle());
//                        sellPrice.setText(sell.getPrice()+"");
//                        sellTime.setText(sell.getCreatedAt());
//                        sellContent.setText(sell.getDesc());
//                        if(sell.getPhoto().size()!=0) {
//                            int a = sell.getPhoto().size();
//                            photo_list.removeAllViews();
//                            for (int j = 0; j < a; j++) {
//                                                /*
//                                                动态添加会导致一个问题，重复添加，导致内存泄漏
//                                                所以为了防止重复添加，现将所有的imageview移除，清空资源，再添加
//                                                 */
//                                final int num = j;
//                                Glide.with(SellDetailsActivity.this).asBitmap().load(sell.getPhoto().get(j)).into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                                        int width = bitmap.getWidth();
//                                        int height = bitmap.getHeight();
//
//                                        int width2 = Utils.getWidth(SellDetailsActivity.this)-36;
//                                        float height3 = ((float)width2/(float)width)*(float)height;
//                                        int height2 = (int)height3;
//
//                                        //Toast.makeText(SellDetailsActivity.this, width+","+height+"  "+width2+","+height2, Toast.LENGTH_SHORT).show();
//                                        final ImageView imageView = new ImageView(SellDetailsActivity.this);
//                                        imageView.setLayoutParams(new LinearLayout.LayoutParams(width2,height2));
//                                        //imageView.setAdjustViewBounds(true);
//                                        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                                        Glide.with(SellDetailsActivity.this).load(sell.getPhoto().get(num)).into(imageView);
//                                        photo_list.addView(imageView);
//                                    }
//                                });
//                            }
//
//                        }
//                    }
//                }else {
//                    Toast.makeText(SellDetailsActivity.this, "查询失败1："+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        if(userinfo!=null){
//            BmobQuery<User> test2 = new BmobQuery<User>();
//            Sell sell1 = new Sell();
//            sell1.setObjectId(objectId);
//            test2.addWhereRelatedTo("likes",new BmobPointer(sell1));
//            test2.findObjects(new FindListener<User>() {
//                @Override
//                public void done(List<User> list, BmobException e) {
//                    if(e==null){
//                        //Toast.makeText(SellDetailsActivity.this, "查询喜欢成功：", Toast.LENGTH_SHORT).show();
//                        for(int i=0;i<list.size();i++){
//                            if(list.get(i).getObjectId().equals(userinfo.getObjectId())){
//                                isLike=true;
//                            }
//                        }
//                        //Toast.makeText(SellDetailsActivity.this, "isLike："+isLike, Toast.LENGTH_SHORT).show();
//                        if(isLike){
//                            like.setImageResource(R.drawable.supportlater);
//                        }
//                    }else{
//                        Toast.makeText(SellDetailsActivity.this, "查询喜欢失败："+e, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            BmobQuery<Sell> query = new BmobQuery<Sell>();
//            User user = new User();
//            user.setObjectId(userinfo.getObjectId());
//            query.addWhereRelatedTo("collection", new BmobPointer(user));
//            query.findObjects(new FindListener<Sell>() {
//                @Override
//                public void done(List<Sell> object, BmobException e) {
//                    if(e==null){
//                       for (int i = 0;i<object.size();i++){
//                           if(object.get(i).getObjectId().equals(objectId)){
//                               isStar=true;
//                           }
//                           if(isStar){
//                               star.setImageResource(R.drawable.collectionlater);
//                           }
//                       }
//                        //Toast.makeText(getApplicationContext(),"查询成功"+object.size(),Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(getApplicationContext(),"查询收藏失败"+object.size(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//            });
//            BmobQuery<User> test4 = new BmobQuery<User>();
//            test4.addWhereRelatedTo("buyer",new BmobPointer(sell1));
//            test4.findObjects(new FindListener<User>() {
//                @Override
//                public void done(List<User> list, BmobException e) {
//                    if(e==null){
//                        //Toast.makeText(SellDetailsActivity.this, "查询收藏成功：", Toast.LENGTH_SHORT).show();
//                        for(int i=0;i<list.size();i++){
//                            if(list.get(i).getObjectId().equals(userinfo.getObjectId())){
//                                isBuy=true;
//                            }
//                        }
//                        //Toast.makeText(SellDetailsActivity.this, "isStar："+isStar, Toast.LENGTH_SHORT).show();
//                        if(isBuy){
//                            want.setText("已购买");
//                            want.setBackgroundColor(want.getResources().getColor(R.color.colorPrimary));
//                            want.setTextColor(want.getResources().getColor(R.color.text_gray));
//                        }
//                    }else{
//                        Toast.makeText(SellDetailsActivity.this, "查询购买失败："+e, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//
//    public void likeOnClick(User user,Sell sell){
//        BmobRelation relation = new BmobRelation();
//        relation.add(user);
//        sell.setLikes(relation);
//        sell.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Toast.makeText(SellDetailsActivity.this,"点赞成功", Toast.LENGTH_SHORT).show();
//                    isLike=true;
//                    like.setImageResource(R.drawable.supportlater);
//                }else {
//                    Toast.makeText(SellDetailsActivity.this,"点赞失败："+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    public void buyOnClick(){
//        new MyDialog(SellDetailsActivity.this).builder().setTitle("购买该资源")
//                .setMsg("购买该资源后将扣除"+sell.getPrice()+"点积分，确定购买？")
//                .setPositiveButton("确认购买", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                            final int jifen = userinfo.getJifen();
//                            if(jifen<sell.getPrice()){
//                                new MyDialog(SellDetailsActivity.this).builder()
//                                        .setMsg("您的积分不足！")
//                                        .setNegativeButton("确定", new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//
//                                            }
//                                        }).show();
//                            }else {
//                                userinfo.setValue("jifen",jifen-sell.getPrice());
//                                userinfo.update(new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if(e==null){
//                                            Deal deal= new Deal();
//                                            deal.setAdd(false);
//                                            deal.setSell(sell);
//                                            deal.setBuyerId(userinfo.getObjectId());
//                                            deal.setSellerId(sellerid);
//                                            deal.setPrice(sell.getPrice());
//                                            deal.save(new SaveListener<String>() {
//                                                @Override
//                                                public void done(String s, BmobException e) {
//                                                    if(e==null) {
//                                                        BmobRelation relation = new BmobRelation();
//                                                        relation.add(userinfo);
//                                                        sell.setBuyer(relation);
//                                                        sell.update(new UpdateListener() {
//                                                            @Override
//                                                            public void done(BmobException e) {
//                                                                if(e==null){
//                                                                    new MyDialog(SellDetailsActivity.this).builder()
//                                                                            .setMsg("购买成功，请到我买到的列表查看下载链接！")
//                                                                            .setNegativeButton("确定", new View.OnClickListener() {
//                                                                                @Override
//                                                                                public void onClick(View v) {
//
//                                                                                }
//                                                                            }).show();
//                                                                    isBuy = true;
//                                                                    want.setText("已购买");
//                                                                    want.setBackgroundColor(want.getResources().getColor(R.color.colorPrimary));
//                                                                    want.setTextColor(want.getResources().getColor(R.color.text_gray));
//                                                                }else {
//                                                                    Toast.makeText(getApplicationContext(), "购买失败3:"+e, Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
//                                                    }else {
//                                                        Toast.makeText(getApplicationContext(), "购买失败2:"+e, Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//
//                                        }else{
//                                            Toast.makeText(getApplicationContext(), "购买失败:"+e, Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                }).setNegativeButton("取消", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).show();
//    }
//
//    public void starOnClick(final User user,final Sell sell,int type){
//        BmobRelation relation = new BmobRelation();
//        if(type==1){
//            relation.add(user);
//            sell.setStar(relation);
//            sell.update(new UpdateListener() {
//                @Override
//                public void done(BmobException e) {
//                    if(e==null){
//                        Toast.makeText(SellDetailsActivity.this,"收藏成功", Toast.LENGTH_SHORT).show();
//                        isStar=true;
//                        star.setImageResource(R.drawable.collectionlater);
//
//                        BmobRelation relation1 = new BmobRelation();
//                        relation1.add(sell);
//                        //多对多关联指向`post`的`likes`字段
//                        user.setCollection(relation1);
//                        user.update(new UpdateListener() {
//                            @Override
//                            public void done(BmobException e) {
//                                if(e==null){
//                                    //Toast.makeText(SellDetailsActivity.this,"逆向收藏成功", Toast.LENGTH_SHORT).show();
//
//                                }else{
//                                    Toast.makeText(SellDetailsActivity.this,"逆向收藏失败", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//
//                        });
//                    }else {
//                        Toast.makeText(SellDetailsActivity.this,"收藏失败："+e, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }else {
//            relation.remove(user);
//            sell.setStar(relation);
//            sell.update(new UpdateListener() {
//                @Override
//                public void done(BmobException e) {
//                    if(e==null){
//                        Toast.makeText(SellDetailsActivity.this,"取消收藏成功", Toast.LENGTH_SHORT).show();
//                        isStar=false;
//                        star.setImageResource(R.drawable.collectionbefore);
//
//                        BmobRelation relation1 = new BmobRelation();
//                        relation1.remove(sell);
//                        //多对多关联指向`post`的`likes`字段
//                        user.setCollection(relation1);
//                        user.update(new UpdateListener() {
//                            @Override
//                            public void done(BmobException e) {
//                                if(e==null){
//                                    //Toast.makeText(SellDetailsActivity.this,"逆向取消收藏成功", Toast.LENGTH_SHORT).show();
//
//                                }else{
//                                    Toast.makeText(SellDetailsActivity.this,"逆向取消收藏失败", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//
//                        });
//                    }else {
//                        Toast.makeText(SellDetailsActivity.this,"取消收藏失败："+e, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//
//    }
}
