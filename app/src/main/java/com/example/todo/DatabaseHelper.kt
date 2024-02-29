package com.example.todo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
}
