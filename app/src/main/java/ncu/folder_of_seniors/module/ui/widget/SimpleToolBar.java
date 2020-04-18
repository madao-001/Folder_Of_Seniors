package ncu.folder_of_seniors.module.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import ncu.folder_of_seniors.R;


public class SimpleToolBar extends Toolbar {
    /**
     * 左侧Title
     */
    private TextView mTxtLeftTitle;
    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 左侧Title
     */
    private TextView mTxtRightTitle;

    public SimpleToolBar(Context context) {
        this(context,null);
    }

    public SimpleToolBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTxtLeftTitle = (TextView) findViewById(R.id.txt_left_title);
        mTxtMiddleTitle = (TextView) findViewById(R.id.txt_main_title);
        mTxtRightTitle = (TextView)findViewById(R.id.txt_right_title);
    }

    //设置中间title的内容
    public void setMainTitle(String text) {
        this.setTitle(" ");
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }

    //设置中间title的内容文字的颜色
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    //设置title左边文字
    public void setLeftTitleText(String text) {
        mTxtLeftTitle.setVisibility(View.VISIBLE);
        mTxtLeftTitle.setText(text);
    }

    //设置title左边文字颜色
    public void setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
    }

    //设置title左边图标
    public void setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mTxtLeftTitle.setCompoundDrawables(dwLeft, null, null, null);
    }
    //设置title左边点击事件
    public void setLeftTitleClickListener(OnClickListener onClickListener){
        mTxtLeftTitle.setVisibility(VISIBLE);
        mTxtLeftTitle.setOnClickListener(onClickListener);
    }

    //设置title右边点击事件
    public void setRightTitleClickListener(OnClickListener onClickListener){
        mTxtRightTitle.setVisibility(VISIBLE);
        mTxtRightTitle.setOnClickListener(onClickListener);
    }

}
