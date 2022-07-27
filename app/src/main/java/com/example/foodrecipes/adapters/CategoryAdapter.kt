package com.example.foodrecipes.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipes.databinding.CategoryItemBinding
import com.example.foodrecipes.models.Category


class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.MyCategoryViewHolder>() {

    private var categoryList = ArrayList<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList =
            categoryList as ArrayList<Category> /* = java.util.ArrayList<com.example.foodrecipes.models.Category> */
        notifyDataSetChanged()
    }

    inner class MyCategoryViewHolder(var binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
        return MyCategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}