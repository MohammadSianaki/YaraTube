package com.yaratech.yaratube.data.source.remote;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.HomeResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetails;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.yaratech.yaratube.utils.AppConstants.CATEGORY_ID;
import static com.yaratech.yaratube.utils.AppConstants.STORE_ID;

public interface ApiService {

    @GET("category/" + STORE_ID + "/" + CATEGORY_ID)
    Call<List<Category>> fetchAllCategories();

    @GET("store/" + STORE_ID)
    Call<HomeResponse> fetchStoreItems();

    @GET("listproducts/{category_id}")
    Call<List<Product>> fetchProductsByCategoryId(@Path("category_id") int categoryId);

    @GET("product/{productId}")
    Call<ProductDetails> fetchProductDetailsByProductId(@Path("productId") int productId);

    @GET("comment/{productId}")
    Call<List<Comment>> fetchCommentOfProductsByProductId(@Path("productId") int productId);


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
            @Field("mobile") String mobile,
            @Field("device_id") String deviceId,
            @Field("verification_code") String verificationCode,
            @Field("nickname") String nickName
    );
}
