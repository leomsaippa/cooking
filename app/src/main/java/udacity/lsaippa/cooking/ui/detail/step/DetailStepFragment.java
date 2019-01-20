package udacity.lsaippa.cooking.ui.detail.step;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Step;

import static udacity.lsaippa.cooking.utils.AppConstants.STEP_TAG;

@SuppressWarnings("ALL")
public class DetailStepFragment extends Fragment {

    public static final String TAG = DetailStepFragment.class.getSimpleName();
    @BindView(R.id.simple_exo_player_view)
    PlayerView mPlayerView;

    @BindView(R.id.tv_step_description)
    TextView mDescription;

    private long mPlayerTimePosition;
    private ExoPlayer mExoPlayer;
    private Step mCurrentStep;

    private static final String PLAYER_TIME_POSITION = "player-position-time";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setExtras();
    }


    private void setExtras(){
        if(getArguments() != null){
            if(getArguments().getParcelable(STEP_TAG) != null){
                mCurrentStep = getArguments().getParcelable(STEP_TAG);
            }

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_detail_step,container,false);
        if(getArguments() != null){
            if(getArguments().getParcelable(STEP_TAG) != null)
                mCurrentStep = getArguments().getParcelable(STEP_TAG);
        }

        ButterKnife.bind(this,view);

        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        if (mCurrentStep != null){
            mDescription.setText(mCurrentStep.getDescription());
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null){
            mPlayerTimePosition = savedInstanceState.getLong(PLAYER_TIME_POSITION, 0L);
        }

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mExoPlayer != null) {
            outState.putLong(PLAYER_TIME_POSITION, mExoPlayer.getCurrentPosition());
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23 && mCurrentStep != null) {
            setUpPlayer(mCurrentStep.getVideoURL());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || mExoPlayer == null) && mCurrentStep != null) {
            setUpPlayer(mCurrentStep.getVideoURL());
        }
    }

    private void setUpPlayer(String stepUrlPath) {

        if(stepUrlPath != null && !stepUrlPath.isEmpty()) {

            TrackSelector trackSelector = new DefaultTrackSelector();


            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            mExoPlayer.prepare(buildMediaSource(Uri.parse(stepUrlPath)), false, true);
            mExoPlayer.seekTo(mPlayerTimePosition);
            mExoPlayer.setPlayWhenReady(true);

            mPlayerView.setPlayer(mExoPlayer);

        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }


    @SuppressWarnings("ConstantConditions")
    private ExtractorMediaSource buildMediaSource(Uri uri) {

        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(getActivity(), "cooking"))
                .createMediaSource(uri);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlayerTimePosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

}
