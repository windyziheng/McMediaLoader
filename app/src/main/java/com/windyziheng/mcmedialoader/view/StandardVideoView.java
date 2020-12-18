package com.windyziheng.mcmedialoader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.windyziheng.mcmedialoader.R;

public class StandardVideoView extends StandardGSYVideoPlayer {

    private ImageView control;

    public StandardVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public StandardVideoView(Context context) {
        super(context);
    }

    public StandardVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_video_standard;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        control = findViewById(R.id.iv_control);
        control.setOnClickListener(v -> clickStartIcon());
    }

    @Override
    protected void updateStartImage() {
        if (mStartButton != null) {
            if (mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setVisibility(GONE);
                    control.setImageResource(R.drawable.ic_pause_video_play);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setVisibility(VISIBLE);
                    control.setImageResource(R.drawable.ic_restart_video_play);
                } else {
                    imageView.setVisibility(VISIBLE);
                    control.setImageResource(R.drawable.ic_restart_video_play);
                }
            }
        }
    }
}
