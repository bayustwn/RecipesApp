package com.example.recipesapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.databinding.ActivityHomeBinding
import com.example.core.domain.model.Recipe
import com.example.core.domain.model.RecipeItem
import com.example.core.presentation.state.RecipesUiState
import com.example.recipesapp.ui.adapter.ItemAdapter
import com.example.core.presentation.viewmodel.RecipesViewModel
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val recipesViewModel: RecipesViewModel by viewModel()
    private lateinit var adapter: ItemAdapter

    private lateinit var splitInstallManager: SplitInstallManager
    private val favoriteModule = "favorite"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Home"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        splitInstallManager = SplitInstallManagerFactory.create(this)

        recipesViewModel.getAllRecipes().apply {
            lifecycleScope.launch {
                recipesViewModel.recipesState.collect { state ->
                    when (state) {
                        is RecipesUiState.Error -> {
                            binding.apply {
                                loading.visibility = View.VISIBLE
                                listRecipes.visibility = View.GONE
                                Log.d("ERRORS",state.message)
                                Toast.makeText(
                                    this@HomeActivity,
                                    getString(R.string.error_displaying_recipes) + state.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        is RecipesUiState.Loading -> {
                            binding.apply {
                                loading.visibility = View.VISIBLE
                                listRecipes.visibility = View.GONE
                            }
                        }
                        is RecipesUiState.Success<*> -> {
                            val recipes: List<RecipeItem> = state.data as List<RecipeItem>
                            adapter = ItemAdapter(recipes)
                            adapter.onItemClicked(object : ItemAdapter.OnItemClick {
                                override fun onClick(recipe: Int) {
                                    startActivity(
                                        Intent(this@HomeActivity, DetailRecipeActivity::class.java)
                                            .putExtra(DetailRecipeActivity.RECIPES_ID, recipe)
                                    )
                                }
                            })
                            binding.apply {
                                loading.visibility = View.GONE
                                listRecipes.visibility = View.VISIBLE
                                listRecipes.adapter = this@HomeActivity.adapter
                                listRecipes.layoutManager =
                                    LinearLayoutManager(this@HomeActivity)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                if (splitInstallManager.installedModules.contains(favoriteModule)) {
                    navigateToFavorite()
                } else {
                    val request = SplitInstallRequest.newBuilder()
                        .addModule(favoriteModule)
                        .build()

                    splitInstallManager.startInstall(request)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Loading Favorite Feature", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Failed Loading Favorite Feature",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToFavorite() {
        val intent = Intent()
        intent.setClassName(packageName, "com.example.favorite.ui.FavoriteActivity")
        startActivity(intent)
    }
}
