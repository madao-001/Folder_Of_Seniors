package ncu.folder_of_seniors.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import ncu.folder_of_seniors.R;


public class UtilTools {

//
//    //保存图片到Bmob
//    public static void putImageToBmob(final Context mContext, ImageView imageView) {
//        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        //第一步：将Bitmap压缩成字节数组输出流
//        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
//        //第二步：利用Base64将我们的字节数组输出流转换成String
//        byte[] byteArray = byStream.toByteArray();
//        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
//        //第三步：将String保存shareUtils
//        ShareUtils s = new ShareUtils();
//        s.putString(mContext,"image_title",imgString);
//        //设置具体的值
//        User userInfo = BmobUser.getCurrentUser(User.class);
//        BmobFile icon = new
//        icon.setImage(imgString);
//        icon.setUserid(userInfo.getObjectId());
//
//        //更新数据
//        icon.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
//
//                }else{
//                    toast("创建数据失败：" + e.getMessage());
//                }
//            }
//        });
//
//        ShareUtils.putString(mContext, "image_title", imgString);
//        ShareUtils.putBoolean(mContext,"image_modify",true);
//
//
//    }
//
//    //读取图片
//    public static void getImageFromBmob(Context mContext, final ImageView imageView) {
//        //1.拿到string
//        User userInfo = User.getCurrentUser(User.class);
//        if(userInfo.isIcon()){
//            BmobQuery<Icon> query = new BmobQuery<Icon>();
//            query.addWhereEqualTo("userid",userInfo.getObjectId());
//            query.findObjects(new FindListener<Icon>() {
//                @Override
//                public void done(List<Icon> list, BmobException e) {
//                    if(e==null){
//                        if(list.size()!=0){
//                            Icon icon=list.get(0);
//                            if (icon!=null){
//                                String imgString = icon.getImage();
//                                if (!imgString.equals("")) {
//                                    //2.利用Base64将我们string转换
//                                    byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
//                                    ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
//                                    //3.生成bitmap
//                                    Bitmap bitmap = BitmapFactory.decodeStream(byStream);
//                                    imageView.setImageBitmap(bitmap);
//                                }
//                            }
//                        }
//                    }
//                }
//            });
//        }else {
//            imageView.setImageResource(R.drawable.my);
//        }
//    }




    //保存图片到服务器

    public static String putImage(final Context mContext, String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //第二步：利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步：将String保存shareUtils
        //保存到服务器

        return imgString;



    }




    //读取图片
    public static void getImage(Context mContext, ImageView imageView, String s) {
        //1.拿到string
        String imgString = s;
        if (!imgString.equals("")) {
            //2.利用Base64将我们string转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);



        }
    }






    //获取版本号
    public static String getVersion(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return mContext.getString(R.string.text_unknown);
        }
    }


        //获取屏幕
        public static int  getWidth(Context context){
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            return width;

    }

    //获取屏幕
    public static int  getHeight(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        return height;

    }























    /**
     * uri转path
     */
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


}
