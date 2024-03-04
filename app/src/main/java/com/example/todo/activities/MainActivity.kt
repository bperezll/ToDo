package com.example.todo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.data.Task
import com.example.todo.data.providers.TaskDAO
import com.example.todo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // View Binding declaration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // FAB button action
        binding.btnFab.setOnClickListener {

        }

        var task: Task = Task(-1, "Comprar leche", false)

        val taskDAO = TaskDAO(this)

        task = taskDAO.insert(task)

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

        // Go to AddTaskActivity
        binding.btnFab.setOnClickListener() {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }



    /*private fun createTaskDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        addTaskTitle = builder.findViewById(R.id.addTaskTitle)
        editTextName.setText("Name here")

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }*/
}