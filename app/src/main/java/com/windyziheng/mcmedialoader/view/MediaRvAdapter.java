package com.windyziheng.mcmedialoader.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.windyziheng.mcmedialoader.R;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 多媒体视图RV适配器
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-15
 * @Organization Convergence Ltd.
 */
public class MediaRvAdapter extends RecyclerView.Adapter<MediaRvAdapter.ViewHolder> {

    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_VIDEO = 1;

    private Context context;
    private List<MediaEntity> mediaList;
    private OnMediaRvListener listener;

    public MediaRvAdapter(Context context, List<MediaEntity> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
    }

    public void setListener(OnMediaRvListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        MediaEntity media = mediaList.get(position);
        switch (media.getMediaType()) {
            case Video:
                return TYPE_VIDEO;
            case Image:
            default:
                return TYPE_IMAGE;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_VIDEO:
                return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_rv_media, parent, false));
            case TYPE_IMAGE:
            default:
                return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_rv_media, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_VIDEO:
                refreshVideoItem((VideoViewHolder) holder, position);
                break;
            case TYPE_IMAGE:
            default:
                refreshImageItem((ImageViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    private void refreshImageItem(@NonNull ImageViewHolder holder, int position) {
        MediaEntity media = mediaList.get(position);
        Glide.with(context).load(media.getPath())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(20)))
                        .placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(holder.ivContentItemImageRvMedia);
        holder.itemImageRvMedia.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMediaItemClick(media, position);
            }
        });
    }

    private void refreshVideoItem(@NonNull VideoViewHolder holder, int position) {
        MediaEntity media = mediaList.get(position);
        Glide.with(context).load(media.getPath())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(20)))
                        .placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(holder.ivVideoItemVideoRvMedia);
        holder.itemVideoRvMedia.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMediaItemClick(media, position);
            }
        });
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected static class ImageViewHolder extends ViewHolder {

        @BindView(R.id.iv_content_item_image_rv_media)
        ImageView ivContentItemImageRvMedia;
        @BindView(R.id.item_image_rv_media)
        RatioFrameLayout itemImageRvMedia;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected static class VideoViewHolder extends ViewHolder {

        @BindView(R.id.iv_video_item_video_rv_media)
        ImageView ivVideoItemVideoRvMedia;
        @BindView(R.id.iv_play_item_video_rv_media)
        ImageView ivPlayItemVideoRvMedia;
        @BindView(R.id.item_video_rv_media)
        RatioFrameLayout itemVideoRvMedia;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnMediaRvListener {

        /**
         * 控件点击回调
         *
         * @param media    多媒体数据
         * @param position 序号
         */
        void onMediaItemClick(MediaEntity media, int position);
    }
}
