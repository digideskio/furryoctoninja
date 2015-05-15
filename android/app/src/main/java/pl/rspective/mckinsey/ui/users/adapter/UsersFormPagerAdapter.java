package pl.rspective.mckinsey.ui.users.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import pl.rspective.mckinsey.ui.users.UserListFragment;
import pl.rspective.mckinsey.ui.utils.SmartFragmentStatePagerAdapter;

public class UsersFormPagerAdapter extends SmartFragmentStatePagerAdapter {

    public UsersFormPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UserListFragment.newInstance(false);
            case 1:
                return UserListFragment.newInstance(true);
            default:
                return UserListFragment.newInstance(false);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Nie wypełnili"; //TODO nazwy trzeba lepiej dobrać :)
            case 1:
                return "Wypełnili";
            default:
                return "";
        }
    }

}