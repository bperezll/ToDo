package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Category
import com.example.todo.data.Task
import com.example.todo.databinding.ItemCategoryListBinding
import com.example.todo.databinding.ItemTaskListBinding

class CategoryAdapter(private var items:List<Category> = listOf(),
                  private val onDeleteCategoryListener: (position:Int) -> Unit,
                  private val onEditCategoryListener: (position:Int) -> Unit
    ) : RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.render(items[position])
        holder.binding.deleteCategoryImageButton.setOnClickListener { onDeleteCategoryListener(position) }
        holder.binding.categoryTextView.setOnClickListener { onEditCategoryListener(position) }
    }

    fun updateItems(results: List<Category>?) {
        items = results!!
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(val binding: ItemCategoryListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(category: Category) {
        binding.categoryTextView.text = category.category
    }
}