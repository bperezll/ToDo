package com.example.todo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.todo.utils.DatabaseManager
import com.example.todo.R
import com.example.todo.data.Task
import com.example.todo.data.providers.TaskDAO

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task: Task = Task(-1, "Comprar leche", false)

        val taskDAO = TaskDAO(this)
        taskDAO.insert(task)

        val taskList = taskDAO.findAll()

        for(task in taskList) {
            Log.i("DATABASE", task.toString())
        }
        /*val db: DatabaseManager = DatabaseManager(this)
        //db.createTask()
        db.readTasks()
        db.updateTask()
        db.readTasks()
        db.deleteTask()*/
    }
}