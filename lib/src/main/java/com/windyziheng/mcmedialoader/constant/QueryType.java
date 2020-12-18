package com.windyziheng.mcmedialoader.constant;

import com.windyziheng.mcmedialoader.query.info.ImageQueryInfo;
import com.windyziheng.mcmedialoader.query.info.QueryInfo;
import com.windyziheng.mcmedialoader.query.info.VideoQueryInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author WangZiheng
 * @CreateDate 2020-12-09
 * @Organization Convergence Ltd.
 */
public enum QueryType {

    //图片及视频
    All(new ImageQueryInfo(), new VideoQueryInfo()),
    //仅图片
    Image(new ImageQueryInfo()),
    //仅视频
    Video(new VideoQueryInfo());

    private List<QueryInfo> queryInfoList;

    QueryType(QueryInfo... infos) {
        queryInfoList = new ArrayList<>();
        queryInfoList.addAll(Arrays.asList(infos));
    }

    public List<QueryInfo> getQueryInfoList() {
        return queryInfoList;
    }
}
