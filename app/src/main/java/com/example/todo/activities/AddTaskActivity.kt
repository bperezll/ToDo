package com.example.todo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.data.Task
import com.example.todo.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding initialization
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //val taskAdding = intent.getStringExtra(binding.addTaskBody.text.toString())
    }

    fun addTask() {
         Task(-1, binding.addTaskBody.text.toString(), false)
    }
}