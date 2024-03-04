package com.example.todo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.data.Task
import com.example.todo.data.providers.TaskDAO
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
        addTask()
    }

    private fun addTask() {
         binding.saveTaskButton.setOnClickListener() {
             var task: Task = Task(-1, binding.addTaskBody.text.toString(), false)
             val taskDAO = TaskDAO(this)
             task = taskDAO.insert(task)

             val intent = Intent(this, MainActivity::class.java)
             startActivity(intent)
         }
    }
}