package com.example.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.Constants;
import com.example.bakingapp.R;
import com.example.bakingapp.model.IngredientList;
import com.example.bakingapp.model.RecipeList;
import com.example.bakingapp.model.StepList;
import com.example.bakingapp.widget.UpdateWidgetService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static RecipeList recipeList;
    private List<IngredientList> ingredientList;
    public static List<StepList> stepList;
    private ArrayList<StepList> nStepList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);



        //mTwoPane = getResources().getBoolean(R.bool.TowPane);

        if(savedInstanceState==null){
            Intent intent = getIntent();
            recipeList = intent.getParcelableExtra(Constants.recipe_content);

            ingredientList = recipeList.getIngredients();
            stepList =recipeList.getSteps();

            String ingredients ="";
            for(int i =0; i<ingredientList.size();i++){
                ingredients +=" * "+ingredientList.get(i).getIngredient()+"("+ingredientList.get(i).getQuantity()+" - "+ingredientList.get(i).getMeasure()+")\n";
            }

            nStepList.add(new StepList("0","Ingredients",ingredients,"",""));
            for(int i=0; i<stepList.size();i++){
                int x = Integer.valueOf(stepList.get(i).getId())+1;
                String ID = Integer.toString(x);
                String ShortDesc =stepList.get(i).getShortDescription() ;
                String Desc = stepList.get(i).getDescription();
                String vURL = stepList.get(i).getVideoURL();
                String imgUrl =stepList.get(i).getThumbnailURL();

                nStepList.add(new StepList(ID, ShortDesc,Desc,vURL,imgUrl));
            }

            stepList=nStepList;
            recipeList.setStepList(nStepList);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(recipeList.getName());
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            if(findViewById(R.id.item_detail_container) != null){
                mTwoPane = true;
            }
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }



    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepsAdapter(this, stepList, mTwoPane));
    }

    public static class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

        private final StepListActivity mParentActivity;
        private final List<StepList> mValues;
        private final boolean mTwoPane;

        StepsAdapter(StepListActivity parent, List<StepList> items, boolean twoPane) {
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

                    arguments.putParcelable("recipleList",recipeList);
                    arguments.putParcelable(Constants.STEP_FRAGMENT_KEY,item);
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
                    intent.putExtra(Constants.recipe_content, recipeList);
                    //intent.putExtra(Constants.step_content, mValues);
                    intent.putExtra(Constants.SELECTED_STEP_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
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
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("RecipeList",recipeList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {

            UpdateWidgetService.updateWidget(this, recipeList);
            //Snackbar.make(coordinatorLayout, String.format(getString(R.string.add), mRecipe.getName()), Snackbar.LENGTH_LONG).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
