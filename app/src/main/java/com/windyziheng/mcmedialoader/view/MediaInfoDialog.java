package com.windyziheng.mcmedialoader.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.windyziheng.mcmedialoader.R;
import com.windyziheng.mcmedialoader.constant.MediaType;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.media.VideoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 多媒体信息底部弹出
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-17
 * @Organization Convergence Ltd.
 */
public class MediaInfoDialog extends BottomSheetDialog {

    @BindView(R.id.tv_name_dialog_media_info)
    TextView tvNameDialogMediaInfo;
    @BindView(R.id.tv_mime_type_dialog_media_info)
    TextView tvMimeTypeDialogMediaInfo;
    @BindView(R.id.tv_resolution_dialog_media_info)
    TextView tvResolutionDialogMediaInfo;
    @BindView(R.id.tv_size_dialog_media_info)
    TextView tvSizeDialogMediaInfo;
    @BindView(R.id.tv_modified_date_dialog_media_info)
    TextView tvModifiedDateDialogMediaInfo;
    @BindView(R.id.tv_duration_dialog_media_info)
    TextView tvDurationDialogMediaInfo;
    @BindView(R.id.tv_path_dialog_media_info)
    TextView tvPathDialogMediaInfo;

    public MediaInfoDialog(@NonNull Context context) {
        super(context, R.style.MediaInfoDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_media_info, null, false);
        setContentView(bottomSheetView);
        ButterKnife.bind(this);
        //点击外部Dialog消失
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams attr = dialogWindow.getAttributes();
        if (attr != null) {
            attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
            attr.gravity = Gravity.BOTTOM;
            dialogWindow.setAttributes(attr);
        }

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(((View) bottomSheetView.getParent()));
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void refresh(MediaEntity media) {
        tvNameDialogMediaInfo.setText(media.getName());
        tvMimeTypeDialogMediaInfo.setText(media.getMimeType());
        tvResolutionDialogMediaInfo.setText(media.getResolution().toString());
        tvSizeDialogMediaInfo.setText(media.getSizeText());
        tvModifiedDateDialogMediaInfo.setText(media.getModifiedDateText());
        if (media.getMediaType() == MediaType.Video && media instanceof VideoEntity) {
            VideoEntity video = (VideoEntity) media;
            tvDurationDialogMediaInfo.setVisibility(View.VISIBLE);
            tvDurationDialogMediaInfo.setText(video.getDurationText());
        } else {
            tvDurationDialogMediaInfo.setVisibility(View.GONE);
        }
        tvPathDialogMediaInfo.setText(media.getPath());
    }
}
