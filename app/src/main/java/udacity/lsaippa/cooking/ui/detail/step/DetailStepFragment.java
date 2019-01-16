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

public class DetailStepFragment extends Fragment {

    @BindView(R.id.simple_exo_player_view)
    PlayerView mPlayerView;

    @BindView(R.id.tv_step_description)
    TextView mDescription;

    private long playerTimePosition;
    private ExoPlayer exoPlayer;
    private Step currentStep;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_detail_step,container,false);
        currentStep = getArguments().getParcelable(STEP_TAG);

        ButterKnife.bind(this,view);

        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        if (currentStep != null){
            mDescription.setText(currentStep.getDescription());
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23 && currentStep != null) {
            setUpPlayer(currentStep.getVideoURL());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || exoPlayer == null) && currentStep != null) {
            setUpPlayer(currentStep.getVideoURL());
        }
    }

    private void setUpPlayer(String stepUrlPath) {

        if(stepUrlPath != null && !stepUrlPath.isEmpty()) {

            TrackSelector trackSelector = new DefaultTrackSelector();


            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            exoPlayer.prepare(buildMediaSource(Uri.parse(stepUrlPath)), false, true);
            exoPlayer.seekTo(playerTimePosition);
            exoPlayer.setPlayWhenReady(true);

            mPlayerView.setPlayer(exoPlayer);

        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }


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
        if (exoPlayer != null) {
            playerTimePosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

}
