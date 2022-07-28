package com.example.foodrecipes.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipes.db.MealDatabase
import com.example.foodrecipes.models.*
import com.example.foodrecipes.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase:MealDatabase
) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    private var categoryLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()

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

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object:Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               // TODO("Not yet implemented")
            }

        })
    }
    fun observePopularItemsLiveData():LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }

    fun getCategory(){
        RetrofitInstance.api.getCategory().enqueue(object:Callback<MealsCategoryList>{
            override fun onResponse(
                call: Call<MealsCategoryList>,
                response: Response<MealsCategoryList>
            ) {
                response.body()?.let { categoryList->
                    categoryLiveData.postValue(categoryList.categories)
                }

            }

            override fun onFailure(call: Call<MealsCategoryList>, t: Throwable) {
                //TODO("Not yet implemented")
            }
        })
    }
    fun observeCategoryLiveData(): MutableLiveData<List<Category>> {
        return categoryLiveData
    }
    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
}