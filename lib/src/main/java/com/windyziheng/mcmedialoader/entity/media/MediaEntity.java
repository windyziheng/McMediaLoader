package com.windyziheng.mcmedialoader.entity.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;

import com.windyziheng.mcmedialoader.constant.MediaType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本地存储的多媒体对象
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-09
 * @Organization Convergence Ltd.
 */
public abstract class MediaEntity implements Parcelable {

    private static final float UNIT_THRESHOLD = 0.75f;

    /**
     * 多媒体类型
     */
    protected MediaType mediaType;
    /**
     * 绝对路径
     */
    protected String path;
    /**
     * 修改时间戳
     */
    protected long modifiedDate;
    /**
     * 文件名
     */
    protected String name;
    /**
     * 目录路径
     */
    protected String parentPath;
    /**
     * 目录名
     */
    protected String parentName;
    /**
     * 文件大小
     */
    protected int size;
    /**
     * MIME类型
     */
    protected String mimeType;
    /**
     * 分辨率宽
     */
    protected int width;
    /**
     * 分辨率高
     */
    protected int height;

    protected MediaEntity(MediaType mediaType, String path, long modifiedDate,
                          int size, String mimeType, Size resolution) {
        this.mediaType = mediaType;
        this.path = path;
        this.modifiedDate = modifiedDate;
        this.size = size;
        this.mimeType = mimeType;
        this.width = resolution.getWidth();
        this.height = resolution.getHeight();
        File file = new File(path);
        File parentFile = file.getParentFile();
        this.name = file.getName();
        if (parentFile != null && parentFile.exists()) {
            parentPath = parentFile.getAbsolutePath();
            parentName = parentFile.getName();
        } else {
            parentPath = file.getParent();
            parentName = "root";
        }
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public String getModifiedDateText() {
        return formatDate(modifiedDate);
    }

    public String getName() {
        return name;
    }

    public String getParentPath() {
        return parentPath;
    }

    public String getParentName() {
        return parentName;
    }

    public int getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Size getResolution() {
        return new Size(width, height);
    }

    public String getSizeText() {
        float kValue = (float) size / 1024;
        if (kValue < UNIT_THRESHOLD) {
            return size + " B";
        } else {
            float mValue = kValue / 1024;
            if (mValue < UNIT_THRESHOLD) {
                return formatSize(kValue) + " K";
            } else {
                float gValue = mValue / 1024;
                if (gValue < UNIT_THRESHOLD) {
                    return formatSize(mValue) + " M";
                } else {
                    float tValue = gValue / 1024;
                    if (tValue < UNIT_THRESHOLD) {
                        return formatSize(gValue) + " G";
                    } else {
                        return formatSize(tValue) + " T";
                    }
                }
            }
        }
    }

    protected static String formatSize(float value) {
        return String.format("%.2f", value);
    }

    protected static String formatDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(date));
    }

    @Override
    public String toString() {
        return "MediaEntity{" +
                "mediaType = " + mediaType +
                " , path = '" + path + '\'' +
                " , modifiedDate = " + modifiedDate +
                " , name = '" + name + '\'' +
                " , parentPath = '" + parentPath + '\'' +
                " , parentName = '" + parentName + '\'' +
                " , size = '" + size + " byte" +
                " , mimeType = '" + mimeType + '\'' +
                " , resolution = " + getResolution().toString() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mediaType == null ? -1 : this.mediaType.ordinal());
        dest.writeString(this.path);
        dest.writeLong(this.modifiedDate);
        dest.writeString(this.name);
        dest.writeString(this.parentPath);
        dest.writeString(this.parentName);
        dest.writeInt(this.size);
        dest.writeString(this.mimeType);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected MediaEntity(Parcel in) {
        int tmpMediaType = in.readInt();
        this.mediaType = tmpMediaType == -1 ? null : MediaType.values()[tmpMediaType];
        this.path = in.readString();
        this.modifiedDate = in.readLong();
        this.name = in.readString();
        this.parentPath = in.readString();
        this.parentName = in.readString();
        this.size = in.readInt();
        this.mimeType = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }
}
