package com.example.todo.data.providers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.todo.data.Task
import com.example.todo.utils.DatabaseManager

class TaskDAO(private val context: Context) {

    private var databaseManager: DatabaseManager = DatabaseManager(context)
    fun insert(task: Task) {
        val db = databaseManager.writableDatabase

        var values = ContentValues()
        values.put("task", task.task)
        values.put("done", task.done)

        var newRowId = db.insert("Task", null, values)
        Log.i("DATABASE", "New record ID: $newRowId")

        db.close()
    }

    fun update(task: Task) {
        val db = databaseManager.writableDatabase

        var values = ContentValues()
        values.put("task", task.task)
        values.put("done", task.done)

        var updateRows = db.update("Task", values, "id = ${task.id}", null)
        Log.i("DATABASE", "Updated records: $updateRows")

        db.close()
    }

    fun delete(task: Task) {
        val db = databaseManager.writableDatabase

        val deleteRows = db.delete("Task", "id = ${task.id}", null)
        Log.i("DATABASE", "Deleted rows: $deleteRows")

        db.close()
    }

    fun find() {

    }

    @SuppressLint("Range")
    fun findAll() : List<Task> {
        val db = databaseManager.writableDatabase
        val cursor = db.query(
            "Task",
            arrayOf("id", "task", "done"),
            null,
            null,
            null,
            null,
            null
        )

        var list: MutableList<Task> = mutableListOf()

        while(cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val taskName = cursor.getString(cursor.getColumnIndex("task"))
            val done = cursor.getInt(cursor.getColumnIndex("done")) == 1
            Log.i("DATABASE", "$id -> Task: $taskName, Done: $done")

            val task: Task = Task(id, taskName, done)

            list.add(task)
        }

        cursor.close()
        db.close()
        return list
    }
}