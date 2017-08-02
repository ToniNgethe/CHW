package chapa.cm.chama;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SecondFormActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbar = (Toolbar) findViewById(R.id.MyPostedJobsToolbar);

        //set toolbar...
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reports");

        mViewPager = (ViewPager) findViewById(R.id.MyPostedJobsViewPager);
        //setup viewpager
        setUpViewPager();

        mTabLayout = (TabLayout) findViewById(R.id.MyPostedJobsTab);
        //setupTab with viewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void setUpViewPager() {
        Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SecondFormPageAdapter myPostedJObsViewPAdapter = new SecondFormPageAdapter(getSupportFragmentManager());
                myPostedJObsViewPAdapter.addFragment(new SummaryData(),"WEEKLY SUMMARY DATA");
                myPostedJObsViewPAdapter.addFragment(new Conditions(),"CONDITIONS");

                mViewPager.setAdapter(myPostedJObsViewPAdapter);
            }
        };

        if (runnable != null)
            mHandler.post(runnable);

    }

}
