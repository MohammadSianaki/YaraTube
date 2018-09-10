package com.yaratech.yaratube.data.source.remote;

import com.yaratech.yaratube.data.model.api.CommentResponse;
import com.yaratech.yaratube.data.model.api.GoogleLoginResponse;
import com.yaratech.yaratube.data.model.api.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.api.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.model.api.ProfileResponseOne;
import com.yaratech.yaratube.data.model.api.StoreResponse;
import com.yaratech.yaratube.data.model.other.Category;
import com.yaratech.yaratube.data.model.other.Comment;
import com.yaratech.yaratube.data.model.other.Product;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.yaratech.yaratube.utils.AppConstants.CATEGORY_ID;
import static com.yaratech.yaratube.utils.AppConstants.STORE_ID;

public interface ApiService {

    @GET("category/" + STORE_ID + "/" + CATEGORY_ID)
    Single<Response<List<Category>>> fetchAllCategories();

    @GET("store/" + STORE_ID)
    Single<Response<StoreResponse>> fetchStoreItems();

    @GET("listproducts/{category_id}")
    Single<Response<List<Product>>> fetchProductsByCategoryId(
            @Path("category_id") int categoryId,
            @Query("offset") int offset,
            @Query("limit") int limit);

    @GET("product/{productId}")
    Single<Response<Product>> fetchProductDetailsByProductId(@Path("productId") int productId, @Query("device_os") String deviceOs);

    @GET("comment/{productId}")
    Single<Response<List<Comment>>> fetchCommentOfProductsByProductId(
            @Path("productId") int productId,
            @Query("offset") int offset,
            @Query("limit") int limit);


    @POST("mobile_login_step1/" + STORE_ID)
    @FormUrlEncoded
    Single<Response<MobileLoginStepOneResponse>> mobileLoginStepOne(
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("device_model") String deviceModel,
            @Field("device_os") String deviceOs,
            @Field("gcm") String gcm);


    @POST("mobile_login_step2/" + STORE_ID)
    @FormUrlEncoded
    Single<Response<MobileLoginStepTwoResponse>> mobileLoginStepTwo(
            @Field("nickname") String nickName, @Field("mobile") String mobile,
            @Field("verification_code") String verificationCode, @Field("device_id") String deviceId
    );

    @POST("comment/{product_id}")
    @FormUrlEncoded
    Single<Response<CommentResponse>> submitCommentToProduct(@Path("product_id") int productId, @Header("Authorization") String token, @Field("title") String title,
                                                             @Field("comment_text") String commentText, @Field("score") int score);


    @Multipart
    @POST("profile")
    Single<Response<ProfileResponseOne>> uploadUserProfileInformation(
            @Part MultipartBody.Part imageAvatar,
            @Part("nickname") RequestBody nickName,
            @Part("date_of_birth") RequestBody dateOfBirth,
            @Part("gender") RequestBody gender,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("device_id") RequestBody deviceId,
            @Part("device_model") RequestBody deviceModel,
            @Part("device_os") RequestBody deviceOs,
            @Part("password") RequestBody password);

    @POST("login_google/" + STORE_ID)
    @FormUrlEncoded
    Single<Response<GoogleLoginResponse>> registerUserWithThisGoogleApiToken
            (@Field("token_id") String googleToken,
             @Field("device_id") String deviceId,
             @Field("device_os") String deviceOs,
             @Field("device_model") String deviceModel);
}