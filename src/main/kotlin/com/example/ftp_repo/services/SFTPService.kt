package com.example.ftp_repo.services

import com.example.ftp_repo.utils.SFTPUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class SFTPService(
    @Qualifier("taipeiSFTPUtil")val taipeiSFTPUtil: SFTPUtils
) {

    fun uploadFile(
        filePath: String,
        remoteDir: String
    ): Boolean {
        return taipeiSFTPUtil.upload(filePath, remoteDir)
    }

    fun downloadFile(
        remotePath: String,
        localFilePath: String
    ): Boolean {
        return taipeiSFTPUtil.download(remotePath, localFilePath)
    }
}