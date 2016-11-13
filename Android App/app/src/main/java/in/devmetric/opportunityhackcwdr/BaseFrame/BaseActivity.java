package in.devmetric.opportunityhackcwdr.BaseFrame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import in.devmetric.opportunityhackcwdr.R;
import in.devmetric.opportunityhackcwdr.ViewHolders.MainScreenHolder;

/**
 * Created by @silverFoxA on 12/11/16.
 */

public class BaseActivity extends AppCompatActivity {

    //ViewHolder
    protected MainScreenHolder mainScreenHolder;

    protected void initFrame() {
        mainScreenHolder = new MainScreenHolder();

        //toolbar
        mainScreenHolder.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainScreenHolder.toolbar);
        setPageTitle(0);

        //setting up bottom bar
        setBottomBar();
    }

    private void setBottomBar() {
        mainScreenHolder.bottomBar = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.tab_1), R.drawable.home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.tab_2), R.drawable.blog);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.tab_3), R.drawable.question);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getResources().getString(R.string.tab_4), R.drawable.hotlines);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getResources().getString(R.string.tab_5), R.drawable.user);

        // Add items
        mainScreenHolder.bottomBar.addItem(item1);
        mainScreenHolder.bottomBar.addItem(item2);
        mainScreenHolder.bottomBar.addItem(item3);
        mainScreenHolder.bottomBar.addItem(item4);
        mainScreenHolder.bottomBar.addItem(item5);

        //disable the transition
        mainScreenHolder.bottomBar.setBehaviorTranslationEnabled(false);
        // Change colors
        mainScreenHolder.bottomBar.setAccentColor(getResources().getColor(R.color.colorAccent));
        mainScreenHolder.bottomBar.setInactiveColor(Color.parseColor("#747474"));

        // Force to tint the drawable (useful for font with icon for example)
        mainScreenHolder.bottomBar.setForceTint(true);


        // Manage titles
        mainScreenHolder.bottomBar.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
//        mainScreenHolder.bottomBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //mainScreenHolder.bottomBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
    }

    protected void setPageTitle(int pos) {
        String title = "";
        switch (pos) {
            case 0:
            default:
                title = "News Feed";
                break;
            case 1:
                title = "Blog";
                break;
            case 2:
                title = "Ask Question";
                break;
            case 3:
                title = "Hotlines";
                break;
            case 4:
                title = "Profile";
                break;

        }

        try {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideToolbar(boolean value) {
        if (getSupportActionBar() != null)
            if (value)
                getSupportActionBar().hide();
            else getSupportActionBar().show();
    }
}
