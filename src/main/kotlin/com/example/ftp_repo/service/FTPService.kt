package com.example.ftp_repo.service

import com.example.ftp_repo.utils.FTPUtils
import com.example.ftp_repo.utils.SFTPUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class FTPService(
    @Qualifier("esunFTPUtil")val esunFTPUtil: FTPUtils
) {

    fun uploadFile(
        filePath: String,
        remoteDir: String
    ): Boolean {
        return esunFTPUtil.upload(filePath, remoteDir)
    }

    fun downloadFile(
        remotePath: String,
        localFilePath: String
    ): Boolean {
        return esunFTPUtil.download(remotePath, localFilePath)
    }
}