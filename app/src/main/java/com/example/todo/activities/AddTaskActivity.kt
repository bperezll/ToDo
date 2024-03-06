package com.example.todo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.data.Task
import com.example.todo.data.providers.TaskDAO
import com.example.todo.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddTaskBinding

    // Needed variable to find ID of selected task to edit and to make conditional to select between addTask and editTask
    private var taskId: Int = -1

    private lateinit var taskDAO: TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding initialization
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        taskDAO = TaskDAO(this)

        taskId = intent.getIntExtra("TASK_ID", -1) // Get Int Extra for Edit Task mode

        // Checking if ID exists to enter addTask or editTask mode

        if (taskId == -1) {
            addTask()
        } else {
            editTask()
        }
    }

    private fun addTask() {
         binding.saveTaskButton.setOnClickListener {

             // Add a task
             val task = Task(-1, binding.addTaskBody.text.toString(), false, "Default")
             taskDAO.insert(task)

             // Go back to MainActivity finishing AddTaskActivity
             finish()
         }
    }

    private fun editTask() {
        val task = taskDAO.find(taskId)!! // Value task equals to the task ID

        binding.addTaskBody.setText(task.task) // Set text to the already added text

        // changing text to the title and button
        binding.addTaskTitle.setText(R.string.update_task_title)
        binding.saveTaskButton.setText(R.string.btn_update_task)

        // Functionality of save button when in edit mode
        binding.saveTaskButton.setOnClickListener {

            // Edit a task
            task.task = binding.addTaskBody.text.toString()
            taskDAO.update(task)

            // Go back to MainActivity finishing AddTaskActivity
            finish()
        }
    }
}