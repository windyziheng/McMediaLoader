package com.windyziheng.mcmedialoader.sort;

import java.util.List;

/**
 * 排序抽象工厂类
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public abstract class SortFactory<T> {

    protected SortFactory() {

    }

    /**
     * 初始化
     *
     * @return 是否成功
     */
    public boolean setup() {
        return onSetup();
    }

    /**
     * 排序
     *
     * @param originList 待排序的列表
     * @return 排序后的列表
     */
    public List<T> sort(List<T> originList) {
        return onSort(originList);
    }

    /**
     * 释放资源
     */
    public void release() {
        onRelease();
    }

    /**
     * 重写此方法以进行初始化操作
     *
     * @return 默认传true才会进行下一步操作
     */
    protected abstract boolean onSetup();

    /**
     * 重写此方法以实现列表排序
     *
     * @param originList 待排序的列表
     * @return 排序后的列表
     */
    protected abstract List<T> onSort(List<T> originList);

    /**
     * 重写此方法以进行资源释放
     */
    protected abstract void onRelease();

    public interface OnSortListener<T> {

        /**
         * 排序完成
         *
         * @param isSuccess  成功与否
         * @param resultList 成功，则排序后生成的新列表；失败，则返回null
         */
        void onSortDone(boolean isSuccess, List<T> resultList);
    }
}
