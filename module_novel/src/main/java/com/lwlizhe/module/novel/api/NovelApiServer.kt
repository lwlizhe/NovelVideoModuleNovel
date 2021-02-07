package com.lwlizhe.module.novel.api

import com.lwlizhe.module.novel.api.entity.*
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface NovelApiServer {

    companion object {
        const val DMZJ_IMG_REFERER_URL = "http://images.dmzj.com/"

        const val BILIBILI_BASE_URL = "http://app.bilibili.com/"

        const val DILIDILI_BASE_URL = "http://app.dilidili.club/"

        const val NOVEL_BASE_URL = "https://v3api.dmzj.com/novel/"

        const val COMIC_BASE_URL = "https://v3api.dmzj.com/"
    }


    @GET(NOVEL_BASE_URL + "recommend.json")
    suspend fun getNovelReCommend(): Flowable<List<NovelReCommendEntity>>

    @GET(NOVEL_BASE_URL + "recentUpdate/{page}.json ")
    suspend fun getNovelRecentUpdata(@Path("page") page: Long): Flowable<List<NovelRecentUpdataEntity>>

    @GET(NOVEL_BASE_URL + "{id}.json")
    suspend fun getNovelDetail(@Path("id") novelId: Long): Flowable<NovelDetailEntity>

    @GET(NOVEL_BASE_URL + "chapter/{id}.json")
    suspend fun getNovelChapter(@Path("id") novelId: Long): Flowable<List<NovelChapterEntity>>

    @GET(NOVEL_BASE_URL + "download/{id}_{volume_id}_{chapter_id}.txt")
    suspend fun getNovel(
        @Path("id") novelId: Long,
        @Path("volume_id") volumeId: Long,
        @Path("chapter_id") chapterId: Long
    ): Flowable<String>

    @GET("http://v2api.dmzj.com/1/category.json")
    suspend fun getNovelCategory(): Flowable<List<NovelCategoryEntity>>

    @GET(NOVEL_BASE_URL + "filter.json")
    suspend fun getNovelFilter(): Flowable<NovelFilterEntity>

    @GET(NOVEL_BASE_URL + "{cat_id}/{status_id}/{order_id}/{page}.json ")
    suspend fun getNovelList(
        @Path("cat_id") catId: Long,
        @Path("status_id") statusId: Long,
        @Path("order_id") orderId: Long,
        @Path("page") page: Long
    ): Flowable<List<NovelListEntity>>

    @GET("http://v2.api.dmzj.com/search/show/{big_cat_id}/{keywords}/{page}.json ")
    suspend fun getNovelSearchResult(
        @Path("big_cat_id") bigCatId: Long,
        @Path("keywords") keyWords: String?,
        @Path("page") page: Long
    ): Flowable<List<NovelSearchResultEntity>>

    @GET("http://v2.api.dmzj.com/comment/1/{comment_type}/{novel_id}/{page}.json ")
    suspend fun getNovelComment(
        @Path("comment_type") commentType: Long,
        @Path("novel_id") novelId: Long,
        @Path("page") page: Long
    ): Flowable<List<NovelCommentEntity>>
}