package com.example.ftp_repo.controller

import com.example.ftp_repo.service.FTPService
import com.example.ftp_repo.service.SFTPService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ftp")
class FTPController(
    private val ftpService: FTPService
) {

    @GetMapping("/upload")
    fun sftpUpload(): Boolean {
        val filePath: String = "C:\\Users\\Tommy\\IdeaProjects\\ftp-repo\\ftp\\upload\\ftp-file.txt"
        val remoteDir: String = "/ftp-file.txt"

        return ftpService.uploadFile(filePath, remoteDir)
    }


    @GetMapping("/download")
    fun sftpDownload(): Boolean {
        val remotePath: String = "/ftp-file.txt"
        val localFilePath: String = "C:\\Users\\Tommy\\IdeaProjects\\ftp-repo\\ftp\\download\\ftp-file.txt"

        return ftpService.downloadFile(remotePath, localFilePath)
    }
}