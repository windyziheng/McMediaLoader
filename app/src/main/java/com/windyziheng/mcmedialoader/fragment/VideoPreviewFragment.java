package com.windyziheng.mcmedialoader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.windyziheng.mcmedialoader.R;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.view.StandardVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 视频预览Fragment
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-16
 * @Organization Convergence Ltd.
 */
public class VideoPreviewFragment extends Fragment {

    @BindView(R.id.video_fragment_video_preview)
    StandardVideoView videoFragmentVideoPreview;
    private MediaEntity media;

    public VideoPreviewFragment(MediaEntity media) {
        this.media = media;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_preview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        Glide.with(this).load(media.getPath()).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_pic_loading).error(R.drawable.ic_pic_error))
                .into(imageView);
        videoFragmentVideoPreview.setUp(media.getPath(), true, media.getName());
        videoFragmentVideoPreview.setThumbImageView(imageView);
//        videoFragmentVideoPreview.startPlayLogic();
    }

    @Override
    public void onPause() {
        super.onPause();
        videoFragmentVideoPreview.release();
    }
}
