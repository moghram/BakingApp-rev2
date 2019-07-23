package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.model.StepList;
import com.example.bakingapp.ui.StepDetailActivity;
import com.example.bakingapp.ui.StepDetailFragment;
import com.example.bakingapp.ui.StepListActivity;

import java.util.List;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.ViewHolder> {

    private final StepListActivity mParentActivity;
    private final List<StepList> mValues;
    private final boolean mTwoPane;

    StepsListAdapter(StepListActivity parent, List<StepList> items, boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            StepList item = (StepList) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(Constants.ARG_STEP_ID, item.getId());
                //addition

                //arguments.putParcelable("recipleList",recipeList);
                arguments.putParcelable(Constants.STEP_FRAGMENT_KEY,item);
               // arguments.putParcelableArray(Constants.step_content, mValues);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra(Constants.ARG_STEP_ID, item.getId());
                intent.putExtra("StepContent", item);
                //intent.putExtra(Constants.recipe_content, recipeList);
                intent.putExtra("ddddd", item);
                intent.putExtra(Constants.SELECTED_STEP_ID, item.getId());

                context.startActivity(intent);
            }
        }
    };


    @Override
    public StepsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new StepsListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StepsListAdapter.ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getShortDescription());

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
