package ncu.folder_of_seniors.module.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ncu.folder_of_seniors.R;


public class BottomBar extends LinearLayout {
    private Context mContext;
    private ImageView iv_home,iv_campus,iv_message,iv_mine;
    private FrameLayout mFirst_bottom, mSecond_bottom, mThird_bottom, mFouth_bottom, mCenter_bottom;
    private OnBottonbarClick mOnBottonbarClick;

    public BottomBar(Context context) {
        super(context);
        init(context);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_bottom, this, true);
        //获取控件id
        initId();
        onBottomBarClick();
    }

    private void initId() {
        mFirst_bottom = (FrameLayout) findViewById(R.id.first);
        mSecond_bottom = (FrameLayout) findViewById(R.id.second);
        mThird_bottom = (FrameLayout) findViewById(R.id.third);
        mFouth_bottom = (FrameLayout) findViewById(R.id.fouth);
        mCenter_bottom = (FrameLayout) findViewById(R.id.center);
        iv_home = (ImageView)findViewById(R.id.tab_home_click);
        iv_campus = (ImageView)findViewById(R.id.tab_campus_click);
        iv_message = (ImageView)findViewById(R.id.tab_message_click);
        iv_mine = (ImageView)findViewById(R.id.tab_mine_click);
    }

    /**
     * 底部按钮点击监听器
     */
    private void onBottomBarClick() {
        mFirst_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onFirstClick();
                    boolean[] isSelected={false,false,false,false};
                    setImageViewSelected(isSelected);
                }
            }
        });
        mSecond_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onSecondClick();
                    boolean[] isSelected={true,true,false,false};
                    setImageViewSelected(isSelected);
                }
            }
        });
        mThird_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onThirdClick();
                    boolean[] isSelected={true,false,true,false};
                    setImageViewSelected(isSelected);
                }
            }
        });
        mFouth_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onFouthClick();
                    boolean[] isSelected={true,false,false,true};
                    setImageViewSelected(isSelected);
                }
            }
        });
        mCenter_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onCenterClick();
                }
            }
        });

    }
    public void setOnBottombarOnclick(OnBottonbarClick onBottonbarClick) {

        mOnBottonbarClick = onBottonbarClick;
    }

    public interface OnBottonbarClick {
        void onFirstClick();
        void onSecondClick();
        void onThirdClick();
        void onFouthClick();
        void onCenterClick();
    }

    public void  performClickFirst(){
        if (mOnBottonbarClick != null) {
            mOnBottonbarClick.onFirstClick();
            boolean[] isSelected={false,false,false,false};
            setImageViewSelected(isSelected);
        }
    }

    public void  performClickSecond(){
        if (mOnBottonbarClick != null) {
            mOnBottonbarClick.onSecondClick();
            boolean[] isSelected={true,true,false,false};
            setImageViewSelected(isSelected);
        }
    }

    public void  performClickThird(){
        if (mOnBottonbarClick != null) {
            mOnBottonbarClick.onThirdClick();
            boolean[] isSelected={true,false,true,false};
            setImageViewSelected(isSelected);
        }
    }

    public void  performClickFouth(){
        if (mOnBottonbarClick != null) {
            mOnBottonbarClick.onFouthClick();
            boolean[] isSelected={true,false,false,true};
            setImageViewSelected(isSelected);
        }
    }

    private void setImageViewSelected(boolean[] isSelected){
        if (isSelected==null) return;
        iv_home.setSelected(isSelected[0]);
        iv_campus.setSelected(isSelected[1]);
        iv_message.setSelected(isSelected[2]);
        iv_mine.setSelected(isSelected[3]);
    }
}
