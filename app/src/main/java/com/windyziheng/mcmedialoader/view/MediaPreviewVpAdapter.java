package com.windyziheng.mcmedialoader.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.windyziheng.mcmedialoader.fragment.ImagePreviewFragment;
import com.windyziheng.mcmedialoader.fragment.VideoPreviewFragment;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

/**
 * 多媒体预览ViewPager适配器
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-16
 * @Organization Convergence Ltd.
 */
public class MediaPreviewVpAdapter extends FragmentStatePagerAdapter {

    private List<MediaEntity> mediaList;

    public MediaPreviewVpAdapter(@NonNull FragmentManager fm, List<MediaEntity> mediaList) {
        super(fm);
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        MediaEntity media = mediaList.get(position);
        switch (media.getMediaType()) {
            case Video:
                return new VideoPreviewFragment(media);
            case Image:
            default:
                return new ImagePreviewFragment(media);
        }
    }

    @Override
    public int getCount() {
        return mediaList.size();
    }
}
