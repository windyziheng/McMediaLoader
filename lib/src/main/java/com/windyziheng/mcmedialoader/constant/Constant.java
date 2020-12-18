package com.windyziheng.mcmedialoader.constant;

/**
 * 常量封装类
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public class Constant {

    /**
     * 所有多媒体分组KEY
     */
    public static final String GROUP_KEY_ALL = "All";
    /**
     * 所有图片类型多媒体分组KEY
     */
    public static final String GROUP_KEY_IMAGE = "Image";
    /**
     * 所有视频类型多媒体分组KEY
     */
    public static final String GROUP_KEY_VIDEO = "Video";

    /**
     * 默认是否在分组结果中，按目录路径中，采用“Type-Custom-Dir”顺序输出分组列表，即Dir类最后
     */
    public static final boolean DEFAULT_IS_RESULT_DIR_GROUP_LAST = true;
    /**
     * 默认是否在检索完成后，对多媒体列表按照指定规则进行排序
     */
    public static final boolean DEFAULT_IS_QUERY_SORT_MEDIA_ACTION = true;
    /**
     * 默认是否在分组完成后，对所有分组对象的多媒体列表按照统一的规则进行排序
     */
    public static final boolean DEFAULT_IS_GROUP_SORT_MEDIA_ACTION = true;
    /**
     * 默认是否在分组完成后，对按目录路径分组的分组对象按照指定规则进行排序
     */
    public static final boolean DEFAULT_IS_SORT_DIR_GROUP_ACTION = true;
    /**
     * 默认是否在分组完成后，对按自定义分组对象按照指定规则进行排序
     */
    public static final boolean DEFAULT_IS_SORT_CUSTOM_GROUP_ACTION = true;
}
