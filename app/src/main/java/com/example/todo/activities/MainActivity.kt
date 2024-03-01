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

        var taskFind: Task? = taskDAO.find(2)

        if(taskFind != null) {
            Log.i("DATABASE", taskFind.toString())
            taskFind.done = true
            taskFind.task = "Pagar facturas"

            taskDAO.update(taskFind)
        }

        taskFind = taskDAO.find(2)
        Log.i("DATABASE", taskFind.toString())

        if (taskFind != null) {
            taskDAO.delete(taskFind)
            taskFind = taskDAO.find(2)
            if(taskFind != null) {
                Log.i("DATABASE", taskFind.toString())
            } else {
                Log.i("DATABASE", "La tarea ha sido borrada")
            }
        }
        /*val db: DatabaseManager = DatabaseManager(this)
        //db.createTask()
        db.readTasks()
        db.updateTask()
        db.readTasks()
        db.deleteTask()*/
    }
}