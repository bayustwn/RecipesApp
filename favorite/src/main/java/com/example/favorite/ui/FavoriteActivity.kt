package com.example.favorite.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.viewmodel.FavoriteViewModel
import com.example.favorite.R
import com.example.favorite.databinding.ActivityFavoriteBinding
import com.example.recipesapp.ui.DetailRecipeActivity
import com.example.recipesapp.ui.adapter.ItemAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        supportActionBar?.elevation = 0f
        supportActionBar?.title="Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            favoriteViewModel.recipeItemRecipes.collect { fav->
                this@FavoriteActivity.adapter = ItemAdapter(fav)
                adapter.onItemClicked(object: ItemAdapter.OnItemClick{
                    override fun onClick(id: Int) {
                        startActivity(Intent(this@FavoriteActivity, DetailRecipeActivity::class.java)
                            .putExtra(DetailRecipeActivity.RECIPES_ID,id)
                        )
                    }

                })
                binding.fav.adapter = this@FavoriteActivity.adapter
                binding.fav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}