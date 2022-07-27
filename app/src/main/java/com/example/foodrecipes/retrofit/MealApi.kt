package com.example.foodrecipes.retrofit

import com.example.foodrecipes.models.CategoryList
import com.example.foodrecipes.models.MealsCategoryList
import com.example.foodrecipes.models.RandomMealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<RandomMealList>

    @GET("lookup.php?")
    fun getMealDetails(
        @Query("i") id:String):Call<RandomMealList>

    @GET("filter.php?")
    fun getPopularItems(
        @Query("c") categoryName:String):Call<CategoryList>

    @GET("categories.php")
    fun getCategory():Call<MealsCategoryList>

}