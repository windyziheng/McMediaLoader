package com.windyziheng.mcmedialoader.entity.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;

import com.windyziheng.mcmedialoader.constant.MediaType;

/**
 * 本地存储的图片对象
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-09
 * @Organization Convergence Ltd.
 */
public class ImageEntity extends MediaEntity {

    public ImageEntity(String path, long modifiedDate, int size, String mimeType, Size resolution) {
        super(MediaType.Image, path, modifiedDate, size, mimeType, resolution);
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
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected ImageEntity(Parcel in) {
        super(in);
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<ImageEntity> CREATOR = new Parcelable.Creator<ImageEntity>() {
        @Override
        public ImageEntity createFromParcel(Parcel source) {
            return new ImageEntity(source);
        }

        @Override
        public ImageEntity[] newArray(int size) {
            return new ImageEntity[size];
        }
    };
}
