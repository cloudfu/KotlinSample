package com.example.kotlinsample.console.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class Test {
    private val _state = MutableStateFlow<String>("unKnown")
    val state: StateFlow<String> get() = _state

    fun getApi(scope: CoroutineScope) {
        scope.launch {
            val res = getApi()
            _state.value = res
        }
    }

    private suspend fun getApi() = withContext(Dispatchers.IO) {
        delay(2000) // 模拟耗时请求
        "hello, stateFlow"
    }
}

/***
 * 这两个协程会一直运行，因为 collect 是一个挂起函数，它会持续监听 StateFlow 的变化。即使 StateFlow 的值已经更新，协程也不会自动结束，除非你显式地取消它们。
 * 因此，runBlocking 会一直等待这些协程完成，但由于这些协程没有终止条件，runBlocking 也就无法结束，导致应用程序一直挂起，不会退出。
 * 程序没有停下来，因为在 StateFlow 的收集者调用 collect 会挂起当前协程，而且永远不会结束。
 * 解决方案：
 *      1.使用 take 操作符：如果你只关心 StateFlow 的第一次更新，可以使用 take(1) 操作符来限制收集的次数。
 *      2.手动取消协程：你可以在 StateFlow 更新后手动取消协程。
 *      3.使用first 并返回true
 */


/***
 * 关于生命周期关系，最佳实践
 * 场景	                        推荐方式
 * Android ViewModel	        viewModelScope.launch { ... }
 * Android Activity/Fragment	lifecycleScope.launch { ... }
 * 普通 Kotlin 应用（单次任务）	runBlocking { ... } + first { ... } 或手动 cancel()
 * 普通 Kotlin 应用（长期运行）	使用 CoroutineScope + 手动管理生命周期
 */

fun main() = runBlocking<Unit> {
    val test = Test()
    val scope = CoroutineScope(Job() + Dispatchers.IO) // 创建一个新的协程作用域
    test.getApi(scope)

    /***
     * 需要在collect 手动进行cancel取消，不然会一直挂起
     */
    launch(Dispatchers.IO) {
        test.state.collect {
            printWithThreadInfo(it)
            this.cancel()
        }
    }
    launch(Dispatchers.IO) {
        // 如果某个协程失败，不会影响其他协程。
        supervisorScope {
            // 使用 first 只会收集一次数据，然后自动取消协程
//            test.state.first{ value ->
//                printWithThreadInfo(value)
//                true
//            }
            // take(1) 只会收集一次数据，然后自动取消协程
            test.state.take(1).collect {
                printWithThreadInfo(it)
            }
        }
    }
}

fun printWithThreadInfo(message: String = "", coroutineName: String = "NoCoroutine") {
    val threadName = Thread.currentThread().name
    val threadId = Thread.currentThread().id

    println(
        "Message:$message | Thread Name:$threadName | Thread ID:$threadId | Coroutine Name:$coroutineName"
    )
}

