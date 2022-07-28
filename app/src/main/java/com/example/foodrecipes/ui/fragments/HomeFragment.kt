package com.example.foodrecipes.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipes.adapters.CategoryAdapter
import com.example.foodrecipes.adapters.PopularMealsAdapters
import com.example.foodrecipes.databinding.FragmentHomeBinding
import com.example.foodrecipes.models.CategoryMeals
import com.example.foodrecipes.models.Meal
import com.example.foodrecipes.ui.activity.CategoryMealsActivity
import com.example.foodrecipes.ui.activity.MainActivity
import com.example.foodrecipes.ui.activity.MealActivity
import com.example.foodrecipes.ui.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: PopularMealsAdapters
    private lateinit var categoryAdapter: CategoryAdapter

    companion object {
        const val MEAL_ID = "com.example.foodrecipes.ui.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodrecipes.ui.fragments.nameMeal"
        const val MEAL_THUMD = "com.example.foodrecipes.ui.fragments.thumbMeal"

        const val CATEGORY_NAME = "com.example.foodrecipes.ui.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        popularItemsAdapter = PopularMealsAdapters()
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

        viewModel.getRandomMeal()
        observerRandomMeal()

        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()

        preparePopularItemsRecyclerView()

        onPopularItemClick()

        prepareCategoryRecyclerView()
        viewModel.getCategory()
        observeCategoryLiveData()

        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = {category->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategoryLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { category ->
           categoryAdapter.setCategoryList(category)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMD, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(
            viewLifecycleOwner,
        ) { mealList ->
            popularItemsAdapter.setMeals(mealList as ArrayList<CategoryMeals> /* = java.util.ArrayList<com.example.foodrecipes.models.CategoryMeals> */)

        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMD, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }


}