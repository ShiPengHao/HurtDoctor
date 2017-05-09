package com.hyzczg.hurtdoctor.api;

import com.hyzczg.hurtdoctor.bean.Movies;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 豆瓣电影相关api
 */

public interface MovieService {
    @GET("top250")
    Observable<Response<Movies>> getMovies(@Query("start") int start, @Query("count") int count);
}
