package ir.apptune.coffechi.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ir.apptune.coffechi.fragments.FirstAdvertiseFragment;
import ir.apptune.coffechi.fragments.SecondAdvertiseFragment;
import ir.apptune.coffechi.fragments.SignUpFragment;


public class WelcomeFragmentAdapter extends FragmentPagerAdapter {
    SignUpFragment signUpFragment = new SignUpFragment();

    public WelcomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FirstAdvertiseFragment.newInstance();
            case 1:
                return SecondAdvertiseFragment.newInstance();
            case 2:
                return signUpFragment;
        }
        return null;
        }

        @Override
        public int getCount () {
            return 3;
        }
    }
