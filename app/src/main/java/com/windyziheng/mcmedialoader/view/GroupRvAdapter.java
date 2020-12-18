package com.windyziheng.mcmedialoader.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.windyziheng.mcmedialoader.R;
import com.windyziheng.mcmedialoader.constant.MediaType;
import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分组列表RV适配器
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-15
 * @Organization Convergence Ltd.
 */
public class GroupRvAdapter extends RecyclerView.Adapter<GroupRvAdapter.ViewHolder> {

    private Context context;
    private List<GroupEntity<MediaEntity>> groupList;
    private GroupEntity<MediaEntity> curGroup;
    private OnGroupRvListener listener;

    public GroupRvAdapter(Context context, List<GroupEntity<MediaEntity>> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    public void setCurGroup(GroupEntity<MediaEntity> curGroup) {
        this.curGroup = curGroup;
    }

    public void setListener(OnGroupRvListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_group, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupEntity<MediaEntity> group = groupList.get(position);
        if (curGroup != null && Objects.equals(curGroup.getKey(), group.getKey())) {
            holder.itemRvGroup.setBackgroundColor(getColor(R.color.colorPrimary));
            holder.tvNameRvGroup.setTextColor(getColor(R.color.colorWhite));
            holder.tvSizeRvGroup.setTextColor(getColor(R.color.colorWhite));
            holder.tvKeyRvGroup.setTextColor(getColor(R.color.colorWhite));
        } else {
            holder.itemRvGroup.setBackgroundColor(getColor(android.R.color.transparent));
            holder.tvNameRvGroup.setTextColor(getColor(R.color.colorTextMain));
            holder.tvSizeRvGroup.setTextColor(getColor(R.color.colorTextMain));
            holder.tvKeyRvGroup.setTextColor(getColor(R.color.colorTextSecondary));
        }
        holder.tvNameRvGroup.setText(group.getName());
        holder.tvSizeRvGroup.setText(group.getGroupInfo().getCount() + "");
        holder.tvKeyRvGroup.setText(group.getKey());
        MediaEntity media = group.getGroupInfo().getCoverEntity();
        if (media.getMediaType() == MediaType.Video) {
            holder.ivCoverPlayRvGroup.setVisibility(View.VISIBLE);
        } else {
            holder.ivCoverPlayRvGroup.setVisibility(View.GONE);
        }
        Glide.with(context).load(media.getPath())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(holder.ivCoverRvGroup);
        holder.itemRvGroup.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGroupItemClick(group, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    private int getColor(@ColorRes int id) {
        return context.getResources().getColor(id);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover_rv_group)
        ImageView ivCoverRvGroup;
        @BindView(R.id.iv_cover_play_rv_group)
        ImageView ivCoverPlayRvGroup;
        @BindView(R.id.item_cover_rv_group)
        FrameLayout itemCoverRvGroup;
        @BindView(R.id.tv_name_rv_group)
        TextView tvNameRvGroup;
        @BindView(R.id.tv_size_rv_group)
        TextView tvSizeRvGroup;
        @BindView(R.id.tv_key_rv_group)
        TextView tvKeyRvGroup;
        @BindView(R.id.item_rv_group)
        ConstraintLayout itemRvGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnGroupRvListener {

        /**
         * 控件点击回调
         *
         * @param group    分组对象数据
         * @param position 序号
         */
        void onGroupItemClick(GroupEntity<MediaEntity> group, int position);
    }
}
