package com.example.foodrecipes.retrofit

import com.example.foodrecipes.models.RandomMealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<RandomMealList>
}