package com.example.todo.data

class Task(var id: Int, var task: String, var done: Boolean) {

    override fun toString(): String {
        return "$id -> Task: $task - $done"
    }
}

