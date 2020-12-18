package com.windyziheng.mcmedialoader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.windyziheng.mcmedialoader.R;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.view.MediaInfoDialog;
import com.windyziheng.mcmedialoader.view.MediaPreviewVpAdapter;
import com.windyziheng.mcmedialoader.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 多媒体预览页面
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-16
 * @Organization Convergence Ltd.
 */
public class MediaPreviewActivity extends AppCompatActivity {

    public static final String KEY_MEDIA_LIST = "mediaList";
    public static final String KEY_GROUP_NAME = "group";
    public static final String KEY_POSITION = "position";

    @BindView(R.id.iv_close_activity_media_preview)
    ImageView ivCloseActivityMediaPreview;
    @BindView(R.id.tv_group_activity_media_preview)
    TextView tvGroupActivityMediaPreview;
    @BindView(R.id.tv_index_activity_media_preview)
    TextView tvIndexActivityMediaPreview;
    @BindView(R.id.tv_separator_activity_media_preview)
    TextView tvSeparatorActivityMediaPreview;
    @BindView(R.id.tv_sum_activity_media_preview)
    TextView tvSumActivityMediaPreview;
    @BindView(R.id.iv_info_activity_media_preview)
    ImageView ivInfoActivityMediaPreview;
    @BindView(R.id.item_header_activity_media_preview)
    FrameLayout itemHeaderActivityMediaPreview;
    @BindView(R.id.vp_activity_media_preview)
    NoScrollViewPager vpActivityMediaPreview;

    private MediaInfoDialog mediaInfoDialog;
    private List<MediaEntity> mediaList;
    private String groupName;
    private int curPosition = 0;

    public static Intent createIntent(Context context, ArrayList<MediaEntity> mediaList, String groupName, int position) {
        Intent intent = new Intent(context, MediaPreviewActivity.class);
        intent.putExtra(KEY_MEDIA_LIST, mediaList);
        intent.putExtra(KEY_GROUP_NAME, groupName);
        intent.putExtra(KEY_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_preview);
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .transparentBar()
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(false)
                //导航栏图标是深色，不写默认为亮色
                .navigationBarDarkIcon(false)
                .init();
        initData();
        initViewPager();
        initView();
    }

    private void initData() {
        mediaList = new ArrayList<>();
        Intent intent = getIntent();
        List<MediaEntity> data = intent.getParcelableArrayListExtra(KEY_MEDIA_LIST);
        groupName = intent.getStringExtra(KEY_GROUP_NAME);
        curPosition = intent.getIntExtra(KEY_POSITION, 0);
        if (data == null || data.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mediaList.addAll(data);
    }

    private void initViewPager() {
        if (mediaList.isEmpty()) {
            return;
        }
        MediaPreviewVpAdapter vpAdapter = new MediaPreviewVpAdapter(getSupportFragmentManager(), mediaList);
        vpActivityMediaPreview.setAdapter(vpAdapter);
        vpActivityMediaPreview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                tvIndexActivityMediaPreview.setText(getPositionText());
                if (mediaInfoDialog != null) {
                    mediaInfoDialog.refresh(mediaList.get(curPosition));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        if (mediaList.isEmpty()) {
            return;
        }
        if (!TextUtils.isEmpty(groupName)) {
            tvGroupActivityMediaPreview.setText(groupName);
        }
        tvIndexActivityMediaPreview.setText(getPositionText());
        tvSumActivityMediaPreview.setText(mediaList.size() + "");
        vpActivityMediaPreview.setCurrentItem(curPosition, false);
        vpActivityMediaPreview.setOnClickListener(v -> {
            boolean isVisible = itemHeaderActivityMediaPreview.getVisibility() == View.VISIBLE;
            itemHeaderActivityMediaPreview.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        });
    }

    private String getPositionText() {
        return (curPosition + 1) + "";
    }

    @OnClick({R.id.iv_close_activity_media_preview, R.id.iv_info_activity_media_preview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close_activity_media_preview:
                finish();
                break;
            case R.id.iv_info_activity_media_preview:
                if (mediaList.isEmpty()) {
                    break;
                }
                if (mediaInfoDialog == null) {
                    mediaInfoDialog = new MediaInfoDialog(this);
                }
                if (mediaInfoDialog.isShowing()) {
                    mediaInfoDialog.dismiss();
                } else {
                    mediaInfoDialog.show();
                    mediaInfoDialog.refresh(mediaList.get(curPosition));
                }
                break;
            default:
                break;
        }
    }
}
