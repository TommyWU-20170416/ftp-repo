package com.example.ftp_repo

data class SFTPProperty(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val privateKeyPath: String? = null,
    val dir: Dir
){
    data class Dir(
        val upload: String,
        val download: String?
    )
}