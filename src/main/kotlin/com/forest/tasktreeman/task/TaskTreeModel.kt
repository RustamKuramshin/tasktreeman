package com.forest.tasktreeman.task


/* Модель Задачи
* id - идентификатор униального экземпляра задания,
* initVal - стркоа начального значения,
* result - результат от воркера в виде "<начальное-значение> + <результат>"*/
data class Task(
        var id: Int,
        var treeId: String,
        var initVal: String,
        var leftChild: Task? = null,
        var rightChild: Task? = null
)

class TaskTree (val treeId: String){

    var root: Task? = null

    init {

        insert(idGen.getNext(), treeId)

    }

    fun find(key: Int)
            : Task? {
        var current = root
        while (current!!.id != key) {
            if (key < current.id)

                current = current.leftChild
            else

                current = current.rightChild
            if (current == null)

                return null
        }
        return current
    }

    fun insert(id: Int, initVal: String) {
        val newNode = Task(id = id, initVal = initVal, treeId = treeId)
        if (root == null)

            root = newNode
        else {
            var current = root
            var parent: Task
            while (true) {
                parent = current!!
                if (id < current!!.id) {
                    current = current.leftChild
                    if (current == null) {
                        parent.leftChild = newNode
                        return
                    }
                } else {
                    current = current.rightChild
                    if (current == null) {
                        parent.rightChild = newNode
                        return
                    }
                }
            }
        }
    }

    fun delete(key: Int)
            : Boolean {
        var current = root
        var parent = root
        var isLeftChild = true

        while (current!!.id != key) {
            parent = current
            if (key < current.id) {
                isLeftChild = true
                current = current.leftChild
            } else {
                isLeftChild = false
                current = current.rightChild
            }
            if (current == null)

                return false
        }

        if (current.leftChild == null && current.rightChild == null) {
            if (current === root)

                root = null
            else if (isLeftChild)
                parent!!.leftChild = null
            else

                parent!!.rightChild = null
        } else if (current.rightChild == null)
            if (current === root)
                root = current.leftChild
            else if (isLeftChild)
                parent!!.leftChild = current.leftChild
            else
                parent!!.rightChild = current.leftChild
        else if (current.leftChild == null)
            if (current === root)
                root = current.rightChild
            else if (isLeftChild)
                parent!!.leftChild = current.rightChild
            else
                parent!!.rightChild = current.rightChild
        else {
            val successor = getSuccessor(current)

            if (current === root)
                root = successor
            else if (isLeftChild)
                parent!!.leftChild = successor
            else
                parent!!.rightChild = successor

            successor.leftChild = current.leftChild
        }

        return true
    }

    private fun getSuccessor(delTask: Task): Task {
        var successorParent = delTask
        var successor = delTask
        var current: Task? = delTask.rightChild
        while (current != null) {
            successorParent = successor
            successor = current
            current = current.leftChild
        }

        if (successor !== delTask.rightChild) {
            successorParent.leftChild = successor.rightChild
            successor.rightChild = delTask.rightChild
        }
        return successor
    }

    fun traverse(traverseType: Int) {
        when (traverseType) {
            1 -> {
                print("\nPreorder traversal: ")
                preOrder(root)
            }
            2 -> {
                print("\nInorder traversal:  ")
                inOrder(root)
            }
            3 -> {
                print("\nPostorder traversal: ")
                postOrder(root)
            }
        }
        println()
    }

    private fun preOrder(localRoot: Task?) {
        if (localRoot != null) {
            print(localRoot.id.toString() + " ")
            preOrder(localRoot.leftChild)
            preOrder(localRoot.rightChild)
        }
    }

    private fun inOrder(localRoot: Task?) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild)
            print(localRoot.id.toString() + " ")
            inOrder(localRoot.rightChild)
        }
    }

    private fun postOrder(localRoot: Task?) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild)
            postOrder(localRoot.rightChild)
            print(localRoot.id.toString() + " ")
        }
    }
}

class TaskQueue {

    var items: MutableList<Task?> = mutableListOf()

    fun isEmpty():Boolean = items.isEmpty()

    fun size():Int = items.count()

    override  fun toString() = items.toString()

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

    fun peek():Any?{
        return items[0]
    }
}

object idGen {

    private var startNum: Int = 1000

    fun getNext() = ++startNum

}