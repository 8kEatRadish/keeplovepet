package com.sniperking.libnetwork

data class ApiResponse<T>(
    var success: Boolean?,
    var status: Int?,
    var message: String?,
    var body: T?
)