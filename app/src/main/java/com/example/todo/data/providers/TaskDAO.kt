package com.example.todo.data.providers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.todo.data.Task
import com.example.todo.utils.DatabaseManager

class TaskDAO(context: Context) {

    private var databaseManager: DatabaseManager = DatabaseManager(context)
    fun insert(task: Task): Task {
        val db = databaseManager.writableDatabase

        var values = ContentValues()
        values.put(Task.COLUMN_NAME_TASK, task.task)
        values.put(Task.COLUMN_NAME_DONE, task.done)

        var newRowId = db.insert(Task.TABLE_NAME, null, values)
        Log.i("DATABASE", "New record ID: $newRowId")

        db.close()

        task.id = newRowId.toInt()
        return task
    }

    fun update(task: Task) {
        val db = databaseManager.writableDatabase

        var values = ContentValues()
        values.put(Task.COLUMN_NAME_TASK, task.task)
        values.put(Task.COLUMN_NAME_DONE, task.done)

        var updateRows = db.update(Task.TABLE_NAME, values, "id = ${task.id}", null)
        Log.i("DATABASE", "Updated records: $updateRows")

        db.close()
    }

    fun delete(task: Task) {
        val db = databaseManager.writableDatabase

        val deleteRows = db.delete(Task.TABLE_NAME, "id = ${task.id}", null)
        Log.i("DATABASE", "Deleted rows: $deleteRows")

        db.close()
    }

    @SuppressLint("Range")
    fun find(id: Int): Task? {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Task.TABLE_NAME,
            Task.COLUMN_NAMES,
            "${DatabaseManager.COLUMN_NAME_ID} = $id",
            null,
            null,
            null,
            null
        )

        var task: Task? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val taskName = cursor.getString(cursor.getColumnIndex(Task.COLUMN_NAME_TASK))
            val done = cursor.getInt(cursor.getColumnIndex(Task.COLUMN_NAME_TASK)) == 1
            //Log.i("DATABASE", "$id -> Task: $taskName, Done: $done")

            task = Task(id, taskName, done)
        }

        cursor.close()
        db.close()

        return task
    }

    @SuppressLint("Range")
    fun findAll(): List<Task> {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Task.TABLE_NAME,
            Task.COLUMN_NAMES,
            null,
            null,
            null,
            null,
            null
        )

        var list: MutableList<Task> = mutableListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val taskName = cursor.getString(cursor.getColumnIndex(Task.COLUMN_NAME_TASK))
            val done = cursor.getInt(cursor.getColumnIndex(Task.COLUMN_NAME_DONE)) == 1
            //Log.i("DATABASE", "$id -> Task: $taskName, Done: $done")

            val task: Task = Task(id, taskName, done)

            list.add(task)
        }

        cursor.close()
        db.close()
        return list
    }
}