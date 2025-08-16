package com.example.ftp_repo

import com.example.ftp_repo.services.SFTPService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SFTPController(
    private val sftpService: SFTPService
) {

    @GetMapping("/sftp/upload")
    fun sftpUpload(): Boolean {
        val filePath: String = "C:\\Users\\Tommy\\IdeaProjects\\ftp-repo\\ftp\\upload\\sftp-file.txt"
        val remoteDir: String = "/upload/sftp-file.txt"

        return sftpService.uploadFile(filePath, remoteDir)
    }


    @GetMapping("/sftp/download")
    fun sftpDownload(): Boolean {
        val remotePath: String = "/upload/sftp-file.txt"
        val localFilePath: String = "C:\\Users\\Tommy\\IdeaProjects\\ftp-repo\\ftp\\download\\sftp-file.txt"

        return sftpService.downloadFile(remotePath, localFilePath)
    }
}