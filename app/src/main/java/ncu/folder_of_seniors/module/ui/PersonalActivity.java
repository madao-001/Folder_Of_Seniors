package ncu.folder_of_seniors.module.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;

public class PersonalActivity extends BaseActivity implements View.OnClickListener{
//    private List<Sell> personalList=new ArrayList<Sell>();
//    private List<User> UserList=new ArrayList<User>();
//    private PersonalAdapter personalAdapter;
//    private RecyclerView personan_rec;
    //定义top组件
    private TextView name;
    private TextView objectId;
    private TextView isOnline;
    private TextView time;
    private TextView sex;
    private TextView chaozan;
    private TextView guanzhu;
    private TextView fensi;
    private ImageView head;
    private ImageView btguanzhu;
    private boolean isLogin;
    private BmobRelation focus=new BmobRelation();
    private BmobRelation fans=new BmobRelation();
//    private User userinfo;
//    private User person;
//    private FansFocus fansFocus;
    private String objectid;
    private int h=0;//用于判断当前用户是否被关注
    private boolean addFocus=false;
    private boolean addFans=false;
    private boolean deleteFocus=false;
    private boolean deleteFans=false;
    private String user_ff_objectid;
    private String person_ff_objectid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initView();

        Intent intent=getIntent();
        //此时得到的objectid是卖主的objectid
        objectid=intent.getStringExtra("objectId");
        Toast.makeText(getApplicationContext(),"传过来的objectid为"+objectid, Toast.LENGTH_SHORT).show();

//        //查找person
//        BmobQuery<User> test=new BmobQuery<User>();
//        test.addWhereEqualTo("objectId",objectid);
//        test.findObjects(new FindListener<User>() {
//            @Override
//            public void done(final List<User> list, BmobException e) {
//                if (e==null){
//                    person=list.get(0);
//                    initData();
//                    searchPersonId();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"用户好像不存在了哦"+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
//        personan_rec=(RecyclerView) findViewById(R.id.personan_rec);
//        //设置布局管理器
//        personan_rec.setLayoutManager(layoutManager);
//        //设置为垂直布局，这也是默认的
//        layoutManager.setOrientation(OrientationHelper. VERTICAL);
//
//        personalAdapter = new PersonalAdapter(PersonalActivity.this, personalList);
//
//        //设置Adapter
//        personan_rec.setAdapter(personalAdapter);
//
//        //改变关注图片
//
//
//        //判断是否登陆
//        userinfo = BmobUser.getCurrentUser(com.example.administrator.folder_for_seniors.dao.User.class);
//        if (userinfo==null){
//            isLogin=false;
//        }else {
//            isLogin=true;
//            searchUserId();
//        }
//        fansFocus=new FansFocus();

    }

    private void initView(){
        //填充上面的信息
        name=(TextView) findViewById(R.id.name);
        objectId=(TextView)findViewById(R.id.objectId);
        isOnline=(TextView)findViewById(R.id.isOnline);
        time=(TextView)findViewById(R.id.time);
        sex=(TextView)findViewById(R.id.sex);
        head=(ImageView)findViewById(R.id.pehead);
        btguanzhu=(ImageView)findViewById(R.id.btguanzhu);
        chaozan=(TextView)findViewById(R.id.chaozan);
        guanzhu=(TextView)findViewById(R.id.guanzhu);
        fensi=(TextView)findViewById(R.id.fensi);

        btguanzhu.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

//    private void initData(){
//        //设置基础信息
//        name.setText("昵称："+person.getUsername());
//        objectId.setText(objectid);
//        if(person.isOnline()){
//            isOnline.setText("我在线上");
//        }else {
//            isOnline.setText("暂时下线");
//        }
//        time.setText("该用户注册于"+person.getCreatedAt());
//        if (person.isSex()){
//            sex.setText("男生。");
//        }else {
//            sex.setText("女生。");
//        }
//
//
////        UtilTools.getImage(PersonalActivity.this,head,person.getImage());
//        //设置关注图片
//        BmobQuery<User> query = new BmobQuery<User>();
//        FansFocus fansFocus=new FansFocus();
//        fansFocus.setObjectId(user_ff_objectid);
//        //Toast.makeText(getApplicationContext(),"user:"+user_ff_objectid,Toast.LENGTH_SHORT).show();
//        query.addWhereRelatedTo("focus", new BmobPointer(fansFocus));
//        query.findObjects(new FindListener<User>() {
//            @Override
//            public void done(List<User> list, BmobException e) {
//                if (e==null){
//                    //Toast.makeText(getApplicationContext(),"一共查询到"+list.size()+"个用户且objectid为"+list.get(0).getObjectId(),Toast.LENGTH_SHORT).show();
//                    for (int i=0;i<list.size();i++){
//                        if (list.get(i).getObjectId().equals(person.getObjectId())){
//                            h=1;
//                        }
//                    }
//                    switch (h){
//                        case 0:
//                            btguanzhu.setImageDrawable(getResources().getDrawable(R.drawable.guanzhu));
//                            break;
//                        case 1:
//                            btguanzhu.setImageDrawable(getResources().getDrawable(R.drawable.yiguanzhu));
//                            break;
//                        default:
//                            break;
//                    }
//
//                    BmobQuery<User> query = new BmobQuery<User>();
//                    FansFocus fansFocus=new FansFocus();
//                    fansFocus.setObjectId(person_ff_objectid);
//                    query.addWhereRelatedTo("focus", new BmobPointer(fansFocus));
//                    query.findObjects(new FindListener<User>() {
//                        @Override
//                        public void done(List<User> list, BmobException e) {
//                            if (e==null){
//                                guanzhu.setText(list.size()+"关注");
//
//                                BmobQuery<User> query1 = new BmobQuery<User>();
//                                FansFocus fansFocus1=new FansFocus();
//                                fansFocus1.setObjectId(person_ff_objectid);
//                                query1.addWhereRelatedTo("fans", new BmobPointer(fansFocus1));
//                                query1.findObjects(new FindListener<User>() {
//                                    @Override
//                                    public void done(List<User> list, BmobException e) {
//                                        if (e==null){
//                                            fensi.setText(list.size()+"粉丝");
//                                        }else {
//                                            Toast.makeText(getApplicationContext(),"获取粉丝数量失败"+e, Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                            }else {
//                                Toast.makeText(getApplicationContext(),"获取关注数量失败"+e, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }else {
//                    if (list==null){
//                        btguanzhu.setImageDrawable(getResources().getDrawable(R.drawable.guanzhu));
//                    }else {
//                        Toast.makeText(getApplicationContext(),"获取关注状态失败"+e, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }
    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btguanzhu){
            if (isLogin){
//                switch (h){
//                    case 0:
//                        //Toast.makeText(PersonalActivity.this,"进入0操作",Toast.LENGTH_LONG).show();
//                        addFocus=true;
//                        if (addFocus==true&&addFans==false){
//                            AddFocus();
//                            addFocus=false;
//                            addFans=true;
//                        }
//                        if(addFans==true&&addFocus==false){
//                            AddFans();
//                            addFocus=false;
//                            addFans=false;
//                        }
//                        h=1;
//                        btguanzhu.setImageDrawable(getResources().getDrawable(R.drawable.yiguanzhu));
//                        FocusFansUpdate();
////                        FocusFansUpdate1();
//                        break;
//                    case 1:
//                        //Toast.makeText(PersonalActivity.this,"进入1操作",Toast.LENGTH_LONG).show();
//                        deleteFocus=true;
//                        if (deleteFocus==true&&deleteFans==false){
//                            DeleteFocus();
//                            deleteFocus=false;
//                            deleteFans=true;
//                        }
//                        if (deleteFocus==false&&deleteFans==true){
//                            DeleteFans();
//                            deleteFans=false;
//                            deleteFocus=false;
//                        }
//                        h=0;
//                        btguanzhu.setImageDrawable(getResources().getDrawable(R.drawable.guanzhu));
//                        FocusFansUpdate();
////                        FocusFansUpdate1();
//                        break;
//                    default:
//                        break;
                }
            }else {
                Toast.makeText(PersonalActivity.this,"你还没有登陆哦", Toast.LENGTH_LONG).show();
            }
        }
    }

