package com.example.model.data

sealed class ResultPackage<T>(
    val code: Int = 0,
    val msg: String = "",
    val data: T? = null
) {

    class Success<T>(data: T): ResultPackage<T>(ResultState.SUCCEED.code, ResultState.SUCCEED.msg, data)
    class Failure<T>(code: Int, msg: String): ResultPackage<T>(code, msg, null)
    class Loading<T>(): ResultPackage<T>(ResultState.LOADING.code, ResultState.LOADING.msg,null)
    class Empty<T>(): ResultPackage<T>(ResultState.SERVER_FAILED.code, ResultState.SERVER_FAILED.msg,null)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success:code=$code, msg=$msg"
            is Failure -> "Success:code=$code, msg=$msg"
            is Loading<T> -> "Loading"
            is Empty -> TODO()
        }
    }
}

enum class ResultState (val code: Int = 0, var msg: String = ""){
    LOADING(-1, "请求中"),
    SUCCEED(200, "获取成功"),
    SERVER_FAILED(1000, "服务器处理问题"),
    NO_NETWORK(300, "无网络"),
    TIMEOUT(400, "请求超时"),
    UNKNOWN_ERROR(2000, "未知错误");
}
