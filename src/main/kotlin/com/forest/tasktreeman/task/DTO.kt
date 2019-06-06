package com.forest.tasktreeman.task

data class TaskDTO(
        var id: Int,
        var treeId: String,
        var initVal: String
)

data class Response(
        var success: Boolean
)