package in.devmetric.opportunityhackcwdr.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.devmetric.opportunityhackcwdr.Fragments.BlogPage;
import in.devmetric.opportunityhackcwdr.Fragments.HotlinesPage;
import in.devmetric.opportunityhackcwdr.Fragments.MainHomePage;
import in.devmetric.opportunityhackcwdr.Fragments.ProfilePage;
import in.devmetric.opportunityhackcwdr.Fragments.QuestionsPage;

/**
 * Created by @silverFoxA on 12/11/16.
 */

public class ViewPagerAdapters extends FragmentPagerAdapter {

    private Fragment fragment;

    public ViewPagerAdapters(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                fragment = new MainHomePage();
                return fragment;
            case 1:
                fragment = new BlogPage();
                return fragment;
            case 2:
                fragment = new QuestionsPage();
                return fragment;
            case 3:
                fragment = new HotlinesPage();
                return fragment;
            case 4:
            default:
                fragment = new ProfilePage();
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

}
