package com.example.todo.data

import com.example.todo.utils.DatabaseManager

class Category(var id: Int, var category: String, var taskCounter: Int) {

    companion object {
        const val TABLE_NAME = "Category"
        const val COLUMN_NAME_CATEGORY = "category"
        const val COLUMN_NAME_TASK_COUNTER = "task_counter"
        val COLUMN_NAMES = arrayOf(
            DatabaseManager.COLUMN_NAME_ID,
            COLUMN_NAME_CATEGORY,
            COLUMN_NAME_TASK_COUNTER
        )
    }
    override fun toString(): String {
        return "$id -> Category: $category - $taskCounter"
    }
}

