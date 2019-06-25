package com.forest.tasktreeman.task

import org.springframework.web.bind.annotation.*

@RestController
class TaskTreeController {

    private var forestMap = mutableMapOf<String, TaskTree>()

    private var taskQueue: TaskQueue = TaskQueue()

    @PostMapping("/tasks")
    fun createTask(
            @RequestParam(required = true) initVal: String
    ): Response {

        if (forestMap[initVal] == null) {
            val taskTree = TaskTree(initVal)
            forestMap[initVal] = taskTree
            taskQueue.enqueue(taskTree.createTask())
        }

        return Response(success = true)
    }

    @GetMapping("/tasks")
    fun getTask(): TaskDTO? {

        val task = taskQueue.dequeue()
        task?.let {
            return TaskDTO(id = it.id, treeId = it.treeId, initVal = it.initVal!!)
        }
        return null
    }

    @PutMapping("/tasks/{Id}")
    fun setResult(
            @PathVariable(required = true) Id: Int,
            @RequestParam(required = true) treeId: String,
            @RequestParam(required = true) result: String
    ): Response {

        val taskTree = forestMap[treeId]
        taskTree?.findTask(Id)?.let {

            it.result = "${it.initVal}$result"

            while (true) {
                val newTask = taskTree.createTask()
                if (newTask == null) {
                    break
                } else taskQueue.enqueue(newTask)
            }
        }

        return Response(success = true)
    }
}