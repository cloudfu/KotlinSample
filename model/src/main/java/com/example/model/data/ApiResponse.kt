package com.example.model.data

sealed class ApiResponse(
) {

    class Success<T>(val data: T): ApiResponse()
    class Failure(val state: ErrorCodes): ApiResponse()
    data object Loading : ApiResponse()
}

// TODO:public sealed interface ApiResponse<out T> {

enum class ErrorCodes (val code: Int = 0, var msg: String = ""){
    SUCCEED(200, "成功过"),
    SERVER_FAILED(1000, "服务器处理问题"),
    NO_NETWORK(300, "无网络"),
    TIMEOUT(400, "请求超时"),
    UNKNOWN_ERROR(2000, "未知错误");
}
