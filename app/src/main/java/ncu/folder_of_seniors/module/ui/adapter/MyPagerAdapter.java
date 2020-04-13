package ncu.folder_of_seniors.module.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/*
* 主活动页面的viewPage的适配器
* */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mListViews;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyPagerAdapter(FragmentManager fm, List<Fragment> list){
        super(fm);
        this.mListViews =list;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment page = null;
        if (mListViews.size() > position) {
            page = mListViews.get(position);
            if (page != null) {
                return page;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }
}
