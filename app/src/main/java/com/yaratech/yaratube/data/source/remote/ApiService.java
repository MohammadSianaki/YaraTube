package com.yaratech.yaratube.data.source.remote;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.HomeResponse;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("category/16/463")
    Call<List<Category>> fetchAllCategories();

    @GET("store/16")
    Call<HomeResponse> fetchStoreItems();

    @GET("listproducts/{category_id}")
    Call<List<Product>> fetchProductsByCategoryId(@Path("category_id") int categoryId);

    @GET("product/{productId}")
    Call<ProductDetails> fetchProductDetailsByProductId(@Path("productId") int productId);

    @GET("comment/{productId}")
    Call<List<Comment>> fetchCommentOfProductsByProductId(@Path("productId") int productId);
}
