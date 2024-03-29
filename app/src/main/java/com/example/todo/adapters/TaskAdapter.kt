package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Task
import com.example.todo.databinding.ItemTaskListBinding

class TaskAdapter(private var items:List<Task> = listOf(),
                  private val onDeleteItemListener: (position:Int) -> Unit,
                  private val onCheckedListener: (position:Int) -> Unit,
                  private val onEditTaskListener: (position:Int) -> Unit
    ) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(items[position])
        holder.binding.deleteImageButton.setOnClickListener { onDeleteItemListener(position) }
        holder.binding.doneCheckbox.setOnCheckedChangeListener { checkbox, isChecked ->
            if (checkbox.isPressed) { onCheckedListener(position) }
        }
        holder.binding.taskTextView.setOnClickListener { onEditTaskListener(position) }
    }

    fun updateItems(results: List<Task>?) {
        items = results!!
        notifyDataSetChanged()
    }
}

class ViewHolder(val binding: ItemTaskListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(task:Task) {
        //binding.doneCheckbox = task.done
        binding.taskTextView.text = task.task
        binding.doneCheckbox.isChecked = task.done

    }
}