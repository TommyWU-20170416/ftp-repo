package com.example.ftp_repo.utils

import com.example.ftp_repo.config.properties.SFTPProperty
import com.jcraft.jsch.JSch
import org.apache.commons.vfs2.FileSystemOptions
import org.apache.commons.vfs2.Selectors
import org.apache.commons.vfs2.VFS
import org.apache.commons.vfs2.provider.sftp.IdentityInfo
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder
import java.io.File
import java.time.Duration

class SFTPUtils(
    private val sftpProperty: SFTPProperty
) {
    private val manager = VFS.getManager()
    private val opts = FileSystemOptions()

    init {
        // 如果有設定 金鑰 需要添加此段，讓 VFS 使用指定的私鑰驗證，不然預設會去抓 ~/.ssh/id_rsa
        var identity = IdentityInfo(File(System.getProperty("user.home") + "/.ssh/id_rsa_jsch"))

        val builder = SftpFileSystemConfigBuilder.getInstance()
        builder.setStrictHostKeyChecking(opts, "no")
        builder.setUserDirIsRoot(opts, false)
        builder.setSessionTimeout(opts, Duration.of(10000.toLong(), java.time.temporal.ChronoUnit.MILLIS))
        builder.setIdentityProvider(opts, identity)

        sftpProperty.privateKeyPath?.let { keyPath ->
            val jsch = JSch()
            val session = jsch.getSession(sftpProperty.username, sftpProperty.host, sftpProperty.port)
            session.setPassword(sftpProperty.password)
            session.setConfig("StrictHostKeyChecking", "no")
            session.connect()
        }
    }

    private fun buildSftpUri(path: String): String {
        return if (sftpProperty.privateKeyPath.isNullOrBlank()) {
            // 帳號密碼登入
            "sftp://${sftpProperty.username}:${sftpProperty.password}@${sftpProperty.host}:${sftpProperty.port}/$path"
        } else {
            // SSH 金鑰登入（不帶 password）
            "sftp://${sftpProperty.username}@${sftpProperty.host}:${sftpProperty.port}/$path"
        }
    }

    fun upload(localFilePath: String, remotePath: String): Boolean {
        val localFile = manager.resolveFile(localFilePath)
        val remoteFile = manager.resolveFile(buildSftpUri(remotePath), opts)
        remoteFile.copyFrom(localFile, Selectors.SELECT_SELF)
        remoteFile.close()
        localFile.close()

        return true
    }

    fun download(remotePath: String, localFilePath: String): Boolean {
        val remoteFile = manager.resolveFile(buildSftpUri(remotePath), opts)
        val localFile = manager.resolveFile(localFilePath)
        localFile.copyFrom(remoteFile, Selectors.SELECT_SELF)
        remoteFile.close()
        localFile.close()

        return true
    }
}