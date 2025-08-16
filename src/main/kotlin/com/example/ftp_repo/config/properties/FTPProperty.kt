package com.example.ftp_repo.config.properties

data class FTPProperty(
    val host: String,
    val port: Int,
    val username: String,
    val password: String
) {}