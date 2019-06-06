package com.forest.tasktreeman.task

import java.util.Random

data class Task(
        var id: Int,
        var treeId: String,
        var initVal: String? = null,
        var result: String? = null,
        var leftChild: Task? = null,
        var rightChild: Task? = null
)

class TaskTree (val treeId: String){

    var root: Task? = null

    fun findTask(id: Int)
            : Task? {
        var current = root
        while (current!!.id != id) {
            if (id < current.id)
                current = current.leftChild
            else
                current = current.rightChild
            if (current == null)
                return null
        }
        return current
    }

    fun createTask(): Task {
        val newId = idGen.getNext()
        val newNode = Task(id = newId, treeId = treeId)
        if (root == null){
            newNode.initVal = treeId
            root = newNode
            return newNode
        }
        else {
            var current = root
            var parent: Task
            while (true) {
                parent = current!!
                if (newId < current!!.id) {
                    current = current.leftChild
                    if (current == null) {
                        parent.leftChild = newNode
                        newNode.initVal = parent.result
                        return newNode
                    }
                } else {
                    current = current.rightChild
                    if (current == null) {
                        parent.rightChild = newNode
                        newNode.initVal = parent.result
                        return newNode
                    }
                }
            }
        }
    }

}

class TaskQueue {

    var items: MutableList<Task?> = mutableListOf()

    fun isEmpty():Boolean = items.isEmpty()

    fun enqueue(element:Task?){
        items.add(element)
    }

    fun dequeue():Task?{
        if (this.isEmpty()){
            return null
        } else {
            return items.removeAt(0)
        }
    }

}

object idGen {

    private val random = Random()

    fun getNext(): Int = random.nextInt(1000000000)

}