package com.example.easycarpoolapp.navigation

import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import com.example.easycarpoolapp.fragment.post.dto.UserPostDto
import com.example.easycarpoolapp.navigation.profile.report.dto.AccuseDto
import com.example.easycarpoolapp.navigation.profile.report.dto.ReportDto
import com.example.easycarpoolapp.navigation.progress.dto.PostReviewDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


//image전송할 수 있도록 변경
interface NavigationAPI {

    @Multipart
    @POST("/api/user/driverAuth")
    public fun getDriverAuthCall(
        @Part image_id: MultipartBody.Part,
        @Part image_car: MultipartBody.Part,
        @Part("email") email: RequestBody?,
        @Part("carNumber") carNumber: RequestBody?,
        @Part("manufacturer") manufacturer: RequestBody?,
        @Part("model") model: RequestBody?
    ): Call<ResponseBody>

    @POST("/api/post/getUserPostData")
    public fun getUserPostDataCall(@Body localUserDto: LocalUserDto): Call<UserPostDto>

    @POST("api/post/progressToComplete")
    public fun progressToComplete(@Body postReviewDto: PostReviewDto): Call<String>
    //Navigation 진행현황 -> detail -> 후기작성후 완료 버튼 클릭 -> 후기 테이블로 전송


    @POST("api/report/accuse")
    fun getAccuseCall(@Body accuseDto: AccuseDto): Call<String>
    //AccuseUserFragment -> NavigationRepository

    @POST("api/report/reportAdmin")
    fun getReportCall(@Body reportDto: ReportDto): Call<String>


    @Multipart
    @POST("/api/user/editProfile")
    public fun getEditProfileCall(
        @Part("email") email: RequestBody?,
        @Part profile_image: MultipartBody.Part?,
        @Part("nickname") nickname: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("introduce_message") introduce_message: RequestBody?
    ): Call<ResponseBody>




}