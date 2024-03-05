package com.example.todo.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.adapters.TaskAdapter
import com.example.todo.data.Task
import com.example.todo.data.providers.TaskDAO
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.ItemTaskListBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // View Binding declaration

    private lateinit var adapter: TaskAdapter // Adapter declaration

    private var taskList:MutableList<Task> = mutableListOf() // Using Task as a List

    private lateinit var bindingItemTaskList: ItemTaskListBinding // Declaring this view to use the delete task button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Adding TaskAdapter
        adapter = TaskAdapter(taskList) { onDeleteItemListener(it) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Go to AddTaskActivity
        binding.btnFab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    // After add task, go back to this Activity with onResume
    override fun onResume() {
        super.onResume()

        // Finding all tasks on db and showing them
        val taskDAO = TaskDAO(this)
        taskList = taskDAO.findAll().toMutableList()
        adapter.updateItems(taskList)

        /*
        // See on log the list of tasks
        for(task in taskList) {
            Log.i("DATABASE", task.toString())
        }*/
    }

    // For later
    private fun onDeleteItemListener(position:Int) {
        val task = taskList[position]
        val taskDAO = TaskDAO(this)
        taskDAO.delete(task)
        taskList.removeAt(position)
        adapter.notifyDataSetChanged()
        //Toast.makeText(this, getString(horoscope.name), Toast.LENGTH_LONG).show()
    }
}

/*val db: DatabaseManager = DatabaseManager(this)
//db.createTask()
db.readTasks()
db.updateTask()
db.readTasks()
db.deleteTask()*/