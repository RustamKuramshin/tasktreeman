package com.forest.tasktreeman.task

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskTreeController {

    private var forestMap = mutableMapOf<String, TaskTree>()

    private var taskQueue: TaskQueue = TaskQueue()

    @PostMapping("/run-new")
    fun runTask(
            @RequestParam(required = true) initVal: String
    ): Response {

        if (forestMap[initVal] == null) {
            val taskTree = TaskTree(initVal)
            forestMap[initVal] = taskTree
            taskQueue.enqueue(taskTree.createTask())
        }

        return Response(success = true)
    }

    @PostMapping("/task-take")
    fun giveTask(): TaskDTO? {

        val task = taskQueue.dequeue()
        task?.let {
            return TaskDTO(id = it.id, treeId = it.treeId, initVal = it.initVal!!)
        }
        return null
    }

    @PostMapping("/task-result")
    fun processResult(
            @RequestParam(required = true) Id: Int,
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