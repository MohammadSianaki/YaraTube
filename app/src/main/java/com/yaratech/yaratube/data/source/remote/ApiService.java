package com.yaratech.yaratube.data.source.remote;

import com.yaratech.yaratube.data.model.home.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("category/16/463")
    Call<List<Category>> fetchAllCategories();


}
