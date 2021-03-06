package com.hyzczg.hurtdoctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyzczg.hurtdoctor.utils.MyApp;

/**
 * recycle view 对应的通用view holder
 */
public class CommonRecycleHolder extends RecyclerView.ViewHolder {

    public final int mLayoutId;
    private SparseArray<View> mViews;

    public CommonRecycleHolder(View itemView,int layoutId) {
        super(itemView);
        mViews = new SparseArray<>();
        mLayoutId = layoutId;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public CommonRecycleHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonRecycleHolder setImageUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        Glide.with(MyApp.getMyApp()).load(url).into(imageView);
        return this;
    }

}
