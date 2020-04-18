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


import butterknife.BindView;
import butterknife.OnClick;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;

public class SelectPopupWindow extends Activity implements OnClickListener {

    private Button btn_first,btn_second,btn_third;
    private LinearLayout layout;
    private String btn1,btn2,btn3;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);

        initview();
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("tag");
        if(bundle!=null){
            setBtn(bundle.getString("btn1"),bundle.getString("btn2"),bundle.getString("btn3"));
        }
        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
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

    public void initview() {
        layout = (LinearLayout) findViewById(R.id.alert_dialog_pop_layout);
        btn_first = (Button) this.findViewById(R.id.alert_dialog_btn_first);
        btn_second = (Button) findViewById(R.id.alert_dialog_btn_second);
        btn_third = (Button) findViewById(R.id.alert_dialog_btn_third);

        btn_first.setOnClickListener(this);
        btn_second.setOnClickListener(this);
        btn_third.setOnClickListener(this);
    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        this.overridePendingTransition(0, R.anim.push_buttom_out);
        return true;
    }

    public void setBtn(String btn1,String btn2,String btn3){
        this.btn1 = btn1;
        btn_first.setText(btn1);
        this.btn2 = btn2;
        btn_second.setText(btn2);
        this.btn3 = btn3;
        btn_third.setText(btn3);
    }

    @Override
    public void onClick(View v) {
        this.overridePendingTransition(0, R.anim.push_buttom_out);
        switch (v.getId()){
            case R.id.alert_dialog_btn_first:
                intent.putExtra("data_return", btn1);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.alert_dialog_btn_second:
                intent.putExtra("data_return", btn2);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.alert_dialog_btn_third:
                intent.putExtra("data_return", btn3);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.alert_dialog_pop_layout:
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
    }
}