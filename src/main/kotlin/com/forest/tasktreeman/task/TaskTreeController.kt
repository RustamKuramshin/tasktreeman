package com.forest.tasktreeman.task

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskTreeController{

    @PostMapping("/run-new")
    fun runTask(
            @RequestParam(required = true) initStr: String
    ) = "Запущена задача, инициализированная параметром $initStr"

    @PostMapping("/task-take")
    fun giveTask() = "Возьмите задачу с Id 234"

    @PostMapping("/task-result")
    fun processResult(
            @RequestParam(required = true) taskId: Int,
            @RequestParam(required = true) result: String
    ) = "Принят результат $result выполнения задачи с Id $taskId"

}