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

class HomeViewModel : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal() {
        //запрос на получения случайной(рандомной картинки)
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<RandomMealList> {
            override fun onResponse(
                call: Call<RandomMealList>,
                response: Response<RandomMealList>
            ) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }

            }

            override fun onFailure(call: Call<RandomMealList>, t: Throwable) {
                // TODO("Not yet implemented")
            }

        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
}