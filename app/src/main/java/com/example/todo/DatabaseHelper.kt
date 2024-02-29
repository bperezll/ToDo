package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "to_do.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE =
            "CREATE TABLE Task (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "task TEXT," +
                    "done BOOLEAN)"

        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS task"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        destroyDatabase(db)
        onCreate(db)
    }

    private fun destroyDatabase(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE)
    }

    fun createTask() {
        val db = writableDatabase
        // db.execSQL("INSERT INTO Task(task, done) VALUES ('Comprar leche', false)")

        var values = ContentValues()
        values.put("task", "Comprar leche")
        values.put("done", false)

        val newRowId = db.insert("Task", null, values)
        Log.i("databasew", "New record ID: $newRowId")

        values = ContentValues()
        values.put("task", "Limpiar el coche")
        values.put("done", false)
    }

    fun readTasks() {
        val db= writableDatabase
        val cursor = db.query(
            "Task",
            arrayOf("id", "task", "done"),
            null,
            null,
            null,
            null,
            "done DESC"
        )
        if(cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val task = cursor.getString(1)
                val done = cursor.getInt(2) == 1
                Log.i("DATABASE", "$id -> Task: $task, Done: $done")
            } while (cursor.moveToNext())
        }
    }
}
