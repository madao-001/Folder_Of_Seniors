package ncu.folder_of_seniors.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Utils {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        //scale   （DisplayMetrics类中属性density）
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        //scale    （DisplayMetrics类中属性density）
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        //fontScale （DisplayMetrics类中属性scaledDensity）
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        //fontScale （DisplayMetrics类中属性scaledDensity）
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getScreenWidth(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getHeight();
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
    public static void initToolbar(AppCompatActivity activity, Toolbar toolbar, String title, String subTitle, int resIcon, Drawable icon) {

        toolbar.setTitle(title);
        toolbar.setSubtitle(subTitle);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);

        if (resIcon != 0) {
            toolbar.setNavigationIcon(resIcon);
            return;
        }
        toolbar.setNavigationIcon(icon);
    }


    public static int[] getImgWH(String urls) throws Exception {

        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();
        Bitmap image = BitmapFactory.decodeStream(is);

        int srcWidth = image.getWidth();      // 源图宽度
        int srcHeight = image.getHeight();    // 源图高度
        int[] imgSize = new int[2];
        imgSize[0] = srcWidth;
        imgSize[1] = srcHeight;

        //释放资源
        image.recycle();
        is.close();
        conn.disconnect();

        return imgSize;
    }

    public static boolean checkSdCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
}
