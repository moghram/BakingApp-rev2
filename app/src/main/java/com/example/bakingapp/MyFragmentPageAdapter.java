package com.example.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.bakingapp.model.StepList;
import com.example.bakingapp.ui.StepDetailFragment;

import java.util.List;

public class MyFragmentPageAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<StepList> mSteps;

    public MyFragmentPageAdapter(Context context, List<StepList> stepList, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        this.mSteps = stepList;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(Constants.STEP_FRAGMENT_KEY, mSteps.get(position));
        //arguments.putParcelable(Constants.ARG_STEP_LIST,mSteps);

        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Ingredients";
        else if (position ==1)
            return "Intorduction";
        else
        return String.format(mContext.getString(R.string.step_num), position);
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }
}
