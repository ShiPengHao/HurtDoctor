package com.hyzczg.hurtdoctor.utils;

import com.hyzczg.hurtdoctor.api.MovieService;
import com.hyzczg.hurtdoctor.bean.MovieBean;
import com.hyzczg.hurtdoctor.bean.Movies;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit实现的api工具类
 */

public class RetrofitHelper {
    private static final String BASE_URL_MOVIE = "https://api.douban.com/v2/movie/";
    private static final int TIMEOUT_DEFAULT = 30;
    private static RetrofitHelper sRetrofitHelper;
    private Retrofit mRetrofit;
    private MovieService mMovieService;

    private RetrofitHelper() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL_MOVIE)
                .build();
    }

    public synchronized static RetrofitHelper getInstance() {
        if (null == sRetrofitHelper) {
            return new RetrofitHelper();
        } else {
            return sRetrofitHelper;
        }
    }

    /**
     * 从豆瓣top250电影api获取指定位置的电影，并将每部电影的bean交给订阅者处理
     *
     * @param subscriber 订阅者
     * @param start      top250起始位置
     * @param count      想要获取的电影数量
     */
    public Observable<MovieBean> getMovies(Observer<MovieBean> subscriber, int start, int count) {
        if (null == mMovieService) {
            mMovieService = mRetrofit.create(MovieService.class);
        }
        Observable<MovieBean> observable = mMovieService.getMovies(start, count)
                .map(new Function<Response<Movies>, List<MovieBean>>() {
                    @Override
                    public List<MovieBean> apply(Response<Movies> doctorsBeanResponse) throws Exception {
                        return doctorsBeanResponse.body().subjects;
                    }
                })
                .flatMap(new Function<List<MovieBean>, Observable<MovieBean>>() {
                    @Override
                    public Observable<MovieBean> apply(List<MovieBean> doctors) throws Exception {
                        return Observable.fromIterable(doctors);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(subscriber);
        return observable;
    }
}
