package udacity.lsaippa.cooking.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.lsaippa.cooking.R;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_recipes)
    RecyclerView mRecycleView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.pb_main)
    ProgressBar mProgressBar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_generic_error)
    TextView mTvGenericError;

    @BindView(R.id.btn_retry)
    Button mBtnRetry;


    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(this, 2);

        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter();
        mRecycleView.setAdapter(mRecipeAdapter);

    }


}
