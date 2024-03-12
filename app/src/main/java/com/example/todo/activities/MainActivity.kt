package com.example.todo.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.adapters.TaskAdapter
import com.example.todo.data.Task
import com.example.todo.data.providers.TaskDAO
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.DialogDeleteMessageBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // View Binding declaration

    private lateinit var adapter: TaskAdapter // Adapter declaration

    private var taskList:MutableList<Task> = mutableListOf() // Using Task as a List

    private lateinit var taskDAO: TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskDAO = TaskDAO(this)

        // View binding initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Adding TaskAdapter
        adapter = TaskAdapter(taskList, {
            onDeleteItemListener(it)
        }, {
            onCheckedListener(it)
        }, {
           onEditTaskListener(it) })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Go to AddTaskActivity
        binding.btnFab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        binding.taskSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean { return false }

            override fun onQueryTextChange(newText: String?): Boolean {

                taskList = if (newText.isNullOrEmpty()) {
                    taskDAO.findAll().toMutableList()
                } else {
                    taskDAO.findAll().filter { task -> (task.task).contains(newText, true) }.toMutableList()
                }

                // Update the list to all if empty, or to the ones containing the query
                adapter.updateItems(taskList)
                return true
            }
        })
    }

    // After add task, go back to this Activity with onResume
    override fun onResume() {
        super.onResume()

        // Finding all tasks on db and showing them
        taskList = taskDAO.findAll().toMutableList()
        adapter.updateItems(taskList)

        // Tasks on app makes firstUseText gone
        if (taskList.isNotEmpty()) {
            binding.firstUseText.visibility = View.GONE
        }

        /*
        // See on log the list of tasks
        for(task in taskList) {
            Log.i("DATABASE", task.toString())
        }*/
    }

    // Delete a task with the delete button
    private fun onDeleteItemListener(position:Int) {

        // Delete task dialog creation
        val askForDeleteDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        val deleteMessageBinding: DialogDeleteMessageBinding = DialogDeleteMessageBinding.inflate(layoutInflater)
        askForDeleteDialog.setView(deleteMessageBinding.root)

        // Delete task button
        askForDeleteDialog.setPositiveButton(R.string.btn_delete_task) { _, _ ->
            // Actions to delete the task
            val task = taskList[position]
            val taskDAO = TaskDAO(this)
            taskDAO.delete(task)
            taskList.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        // Cancel button
        askForDeleteDialog.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }

        // Showing the dialog no pressing delete icon
        val alertDialog: AlertDialog = askForDeleteDialog.create()
        alertDialog.show()

        // No tasks on app makes firstUseText visible
        if (taskList.isEmpty()) {
            binding.firstUseText.visibility = View.VISIBLE
        }

        /*binding.taskSearch.setOnClickListener {
            val searchText = binding.taskSearch
            initSearchView(searchText)
        }*/
        // initSearchView(binding.taskSearch as MenuItem)
    }

    // Change true or false in the done column
    private fun onCheckedListener(position:Int) {
        val task = taskList[position]
        task.done = !task.done // Task done change state between true or false
        val taskDAO = TaskDAO(this)
        taskDAO.update(task)
    }

    private fun onEditTaskListener(position: Int) {

        // A put extra is needed for the task ID to be able to edit the task text and enter to editTask mode
        val intent = Intent(this, AddTaskActivity::class.java)
        intent.putExtra("TASK_ID", taskList[position].id) // Put extra to be called on AddTaskActivity
        startActivity(intent)
    }
}

/*val db: DatabaseManager = DatabaseManager(this)
//db.createTask()
db.readTasks()
db.updateTask()
db.readTasks()
db.deleteTask()*/