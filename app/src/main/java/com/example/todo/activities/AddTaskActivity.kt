package com.example.todo.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        addTask()
    }

    private fun addTask() {
         binding.saveTaskButton.setOnClickListener {

             // Add a task
             val task = Task(-1, binding.addTaskBody.text.toString(), false)
             val taskDAO = TaskDAO(this)
             taskDAO.insert(task)

             // Go back to MainActivity
             val intent = Intent(this, MainActivity::class.java)
             startActivity(intent)
         }
    }
}