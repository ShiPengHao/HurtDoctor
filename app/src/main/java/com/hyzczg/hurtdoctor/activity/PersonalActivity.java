package com.hyzczg.hurtdoctor.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hyzczg.hurtdoctor.R;
import com.hyzczg.hurtdoctor.adapter.CommonRecycleAdapter;
import com.hyzczg.hurtdoctor.adapter.CommonRecycleHolder;
import com.hyzczg.hurtdoctor.bean.DoctorBean;
import com.hyzczg.hurtdoctor.bean.MovieBean;
import com.hyzczg.hurtdoctor.databinding.ActivityDoctorBinding;
import com.hyzczg.hurtdoctor.utils.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private DoctorBean mDoctorBean;
    private ActivityDoctorBinding mDoctorBinding;
    private RecyclerView.Adapter mRecycleAdapter;
    private ArrayList<MovieBean> mMovieList = new ArrayList<>();
    private Observable<MovieBean> movieBeanObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoctorBinding = DataBindingUtil.setContentView(this, R.layout.activity_doctor);
        setBinding();
//        getMovie();
        getMovieList();
    }

    /**
     * 练习简单数据绑定
     */
    public void setBinding() {
        mDoctorBean = new DoctorBean("孙连城", "410102201704263518", "id1", "h1", "d1", "胸怀宇宙");
        mDoctorBinding.setDoctor(mDoctorBean);
//        mDoctorBean.name = "李达康";
        mDoctorBinding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, String.format("id :%s", mDoctorBean.id), Toast.LENGTH_LONG).show();
        mDoctorBinding.etTitle.setFocusable(true);
        mDoctorBinding.etTitle.setFocusableInTouchMode(true);
        mDoctorBinding.etTitle.requestFocus();
        mDoctorBinding.etTitle.setSelection(mDoctorBean.id.length());
    }

    /**
     * 从豆瓣获取第一篇文章的标题，并吐司显示标题
     */
    private void getMovie() {
        Observer<MovieBean> mMovieSubscriber = new Observer<MovieBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MovieBean movieBean) {
                Toast.makeText(PersonalActivity.this, String.format("movie :%s", movieBean.getTitle()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        };
        RetrofitHelper.getInstance().getMovies(mMovieSubscriber, 0, 1);
    }

    /**
     * 从豆瓣获取一组文章，并显示标题和图片到recycle view
     */
    private void getMovieList() {
        mMovieList.clear();
        CommonRecycleAdapter.MultiItemTypeSupport<MovieBean> typeSupport = new CommonRecycleAdapter.MultiItemTypeSupport<MovieBean>() {

            private final int TYPE1 = 0;
            private final int TYPE2 = 1;

            @Override
            public int getItemViewType(int position, MovieBean movieBean) {
                return position % 2;
            }

            @Override
            public int getLayoutId(int viewType) {
                switch (viewType) {
                    case TYPE1:
                        return R.layout.item_movie;
                    case TYPE2:
                        return R.layout.item_ad;
                }
                return 0;
            }
        };
        mRecycleAdapter = new CommonRecycleAdapter<MovieBean>(this, mMovieList, typeSupport) {

            @Override
            public void bind(CommonRecycleHolder holder, MovieBean bean) {
                switch (holder.mLayoutId) {
                    case R.layout.item_movie:
                        holder.setText(R.id.tv, bean.getTitle()).setImageUrl(R.id.iv, bean.getImages().getLarge());
                        break;
                    case R.layout.item_ad:
                        holder.setText(R.id.tv, bean.getTitle());
                        break;
                }
            }
        };
        mDoctorBinding.rv.setAdapter(mRecycleAdapter);
        mDoctorBinding.rv.setLayoutManager(new LinearLayoutManager(this));
//        mDoctorBinding.rv.setItemAnimator(new DefaultItemAnimator());
        mDoctorBinding.rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Observer<MovieBean> mMoviesSubscriber = new Observer<MovieBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MovieBean movieBean) {
                mMovieList.add(movieBean);
//                mRecycleAdapter.notifyItemInserted(mMovieList.size() - 1);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(PersonalActivity.this, "on error", Toast.LENGTH_SHORT).show();
//                movieBeanObservable.retry();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                mRecycleAdapter.notifyDataSetChanged();
            }
        };
        movieBeanObservable = RetrofitHelper.getInstance().getMovies(mMoviesSubscriber, 0, 200);
    }
}
