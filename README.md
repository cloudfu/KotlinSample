## 1. 应用无法自动关闭 TestStatFlow.kt
这两个协程会一直运行，因为 collect 是一个挂起函数，它会持续监听 StateFlow 的变化。即使 StateFlow 的值已经更新，协程也不会自动结束，除非你显式地取消它们。
因此，runBlocking 会一直等待这些协程完成，但由于这些协程没有终止条件，runBlocking 也就无法结束，导致应用程序一直挂起，不会退出。
解决方案：
     1.使用 take 操作符：如果你只关心 StateFlow 的第一次更新，可以使用 take(1) 操作符来限制收集的次数。
     2.手动取消协程：你可以在 StateFlow 更新后手动取消协程。
     3.使用first 并返回true


关于生命周期关系，最佳实践
场景	                         推荐方式
Android ViewModel             viewModelScope.launch { ... }
Android Activity/Fragment     lifecycleScope.launch { ... }
普通 Kotlin 应用（单次任务）    runBlocking { ... } + first { ... } 或手动 cancel()
普通 Kotlin 应用（长期运行）    使用 CoroutineScope + 手动管理生命周期

ghp_1NWiCiwOaDaecFAxFM9wtQ08xbSn9Be4OrMlJ

1. Hilt 依赖注入 retrofit/okhttp/repository
2. repository local/remote 数据merge
3. room hilt
4. network 监听
5. api host动态变更
6. seal class out T
7. viewMode lambda success/failed接口回调