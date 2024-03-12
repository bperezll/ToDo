package com.example.todo.data.providers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.todo.data.Category
import com.example.todo.data.Task
import com.example.todo.utils.DatabaseManager

class CategoryDAO(context: Context) {

    private var databaseManager: DatabaseManager = DatabaseManager(context)
    fun insert(category: Category): Category {
        val db = databaseManager.writableDatabase

        var values = ContentValues()
        values.put(Category.COLUMN_NAME_CATEGORY, category.category)
        values.put(Category.COLUMN_NAME_TASK_COUNTER, category.taskCounter)

        var newRowId = db.insert(Category.TABLE_NAME, null, values)
        Log.i("DATABASE", "New record ID: $newRowId")

        db.close()

        category.id = newRowId.toInt()
        return category
    }

    fun update(category: Category) {
        val db = databaseManager.writableDatabase

        var values = ContentValues()
        values.put(Category.COLUMN_NAME_CATEGORY, category.category)
        values.put(Category.COLUMN_NAME_TASK_COUNTER, category.taskCounter)

        var updateRows = db.update(Category.TABLE_NAME, values, "${DatabaseManager.COLUMN_NAME_ID} = ${category.id}", null)
        Log.i("DATABASE", "Updated records: $updateRows")

        db.close()
    }

    fun delete(category: Category) {
        val db = databaseManager.writableDatabase

        val deleteRows = db.delete(Category.TABLE_NAME, "${DatabaseManager.COLUMN_NAME_ID} = ${category.id}", null)
        Log.i("DATABASE", "Deleted rows: $deleteRows")

        db.close()
    }

    @SuppressLint("Range")
    fun find(id: Int): Category? {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Category.TABLE_NAME,
            Category.COLUMN_NAMES,
            "${DatabaseManager.COLUMN_NAME_ID} = $id",
            null,
            null,
            null,
            null
        )

        var category: Category? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val categoryName = cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME_CATEGORY))
            val taskCounter = cursor.getInt(cursor.getColumnIndex(Category.COLUMN_NAME_TASK_COUNTER))
            //Log.i("DATABASE", "$id -> Task: $taskName, Done: $done")

            category = Category(id, categoryName, taskCounter)
        }

        cursor.close()
        db.close()

        return category
    }

    @SuppressLint("Range")
    fun findAll(): List<Category> {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Category.TABLE_NAME,
            Category.COLUMN_NAMES,
            null,
            null,
            null,
            null,
            null
        )

        var list: MutableList<Category> = mutableListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val categoryName = cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME_CATEGORY))
            val taskCounter = cursor.getInt(cursor.getColumnIndex(Category.COLUMN_NAME_TASK_COUNTER))
            //Log.i("DATABASE", "$id -> Task: $taskName, Done: $done")

            val category: Category = Category(id, categoryName, taskCounter)

            list.add(category)
        }

        cursor.close()
        db.close()

        return list
    }
}