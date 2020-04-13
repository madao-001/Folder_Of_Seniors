package ncu.folder_of_seniors.module.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import ncu.folder_of_seniors.R;

public class SelectPicPopupWindow extends Activity implements OnClickListener {

    private Button paizhao,tuku,quxiao;
    private LinearLayout layout;
    private String choose;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);

        initview();

        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    public void initview(){
        layout =(LinearLayout)findViewById(R.id.pop_layout);
        paizhao = (Button) this.findViewById(R.id.btn_paizhao);
        tuku = (Button)findViewById(R.id.btn_tuku);
        quxiao = (Button)findViewById(R.id.btn_quxiao);

        paizhao.setOnClickListener(this);
        tuku.setOnClickListener(this);
        quxiao.setOnClickListener(this);
    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        this.overridePendingTransition(0, R.anim.push_buttom_out);

        return true;
    }

    public void onClick(View v) {
        this.overridePendingTransition(0, R.anim.push_buttom_out);

        switch (v.getId()) {
            case R.id.btn_paizhao:
                intent.putExtra("data_return", "拍照");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.btn_tuku:
                intent.putExtra("data_return", "图库");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.btn_quxiao:
                choose = "取消";
                finish();
                break;
            default:
                break;
        }
        finish();
    }

}