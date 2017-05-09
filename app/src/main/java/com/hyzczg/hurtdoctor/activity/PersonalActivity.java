package com.hyzczg.hurtdoctor.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
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

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private DoctorBean mDoctorBean;
    private ActivityDoctorBinding mDoctorBinding;
    private RecyclerView.Adapter mRecycleAdapter;
    private ArrayList<MovieBean> mMovieList = new ArrayList<>();

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
        mRecycleAdapter = new CommonRecycleAdapter<MovieBean>(this, mMovieList, R.layout.item_movie) {

            @Override
            public void bind(CommonRecycleHolder holder, MovieBean bean) {
                holder.setText(R.id.tv, bean.getTitle()).setImageUrl(R.id.iv, bean.getImages().getLarge());
            }
        };
        mDoctorBinding.rv.setAdapter(mRecycleAdapter);
        mDoctorBinding.rv.setLayoutManager(new GridLayoutManager(this, 3));
        mDoctorBinding.rv.setItemAnimator(new DefaultItemAnimator());
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
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                mRecycleAdapter.notifyDataSetChanged();
            }
        };
        RetrofitHelper.getInstance().getMovies(mMoviesSubscriber, 0, 200);
    }
}
