package com.example.foodrecipes.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentHomeBinding
import com.example.foodrecipes.models.Meal
import com.example.foodrecipes.models.RandomMealList
import com.example.foodrecipes.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //запрос на получения случайной(рандомной картинки)
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<RandomMealList>{
            override fun onResponse(
                call: Call<RandomMealList>,
                response: Response<RandomMealList>
            ) {
                if (response.body() != null) {
                    val randomMeal:Meal = response.body()!!.meals[0]
                    Glide.with(this@HomeFragment)
                        .load(randomMeal.strMealThumb)
                        .into(binding.imgRandomMeal)
                }else {
                    return
                }

            }

            override fun onFailure(call: Call<RandomMealList>, t: Throwable) {
               // TODO("Not yet implemented")
            }

        })
        
    }


}