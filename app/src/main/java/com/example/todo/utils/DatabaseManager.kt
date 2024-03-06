package com.example.todo.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.todo.data.Task

class DatabaseManager(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "to_do.db"
        const val DATABASE_VERSION = 2
        const val COLUMN_NAME_ID = "id"

        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${Task.TABLE_NAME} (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${Task.COLUMN_NAME_TASK} TEXT," +
            "${Task.COLUMN_NAME_DONE} BOOLEAN," +
            "${Task.COLUMN_NAME_CATEGORY} TEXT)"

        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${Task.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion == 1) {
            db.execSQL("DROP TABLE IF EXISTS ${Task.TABLE_NAME}")
        }
        destroyDatabase(db)
        onCreate(db)
    }

    private fun destroyDatabase(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE)
    }

    //////////////////////////////////////

    fun createTask() {
        val db = writableDatabase
        // db.execSQL("INSERT INTO Task(task, done) VALUES ('Comprar leche', false)")

        var values = ContentValues()
        values.put("task", "Comprar leche")
        values.put("done", false)

        var newRowId = db.insert("Task", null, values)
        Log.i("DATABASE", "New record ID: $newRowId")

        values = ContentValues()
        values.put("task", "Limpiar el coche")
        values.put("done", false)

        newRowId = db.insert("Task", null, values)
        Log.i("DATABASE", "New record id: $newRowId")
    }

    @SuppressLint("Range")
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
        /*if(cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val task = cursor.getString(1)
                val done = cursor.getInt(2) == 1
                Log.i("DATABASE", "$id -> Task: $task, Done: $done")
            } while (cursor.moveToNext())
        }*/

        while(cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex("id"))
            val task = cursor.getString(cursor.getColumnIndex("task"))
            val done = cursor.getInt(cursor.getColumnIndex("done")) == 1
            Log.i("DATABASE", "$id -> Task: $task, Done: $done")
        }
        cursor.close()
    }

    fun updateTask() {
        val db = writableDatabase

        var values = ContentValues()
        values.put("done", true)

        var updateRows = db.update("Task", values, "id = ? OR id = ?", arrayOf("1", "3"))
        Log.i("DATABASE", "Updated records: $updateRows")
    }

    fun deleteTask() {
        val db = writableDatabase

        val deleteRows = db.delete("Task", "id = 1", null)
        Log.i("DATABASE", "Deleted rows: $deleteRows")
    }
}
