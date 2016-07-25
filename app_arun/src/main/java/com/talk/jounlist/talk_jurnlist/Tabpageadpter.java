package com.talk.jounlist.talk_jurnlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Arun on 6/24/2016.
 */
public class Tabpageadpter extends FragmentPagerAdapter {
    public Tabpageadpter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
           /* case 0:
                // Top Rated fragment activity
                return new Programfragment();*/
            case 0:
                // Movies fragment activity
               // return new Waiting();
                return new Gallery_Photos();
            case 1:
                // Games fragment activity
              //  return new ToBeShipped();
                return new Homefragment();
            case 2:
                // Movies fragment activity
              //  return new Shipped();
                return new Sessionframent();
           /* case 4:
                return new Workshop();
                // Movies fragment activity
               // return new Completed();*/
            case 3:
                return new Speaker();
                // Movies fragment activity
                // return new Completed();

                // Movies fragment activity
                // return new Completed();
        }
        return null;

    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
}