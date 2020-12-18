package com.windyziheng.mcmedialoader.core;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.SortFactory;
import com.windyziheng.mcmedialoader.sort.media.MediaSortFactory;
import com.windyziheng.mcmedialoader.sort.media.MediaSortTask;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 多媒体排序器
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public class MediaSorter<T extends MediaEntity> {

    protected MediaSorter() {

    }

    /**
     * 创建多媒体排序操作执行类
     *
     * @param rule 多媒体排序规则
     * @param <E>  多媒体类
     * @return 多媒体排序操作执行类
     */
    public static <E extends MediaEntity> Sorter<E> createSorter(SortMediaRule<E> rule) {
        return new Sorter<>(rule);
    }

    /**
     * 多媒体排序操作执行类
     *
     * @param <Y> 多媒体类
     */
    public static class Sorter<Y extends MediaEntity> {

        private MediaSortFactory<Y> mediaSortFactory;

        public Sorter(@NonNull SortMediaRule<Y> rule) {
            mediaSortFactory = new MediaSortFactory<>(rule);
        }

        /**
         * 执行排序操作
         *
         * @param mediaList 多媒体列表
         * @param listener  排序监听
         */
        public void sort(@NonNull List<Y> mediaList, @NonNull SortFactory.OnSortListener<Y> listener) {
            MediaSortTask<Y> mediaSortTask = new MediaSortTask<>(mediaSortFactory, listener);
            MediaEntity[] medias = (MediaEntity[]) Array.newInstance(MediaEntity.class, mediaList.size());
            for (int i = 0; i < mediaList.size(); i++) {
                medias[i] = mediaList.get(i);
            }
            mediaSortTask.execute((Y[]) medias);
        }
    }
}
