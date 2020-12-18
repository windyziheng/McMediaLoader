package com.windyziheng.mcmedialoader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.windyziheng.mcmedialoader.R;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片预览Fragment
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-16
 * @Organization Convergence Ltd.
 */
public class ImagePreviewFragment extends Fragment {

    @BindView(R.id.iv_image_fragment_image_preview)
    PhotoView ivImageFragmentImagePreview;

    private MediaEntity media;

    public ImagePreviewFragment(MediaEntity media) {
        this.media = media;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_preview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivImageFragmentImagePreview.enable();
        Glide.with(this).load(media.getPath()).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivImageFragmentImagePreview);
    }
}
