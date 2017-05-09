package com.hyzczg.hurtdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * recycle view 对应的通用adapter
 */
public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter {
    /**
     * 用于recycle view的{@link CommonRecycleAdapter}多条目支持接口，维护一些类型
     *
     * @param <T> 条目数据类型
     */
    public interface MultiItemTypeSupport<T> {
        /**
         * 根据条目位置，设置条目类型
         * @param position 位置
         * @param t 数据bean
         * @return 类型
         */
        int getItemViewType(int position, T t);

        /**
         * 根据条目类型，返回对应的布局id
         * @param viewType 条目类型
         * @return 布局id
         */
        int getLayoutId(int viewType);

    }

    private MultiItemTypeSupport<T> mTypeSupport;

    private final int INVALID_LAYOUT_ID = -1;

    private int mLayoutId = INVALID_LAYOUT_ID;
    /**
     * adapter的数据源
     */
    private List<T> mData;

    private Context mContext;

    /**
     * 条目多种类型时使用此构造
     *
     * @param context
     * @param source
     * @param typeSupport {@link MultiItemTypeSupport}的实现
     */
    public CommonRecycleAdapter(Context context, List<T> source, MultiItemTypeSupport typeSupport) {
        mContext = context;
        mData = source;
        mTypeSupport = typeSupport;
    }

    /**
     * 单条目时使用此构造
     *
     * @param context
     * @param source
     * @param layoutId 布局id
     */
    public CommonRecycleAdapter(Context context, List<T> source, int layoutId) {
        mContext = context;
        mData = source;
        mLayoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        return INVALID_LAYOUT_ID == mLayoutId ? mTypeSupport.getItemViewType(position, mData.get(position)) : super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = INVALID_LAYOUT_ID == mLayoutId ? mTypeSupport.getLayoutId(viewType) : mLayoutId;
        return new CommonRecycleHolder(LayoutInflater.from(mContext).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T bean = mData.get(position);
        convert((CommonRecycleHolder) holder, bean);
    }

    public abstract void convert(CommonRecycleHolder holder, T bean);

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
