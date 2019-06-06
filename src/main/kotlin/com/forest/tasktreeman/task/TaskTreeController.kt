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
    ) {
        /*initVal - начальное значение для Task1.

        * При вызове данного метода менеджер должен начать
        * процесс выполнения иерархии задач, начиная с Task1 и далее запускать задачи
        * в соответствии с представленной иерархией. Метод может быть вызван
        * неограниченное количество раз параллельно за короткий промежуток
        * времени, и все запущенные на выполнение иерархии с различными
        * начальными значениями должны выполняться параллельно и независимо друг от друга*/

         if (forestMap[initVal] == null) {
             val taskTree = TaskTree(initVal)
             forestMap[initVal] = taskTree
             taskQueue.enqueue(taskTree.root)
         }
    }

    @PostMapping("/task-take")
    fun giveTask(): Task? {

        /*Метод предназначен для "взятия" задания
        * на выполнение условным воркером. Менеджер должен вернуть в ответе на
        * этот запрос одно задание с установленным начальным значением, которое
        * нужно выполнить.*/

        return taskQueue.dequeue()

    }

    @PostMapping("/task-result")
    fun processResult(
            @RequestParam(required = true) Id: Int,
            @RequestParam(required = true) treeId: String,
            @RequestParam(required = true) result: String
    ) {
        /*id - идентификатор конкретного экземпляра,
        * result - строка <результат>, которая далее будет входным
        * значением для следующего по иерархии задания.
        *
        * Метод предназначен для уведомления о завершении
        * конкретного экземпляра задания.*/

        val taskTree = forestMap[treeId]
        taskTree?.find(Id)?.let {
            it.leftChild  = Task(id = idGen.getNext(), treeId = treeId, initVal = result)
            it.rightChild = Task(id = idGen.getNext(), treeId = treeId, initVal = result)
            taskQueue.enqueue(it.leftChild)
            taskQueue.enqueue(it.rightChild)

        }

    }

}