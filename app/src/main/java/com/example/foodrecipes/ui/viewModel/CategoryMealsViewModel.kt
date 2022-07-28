package com.example.foodrecipes.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipes.models.CategoryList
import com.example.foodrecipes.models.CategoryMeals
import com.example.foodrecipes.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {
    val mealsLiveData = MutableLiveData<List<CategoryMeals>>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object:Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { mealsList->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                //TODO("Not yet implemented")
            }

        })
    }
    fun observeMealsLiveData(): LiveData<List<CategoryMeals>>{
        return mealsLiveData
    }
}