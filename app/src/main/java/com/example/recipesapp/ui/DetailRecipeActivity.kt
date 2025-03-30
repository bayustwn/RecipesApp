package com.example.recipesapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recipesapp.R
import com.example.recipesapp.databinding.ActivityDetailRecipeBinding
import com.example.core.domain.model.Recipe
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.core.domain.model.RecipeItem
import com.example.presentation.presentation.state.RecipesUiState
import com.example.presentation.presentation.viewmodel.FavoriteViewModel
import com.example.presentation.presentation.viewmodel.RecipesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecipeBinding
    private val recipesViewModel: RecipesViewModel by viewModel()
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var data: RecipeItem
    private var favorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            this.title =""
            this.setDisplayHomeAsUpEnabled(true)
            this.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            this.elevation = 0f
        }
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.scrollview.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.favoriteToggle.hide()
            } else {
                binding.favoriteToggle.show()
            }
        }

        val recipe = intent.getIntExtra(RECIPES_ID,0)

        recipesViewModel.getRecipeById(recipe).apply {
            lifecycleScope.launch {
                recipesViewModel.recipeDetailState.collect { state->
                    when(state){
                        is RecipesUiState.Error -> {
                            binding.apply {
                                this.loading.visibility = View.VISIBLE
                                this.main.visibility = View.GONE
                                Toast.makeText(this@DetailRecipeActivity,
                                    getString(R.string.error_displaying_recipes), Toast.LENGTH_LONG).show()
                            }
                        }
                        is RecipesUiState.Loading -> {
                            binding.apply {
                                this.main.visibility = View.GONE
                                this.loading.visibility = View.VISIBLE
                            }
                        }
                        is RecipesUiState.Success<*> -> {

                            binding.apply {
                                this.main.visibility = View.VISIBLE
                                this.loading.visibility = View.GONE
                            }
                            val data = state.data as Recipe
                            prepareToggle(data.id)
                            this@DetailRecipeActivity.data = RecipeItem(id = data.id, name = data.name, difficulty = data.difficulty, image = data.image)
                            Glide
                                .with(this@DetailRecipeActivity)
                                .load(data.image)
                                .into(binding.image)

                            binding.apply {
                                this.name.text = data.name
                                this.type.text = data.mealType[0]
                            }
                            var i = 0
                            var j = 0
                            for(item in data.ingredients){
                                val textView = TextView(this@DetailRecipeActivity)
                                textView.text = "${i+1}.${item}"
                                binding.listIngredients.addView(textView)
                                i++
                            }

                            for (item in data.instructions){
                                val textView = TextView(this@DetailRecipeActivity)
                                textView.text = "${j+1}.${item}"
                                binding.listInstruction.addView(textView)
                                j++
                            }
                        }
                    }
                }
            }
        }
    }

    private fun prepareToggle(id:Int){
        lifecycleScope.launch {
            favoriteViewModel.checkFavorite(id).apply {
                favoriteViewModel.recipeItemState.collect { fav->
                    if (fav != null){
                        favorite = true
                        binding.favoriteToggle.setImageResource(R.drawable.favorite_icon)
                    }else{
                        favorite = false
                        binding.favoriteToggle.setImageResource(R.drawable.favorite_icon_false)
                    }
                }
            }
        }

        binding.favoriteToggle.setOnClickListener {
            if (favorite){

                lifecycleScope.launch {
                    favoriteViewModel.deleteFavorite(data.id).apply {
                        binding.favoriteToggle.setImageResource(R.drawable.favorite_icon_false)
                    }
                }

            }else{
                lifecycleScope.launch {
                    favoriteViewModel.addFavorite(data).apply {
                        binding.favoriteToggle.setImageResource(R.drawable.favorite_icon)
                    }
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object{
        const val RECIPES_ID = "recipes_id"
    }

}