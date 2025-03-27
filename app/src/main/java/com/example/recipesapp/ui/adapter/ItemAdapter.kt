package com.example.recipesapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesapp.databinding.ItemRecipeBinding
import com.example.core.domain.model.Recipe
import com.example.core.domain.model.RecipeItem
import com.example.recipesapp.ui.adapter.ItemAdapter.ViewHolder

class ItemAdapter(private val recipes: List<RecipeItem>): RecyclerView.Adapter<ViewHolder>() {

    private lateinit var onClick: OnItemClick

    fun onItemClicked(onClick: OnItemClick){
        this.onClick = onClick
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data = recipes[position]
        holder.bind(data,holder.itemView.context)
        holder.itemView.setOnClickListener { onClick.onClick(data.id) }
    }

    override fun getItemCount(): Int = recipes.size

    inner class ViewHolder(private val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: RecipeItem,context: Context){
            Glide
                .with(context)
                .load(item.image)
                .into(binding.image)
            binding.name.text = item.name
            binding.difficulty.text = item.difficulty
        }
    }

    interface OnItemClick {
        fun onClick(recipe: Int)
    }

}