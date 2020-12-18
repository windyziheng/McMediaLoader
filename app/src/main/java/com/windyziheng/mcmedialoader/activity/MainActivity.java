package com.windyziheng.mcmedialoader.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.permissionx.guolindev.PermissionX;
import com.windyziheng.mcmedialoader.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页面
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-18
 * @Organization Convergence Ltd.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_title_activity_main)
    TextView tvTitleActivityMain;
    @BindView(R.id.item_start_activity_main)
    LinearLayout itemStartActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .transparentBar()
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(true)
                //导航栏图标是深色，不写默认为亮色
                .navigationBarDarkIcon(true)
                .init();
    }

    @OnClick(R.id.item_start_activity_main)
    public void onViewClicked() {
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Intent intent = new Intent(this, AlbumActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}