package com.example.foodrecipes.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipes.models.Meal
import com.example.foodrecipes.models.RandomMealList
import com.example.foodrecipes.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel:ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id:String){
       RetrofitInstance.api.getMealDetails(id).enqueue(object:Callback<RandomMealList>{
           override fun onResponse(call: Call<RandomMealList>, response: Response<RandomMealList>) {
               if (response.body() != null) {
                   mealDetailsLiveData.value = response.body()!!.meals[0]
               }else {
                   return
               }
           }

           override fun onFailure(call: Call<RandomMealList>, t: Throwable) {
               //TODO("Not yet implemented")
           }

       })
    }
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

}

