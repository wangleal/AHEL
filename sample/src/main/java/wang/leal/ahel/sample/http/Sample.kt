package wang.leal.ahel.sample.http

import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import wang.leal.ahel.http.api.annotation.*

interface Sample {
    @POST
    @FormUrlEncoded
    fun test(@Url url:String, @Field("a") a:String):Observable<Int>

    @POST
    fun testPath(@Url url:String,@Path("a") a:String,@Path("b") b:String):Observable<Int>

    @GET
    @Headers("X-Foo: Bar","X-Ping: Pong")
    fun testResult(@Url url: String,@Query("hehe")q:String,@Header("token") header:String):Observable<Result>

    @GET
    fun testQuery(@Url url: String,@Query("hehe")hehe:String,@Query("haha")haha:String,@Query("heihei")heihei:String):Observable<Unit>

    @POST
    fun testBody(@Url url: String,@Body result: Result):Observable<Result>

    @POST
    fun testEmptyResult(@Url url: String,@Body result: Result):Observable<Void>

    @POST
    fun testEmptyData(@Url url: String,@Body result: Result):Observable<Unit>

    @POST
    @Multipart
    fun testMulti(@Url url: String,@PartMap partMap:Map<String,@JvmSuppressWildcards RequestBody>):Observable<Int>

    @GET
    fun testError(@Url url:String):Observable<Int>
}