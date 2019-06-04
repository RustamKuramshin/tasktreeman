package com.forest.tasktreeman.task

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController{

    var counter: Int = 0

    @PostMapping("/run-new")
    fun runTask() = "Запущена задача № ${++counter}"

    @PostMapping("/task-take")
    fun giveTask() = "Возьмите задачу № ${++counter}"

    @PostMapping("/task-result")
    fun processResult() = "Обрабатываю результат выполнения задачи № ${++counter}"

}