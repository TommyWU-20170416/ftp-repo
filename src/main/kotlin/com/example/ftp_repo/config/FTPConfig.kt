package com.example.ftp_repo.config

import com.example.ftp_repo.config.properties.FTPProperty
import com.example.ftp_repo.config.properties.SFTPProperty
import com.example.ftp_repo.utils.FTPUtils
import com.example.ftp_repo.utils.SFTPUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "ftp")
@Configuration
class FTPConfig {

    lateinit var esunFTP: FTPProperty
    @Bean
    fun esunFTPUtil(): FTPUtils {
        return FTPUtils(esunFTP)
    }

}