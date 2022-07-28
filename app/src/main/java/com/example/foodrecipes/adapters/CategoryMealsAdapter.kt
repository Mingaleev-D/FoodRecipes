package com.example.foodrecipes.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipes.databinding.MealItemBinding
import com.example.foodrecipes.models.CategoryMeals

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    private var mealsList = ArrayList<CategoryMeals>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMealsList(mealsList: List<CategoryMeals>) {
        this.mealsList =
            mealsList as ArrayList<CategoryMeals> /* = java.util.ArrayList<com.example.foodrecipes.models.MealsCategoryList> */
        notifyDataSetChanged()
    }

    inner class CategoryMealsViewHolder(var binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}