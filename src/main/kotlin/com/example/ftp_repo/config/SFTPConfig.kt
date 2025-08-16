package com.example.ftp_repo.config

import com.example.ftp_repo.SFTPProperty
import com.example.ftp_repo.utils.SFTPUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "ftp")
@Configuration
class SFTPConfig {

    lateinit var taipeiSFTP: SFTPProperty
    lateinit var funbankSFTP: SFTPProperty

    @Bean
    fun taipeiSFTPUtil(): SFTPUtils {
        return SFTPUtils(taipeiSFTP)
    }

//    @Bean
//    fun funbankSFTPUtil(): SFTPUtils {
//        return SFTPUtils(funbankSFTP)
//    }
}