//    private void AddFocus(){
//        FansFocus fansFocus=new FansFocus();
//        //Toast.makeText(getApplicationContext(),"我的id"+userinfo.getObjectId(),Toast.LENGTH_SHORT).show();
//        fansFocus.setObjectId(user_ff_objectid);
//        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
//        BmobRelation relation = new BmobRelation();
//        //将当前用户添加到多对多关联中
//        relation.add(person);
//        //Toast.makeText(getApplicationContext(),"person的id"+person.getObjectId(),Toast.LENGTH_SHORT).show();
//        //多对多关联指向`post`的`likes`字段
//        fansFocus.setFocus(relation);
//        fansFocus.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e==null){
//                    Toast.makeText(getApplicationContext(),"关注成功", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(),"关注失败"+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void AddFans(){
//        FansFocus fansFocus=new FansFocus();
//        fansFocus.setObjectId(person_ff_objectid);
//        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
//        BmobRelation relation = new BmobRelation();
//        //将当前用户添加到多对多关联中
//        relation.add(userinfo);
//        //多对多关联指向`post`的`likes`字段
//        fansFocus.setFans(relation);
//        fansFocus.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e==null){
//                    //Toast.makeText(getApplicationContext(),"对方添加粉丝成功",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(),"对方添加粉丝失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    private void DeleteFocus(){
//        FansFocus fansFocus=new FansFocus();
//        fansFocus.setObjectId(user_ff_objectid);
//        BmobRelation relation=new BmobRelation();
//        relation.remove(person);
//        fansFocus.setFocus(relation);
//        fansFocus.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e==null){
//                    Toast.makeText(getApplicationContext(),"取消关注成功", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(),"取消关注失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void DeleteFans(){
//        FansFocus fansFocus=new FansFocus();
//        fansFocus.setObjectId(person_ff_objectid);
//        BmobRelation relation=new BmobRelation();
//        relation.remove(userinfo);
//        fansFocus.setFans(relation);
//        fansFocus.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e==null){
//                    //Toast.makeText(getApplicationContext(),"对方取消粉丝成功",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(),"对方取消粉丝失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void FocusFansUpdate(){
//        //Toast.makeText(getApplicationContext(),"执行focusfansupdate",Toast.LENGTH_SHORT).show();
//
//        BmobQuery<User> query = new BmobQuery<User>();
//        FansFocus fansFocus=new FansFocus();
//        fansFocus.setObjectId(person_ff_objectid);
//        query.addWhereRelatedTo("focus", new BmobPointer(fansFocus));
//        query.findObjects(new FindListener<User>() {
//            @Override
//            public void done(List<User> list, BmobException e) {
//                if (e==null){
//                    guanzhu.setText(list.size()+"关注");
//
//                    BmobQuery<User> query1 = new BmobQuery<User>();
//                    FansFocus fansFocus1=new FansFocus();
//                    fansFocus1.setObjectId(person_ff_objectid);
//                    query1.addWhereRelatedTo("fans", new BmobPointer(fansFocus1));
//                    query1.findObjects(new FindListener<User>() {
//                        @Override
//                        public void done(List<User> list, BmobException e) {
//                            if (e==null){
//                                fensi.setText(list.size()+"粉丝");
//                            }else {
//                                Toast.makeText(getApplicationContext(),"获取粉丝数量失败"+e, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }else {
//                    Toast.makeText(getApplicationContext(),"获取关注数量失败"+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//    private void FocusFansUpdate1(){
//        BmobQuery<User> query1 = new BmobQuery<User>();
//        FansFocus fansFocus1=new FansFocus();
//        fansFocus1.setObjectId(person_ff_objectid);
//        query1.addWhereRelatedTo("fans", new BmobPointer(fansFocus1));
//        query1.findObjects(new FindListener<User>() {
//            @Override
//            public void done(List<User> list, BmobException e) {
//                if (e==null){
//                    fensi.setText(list.size()+"粉丝");
//                }else {
//                    Toast.makeText(getApplicationContext(),"获取粉丝数量失败"+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void searchUserId(){
//        BmobQuery<FansFocus> test=new BmobQuery<>();
//        test.addWhereEqualTo("my",userinfo.getObjectId());
//        test.findObjects(new FindListener<FansFocus>() {
//            @Override
//            public void done(List<FansFocus> list, BmobException e) {
//                if(e==null){
//                    user_ff_objectid=list.get(0).getObjectId();
//                }else {
//                    //Toast.makeText(getApplicationContext(),"user_ff_objectid查询失败"+e,Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }
//
//    private void searchPersonId(){
//        BmobQuery<FansFocus> test1=new BmobQuery<>();
//        test1.addWhereEqualTo("my",person.getObjectId());
//        test1.findObjects(new FindListener<FansFocus>() {
//            @Override
//            public void done(List<FansFocus> list, BmobException e) {
//                if(e==null){
//                    person_ff_objectid=list.get(0).getObjectId();
//                }else {
//                    //Toast.makeText(getApplicationContext(),"person_ff_objectid查询失败"+e,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//         /*
//        1、获取数据
//        2、将数据添加到集合
//        3、设置适配器
//         */
////         FocusFansUpdate();
////         FocusFansUpdate1();
////
////
////        //1、获取表中存放的数据
////        BmobQuery<Sell> test=new BmobQuery<>();
////        User user = new User();
////        user.setObjectId(objectid);
////        test.addWhereEqualTo("seller",user);
////        test.order("-updateAt");
////        test.include("seller");
////        test.findObjects(new FindListener<Sell>() {
////            @Override
////            public void done(final List<Sell> list, BmobException e) {
////                if (e==null){
////                    personalList.clear();
////                    personalList.addAll(list);
////                    personalAdapter.notifyDataSetChanged();
////                    //Toast.makeText(getApplicationContext(),"成功查找"+personalList.size(),Toast.LENGTH_SHORT).show();
////                }
////                else {
////                    Toast.makeText(getApplicationContext(),"该用户没有发布任何产品", Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
//    }

