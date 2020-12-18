package com.windyziheng.mcmedialoader.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.windyziheng.mcmedialoader.R;
import com.windyziheng.mcmedialoader.constant.GroupType;
import com.windyziheng.mcmedialoader.constant.QueryType;
import com.windyziheng.mcmedialoader.core.MediaGrouper;
import com.windyziheng.mcmedialoader.core.MediaLoader;
import com.windyziheng.mcmedialoader.core.MediaQuerier;
import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.MediaLoaderResult;
import com.windyziheng.mcmedialoader.group.rule.CustomRule;
import com.windyziheng.mcmedialoader.view.GroupRvAdapter;
import com.windyziheng.mcmedialoader.view.MediaRvAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 相册页面
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-15
 * @Organization Convergence Ltd.
 */
public class AlbumActivity extends AppCompatActivity implements MediaLoader.OnProcessListener<MediaEntity>,
        MediaRvAdapter.OnMediaRvListener, GroupRvAdapter.OnGroupRvListener {

    @BindView(R.id.iv_close_activity_main)
    ImageView ivCloseActivityMain;
    @BindView(R.id.iv_group_activity_main)
    ImageView ivGroupActivityMain;
    @BindView(R.id.rv_media_activity_main)
    RecyclerView rvMediaActivityMain;
    @BindView(R.id.rv_group_activity_album)
    RecyclerView rvGroupActivityAlbum;
    @BindView(R.id.drawer_activity_main)
    DrawerLayout drawerActivityMain;
    @BindView(R.id.item_group_activity_album)
    LinearLayout itemGroupActivityAlbum;
    @BindView(R.id.tv_title_activity_album)
    TextView tvTitleActivityAlbum;

    private Context context;

    private MediaLoader<MediaEntity> mediaLoader;
    private MediaLoaderResult<MediaEntity> result;

    private List<MediaEntity> mediaList;
    private List<GroupEntity<MediaEntity>> groupList;
    private MediaRvAdapter mediaRvAdapter;
    private GroupRvAdapter groupRvAdapter;
    private GroupEntity<MediaEntity> curGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        context = this;
        ImmersionBar.with(this)
                //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .transparentBar()
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(true)
                //导航栏图标是深色，不写默认为亮色
                .navigationBarDarkIcon(true)
                .init();
        itemGroupActivityAlbum.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0);
        initRecyclerView();
//        mediaLoader = MediaLoader.createDefaultMediaLoader(this).setOnProcessListener(this);
        mediaLoader = MediaLoader.create(new MediaLoader.Provider<MediaEntity>() {
            @Override
            public MediaQuerier.Querier<MediaEntity> provideQuerier() {
                return MediaQuerier.createDefaultQuerier(context, QueryType.All);
//                return MediaQuerier.createQuerier(queryFactory)
//                        .setQuerySortMediaIsAction(true)
//                        .setQuerySortMediaRule(new ModifiedDateRule<>(false));

            }

            @Override
            public MediaGrouper.Grouper<MediaEntity> providerGrouper() {
                return MediaGrouper.createDefaultGrouper();
//                return MediaGrouper.createGrouper(groupFactory)
//                        .setGroupSortMediaIsAction(true)
//                        .setGroupSortMediaRule(new ModifiedDateRule<>(true))
//                        .setSortDirGroupIsAction(true)
//                        .setSortDirGroupRule(new KeyNameRule<>(true))
//                        .setSortCustomGroupIsAction(true)
//                        .setSortCustomGroupRule(new ListSizeRule<>(false));
            }

            private CustomRule<MediaEntity> paginateRule = new CustomRule<MediaEntity>("paginate") {

                private static final int PAGE_SIZE = 100;

                @Override
                public List<GroupEntity<MediaEntity>> createGroups(@NonNull List<MediaEntity> mediaList) {
                    List<GroupEntity<MediaEntity>> list = new ArrayList<>();
                    for (int i = 0; i < mediaList.size() / PAGE_SIZE + 1; i++) {
                        List<MediaEntity> medias = new ArrayList<>();
                        int start = i * PAGE_SIZE;
                        int end = Math.min((i + 1) * PAGE_SIZE, mediaList.size());
                        String key = (start + 1) + " -- " + end;
                        for (int j = start; j < end; j++) {
                            medias.add(mediaList.get(j));
                        }
                        GroupEntity<MediaEntity> group = new GroupEntity<>(GroupType.Custom, key, key, medias);
                        if (group.isAvailable()) {
                            list.add(group);
                        }
                    }
                    return list;
                }
            };

        }).setOnProcessListener(this);
        mediaLoader.runLogic();
    }

    private void initRecyclerView() {
        mediaList = new ArrayList<>();
        groupList = new ArrayList<>();
        mediaRvAdapter = new MediaRvAdapter(this, mediaList);
        groupRvAdapter = new GroupRvAdapter(this, groupList);
        rvMediaActivityMain.setLayoutManager(new GridLayoutManager(this, 3));
        rvGroupActivityAlbum.setLayoutManager(new LinearLayoutManager(this));
        rvMediaActivityMain.setAdapter(mediaRvAdapter);
        rvGroupActivityAlbum.setAdapter(groupRvAdapter);
        mediaRvAdapter.setListener(this);
        groupRvAdapter.setListener(this);
    }

    private void refreshGroupData() {
        if (result == null || !result.isSuccess()) {
            return;
        }
        List<GroupEntity<MediaEntity>> allGroupList = result.getGroupResult().getAllGroupList();
        if (allGroupList == null) {
            return;
        }
        groupList.clear();
        groupList.addAll(allGroupList);
        if (groupList != null) {
            curGroup = groupList.get(0);
        } else {
            curGroup = null;
        }
        refreshGroupRv();
        refreshMediaList();
    }

    private void refreshMediaList() {
        mediaList.clear();
        if (curGroup != null) {
            tvTitleActivityAlbum.setText(curGroup.getName());
            mediaList.addAll(curGroup.getMediaList());
        } else {
            tvTitleActivityAlbum.setText("Album");
        }
        refreshMediaRv();
    }

    private void refreshGroupRv() {
        groupRvAdapter.setCurGroup(curGroup);
        groupRvAdapter.notifyDataSetChanged();
    }

    private void refreshMediaRv() {
        mediaRvAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_close_activity_main, R.id.iv_group_activity_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close_activity_main:
                finish();
                break;
            case R.id.iv_group_activity_main:
                if (drawerActivityMain.isDrawerOpen(GravityCompat.START)) {
                    drawerActivityMain.closeDrawer(GravityCompat.START);
                } else if (!groupList.isEmpty()) {
                    drawerActivityMain.openDrawer(GravityCompat.START);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMediaStart() {

    }

    @Override
    public void onLoadMediaDone(MediaLoaderResult<MediaEntity> result) {
        this.result = result;
        if (result.isSuccess()) {
            refreshGroupData();
        } else {
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMediaItemClick(MediaEntity media, int position) {
        startActivity(MediaPreviewActivity.createIntent(this, new ArrayList<>(mediaList),
                curGroup.getName(), position));
    }

    @Override
    public void onGroupItemClick(GroupEntity<MediaEntity> group, int position) {
        boolean isGroupChange = curGroup == null || !Objects.equals(curGroup.getKey(), group.getKey());
        curGroup = group;
        refreshGroupRv();
        if (isGroupChange) {
            refreshMediaList();
        }
        drawerActivityMain.closeDrawer(GravityCompat.START);
    }
}
