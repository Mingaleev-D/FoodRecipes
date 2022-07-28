package com.example.foodrecipes.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.ActivityMealBinding
import com.example.foodrecipes.db.MealDatabase
import com.example.foodrecipes.models.Meal
import com.example.foodrecipes.ui.fragments.HomeFragment
import com.example.foodrecipes.ui.viewModel.MealViewModel
import com.example.foodrecipes.ui.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink:String

    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInformationViews()

        loadingCase()
        mealMvvm.getMealDetails(mealId)
        observerMealDetailsLiveData()

        onYouTubeImageClick()

        onFavoriteClick()

    }

    private fun onFavoriteClick() {
       binding.btnAddFav.setOnClickListener {
           mealToSave?.let {
               mealMvvm.insertMeal(it)
               Toast.makeText(this, "Save Meal", Toast.LENGTH_SHORT).show()
           }
       }
    }

    private fun onYouTubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave : Meal? = null
    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                mealToSave = meal

                binding.apply {
                    tvCategory.text = "Category ${meal!!.strCategory}"
                    tvArea.text = "Area ${meal!!.strArea}"
                    tvInstructionsStr.text = meal.strInstructions
                }
                youtubeLink = meal!!.strYoutube
            }

        })
    }

    private fun setInformationViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMD)!!
    }

    private fun loadingCase(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            btnAddFav.visibility = View.INVISIBLE
            tvInstructions.visibility = View.INVISIBLE
            tvCategory.visibility = View.INVISIBLE
            tvArea.visibility = View.INVISIBLE
            imgYoutube.visibility = View.INVISIBLE
        }
    }

    private fun onResponseCase(){
        binding.apply {
            progressBar.visibility = View.INVISIBLE
            btnAddFav.visibility = View.VISIBLE
            tvInstructions.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvArea.visibility = View.VISIBLE
            imgYoutube.visibility = View.VISIBLE
        }

    }


}