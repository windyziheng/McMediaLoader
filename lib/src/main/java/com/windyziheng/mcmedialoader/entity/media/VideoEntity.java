package com.windyziheng.mcmedialoader.entity.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;

import com.windyziheng.mcmedialoader.constant.MediaType;

/**
 * 本地存储的视频对象
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-09
 * @Organization Convergence Ltd.
 */
public class VideoEntity extends MediaEntity {

    private long duration;

    public VideoEntity(String path, long modifiedDate, int size, String mimeType, Size resolution, long duration) {
        super(MediaType.Video, path, modifiedDate, size, mimeType, resolution);
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public String getDurationText() {
        return getRecordTimeText((int) (duration/1000));
    }

    /**
     * 根据录制秒数获取“00:00”格式文本
     */
    private static String getRecordTimeText(int recordSeconds) {
        StringBuilder stringBuilder = new StringBuilder();
        int minute = recordSeconds / 60;
        int second = recordSeconds % 60;
        if (minute < 10) {
            stringBuilder.append("0").append(minute).append(":");
        } else {
            stringBuilder.append(minute).append(":");
        }
        if (second < 10) {
            stringBuilder.append("0").append(second);
        } else {
            stringBuilder.append(second);
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return mediaType + "{" +
                "path = '" + path + '\'' +
                " , modifiedDate = " + modifiedDate +
                " , name = '" + name + '\'' +
                " , parentPath = '" + parentPath + '\'' +
                " , parentName = '" + parentName + '\'' +
                " , size = '" + size + " byte" +
                " , mimeType = '" + mimeType + '\'' +
                " , resolution = " + getResolution().toString() +
                " , duration = " + duration +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.duration);
    }

    protected VideoEntity(Parcel in) {
        super(in);
        this.duration = in.readLong();
    }

    public static final Parcelable.Creator<VideoEntity> CREATOR = new Parcelable.Creator<VideoEntity>() {
        @Override
        public VideoEntity createFromParcel(Parcel source) {
            return new VideoEntity(source);
        }

        @Override
        public VideoEntity[] newArray(int size) {
            return new VideoEntity[size];
        }
    };
}
