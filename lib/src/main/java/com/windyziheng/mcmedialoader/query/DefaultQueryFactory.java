package com.windyziheng.mcmedialoader.query;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Size;

import com.windyziheng.mcmedialoader.constant.MediaType;
import com.windyziheng.mcmedialoader.constant.QueryType;
import com.windyziheng.mcmedialoader.entity.media.ImageEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.media.VideoEntity;
import com.windyziheng.mcmedialoader.query.info.QueryInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认的多媒体查询工厂实现
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public final class DefaultQueryFactory extends QueryFactory<MediaEntity> {

    protected QueryType queryType;
    protected boolean isQueryRoot;
    protected List<QueryInfo> queryInfoList;

    protected DefaultQueryFactory(Creator creator) {
        super(creator.context);
        queryType = creator.queryType;
        isQueryRoot = creator.isQueryRoot;
        queryInfoList = queryType.getQueryInfoList();
    }

    /**
     * 设置是否查询根目录多媒体文件
     */
    public void setQueryRoot(boolean queryRoot) {
        isQueryRoot = queryRoot;
    }

    @Override
    protected boolean onSetup() {
        return true;
    }

    @Override
    protected List<MediaEntity> onQuery() {
        List<MediaEntity> mediaList = new ArrayList<>();
        if (!isWorking) {
            return mediaList;
        }
        //使用ContentResolver检索本地多媒体文件
        ContentResolver contentResolver = context.getContentResolver();
        //QueryInfo中封装了ContentResolver检索需要的相关信息，遍历信息列表即可
        for (QueryInfo queryInfo : queryInfoList) {
            if (!queryInfo.isAvailable()) {
                continue;
            }
            //根据QueryInfo中信息获取检索指针
            Cursor cursor = contentResolver.query(queryInfo.getUri(), queryInfo.getProjection(),
                    queryInfo.getSelection(), queryInfo.getSelectionArgs(), queryInfo.getOrder());
            if (cursor == null) {
                continue;
            }
            //遍历检索指针，每一步均对多媒体列表进行处理
            while (!cursor.isLast()) {
                cursor.moveToNext();
                processQueryData(queryInfo.getMediaType(), mediaList, cursor);
            }
            cursor.close();
        }
        return mediaList;
    }

    @Override
    protected void onRelease() {

    }

    /**
     * 处理查询结果
     *
     * @param mediaType 多媒体类型
     * @param mediaList 多媒体数据列表
     * @param cursor    数据库查询指针
     */
    protected void processQueryData(MediaType mediaType, List<MediaEntity> mediaList, Cursor cursor) {
        switch (mediaType) {
            case Image:
                processImageData(mediaList, cursor);
                break;
            case Video:
                processVideoData(mediaList, cursor);
                break;
            default:
                break;
        }
    }

    /**
     * 处理图片数据
     *
     * @param mediaList 多媒体数据列表
     * @param cursor    数据库查询指针
     */
    protected void processImageData(List<MediaEntity> mediaList, Cursor cursor) {
        // 1.获取图片的路径
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        //2.获取图片的修改日期
        long modifiedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED));
        if (isRootPath(path) && !isQueryRoot) {
            //不获取sd卡根目录下的图片
            return;
        }
        //3.获取图片的文件大小
        int size = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
        //4.获取图片的MIME Type
        String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
        //5.获取图片的分辨率
        int width = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.WIDTH));
        int height = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT));
        Size resolution = new Size(width, height);

        MediaEntity image = new ImageEntity(path, modifiedDate, size, mimeType, resolution);
        mediaList.add(image);
    }

    /**
     * 处理视频数据
     *
     * @param mediaList 多媒体数据列表
     * @param cursor    数据库查询指针
     */
    protected void processVideoData(List<MediaEntity> mediaList, Cursor cursor) {
        // 1.获取视频的路径
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        if (isRootPath(path) && !isQueryRoot) {
            //不获取sd卡根目录下的视频
            return;
        }
        //2.获取视频的修改日期
        long modifiedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED));
        //3.获取视频的文件大小
        int size = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
        //4.获取视频的MIME Type
        String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
        //5.获取视频的分辨率
        String resolutionText = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.RESOLUTION));
        int width = 0;
        int height = 0;
        if (!TextUtils.isEmpty(resolutionText)) {
            String[] texts = resolutionText.split("[×x]");
            if (texts != null && texts.length > 0) {
                width = Integer.parseInt(texts[0]);
                height = Integer.parseInt(texts[1]);
            }
        }
        Size resolution = new Size(width, height);
        //6.获取视频的时长
        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));

        MediaEntity video = new VideoEntity(path, modifiedDate, size, mimeType, resolution, duration);
        mediaList.add(video);
    }

    /**
     * 判断是否根目录下的路径
     *
     * @param path 路径
     * @return 是否根目录下的路径
     */
    protected static boolean isRootPath(String path) {
        File parentFile = new File(path).getParentFile();
        return parentFile == null || !parentFile.exists();
    }

    public static class Creator {

        private Context context;
        private QueryType queryType;
        private boolean isQueryRoot = false;

        public Creator(Context context, QueryType queryType) {
            this.context = context;
            this.queryType = queryType;
        }

        /**
         * 设置是否查询根目录多媒体文件
         */
        public Creator setQueryRoot(boolean isQueryRoot) {
            this.isQueryRoot = isQueryRoot;
            return this;
        }

        public DefaultQueryFactory create() {
            return new DefaultQueryFactory(this);
        }
    }
}
