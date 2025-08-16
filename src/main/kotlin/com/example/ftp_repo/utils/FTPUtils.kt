package com.example.ftp_repo.utils

import com.example.ftp_repo.config.properties.FTPProperty
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.FileInputStream
import java.io.FileOutputStream

class FTPUtils(
    private val ftpProperty: FTPProperty
) {
    private val ftpClient = FTPClient()

    init {
        try {
            ftpClient.connect(ftpProperty.host, ftpProperty.port)
            ftpClient.login(ftpProperty.username, ftpProperty.password)
            ftpClient.enterLocalPassiveMode()
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
            // 驗證連線是否成功
            ftpClient.listFiles("/")
        } catch (e: Exception) {
            throw RuntimeException("FTP 連線失敗: ${e.message}", e)
        }
    }

    fun upload(localFilePath: String, remoteFilePath: String): Boolean {
        FileInputStream(localFilePath).use { input ->
            return ftpClient.storeFile(remoteFilePath, input)
        }
    }

    fun download(remoteFilePath: String, localFilePath: String): Boolean {
        FileOutputStream(localFilePath).use { output ->
            return ftpClient.retrieveFile(remoteFilePath, output)
        }
    }

    fun disconnect() {
        if (ftpClient.isConnected) {
            ftpClient.logout()
            ftpClient.disconnect()
        }
    }
}