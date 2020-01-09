package xyz.do9core.newsapplication.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import xyz.do9core.newsapplication.BuildConfig
import xyz.do9core.newsapplication.data.model.Headline

interface NewsService {

    @GET("/v2/top-headlines")
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    suspend fun getHeadline(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Headline

    @GET("/v2/top-headlines")
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    suspend fun getHeadline(
        @Query("sources") sources: String,
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Headline

    @GET("/v2/everything")
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    suspend fun everything(
        @Query("q") query: String,
        @Query("qInTitle") queryInTitle: String,
        @Query("sources") sources: String,
        @Query("domains") domains: String,
        @Query("excludeDomains") excludeDomains: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): ResponseBody

    @GET("/v2/sources")
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    suspend fun sources(
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("country") country: String
    ): ResponseBody
}