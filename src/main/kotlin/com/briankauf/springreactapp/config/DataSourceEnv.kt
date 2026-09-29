package com.briankauf.springreactapp.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import java.net.URI

class DataSourceEnv : EnvironmentPostProcessor {
    override fun postProcessEnvironment(env: ConfigurableEnvironment, application: SpringApplication) {
        val raw = env.getProperty("DATABASE_URL") ?: return
        if (raw.startsWith("jdbc:")) return
        if (!raw.startsWith("postgres")) return

        val uri = URI(raw)
        val userInfo = uri.userInfo?.split(":", limit = 2)
        val user = userInfo?.getOrNull(0)
        val password = userInfo?.getOrNull(1)
        val db = uri.path.trimStart('/')
        val query = uri.query?.let { "?$it" } ?: ""
        val jdbc = "jdbc:postgresql://${uri.host}${if (uri.port > 0) ":${uri.port}" else ""}/$db$query"

        val map = mutableMapOf<String, Any>("spring.datasource.url" to jdbc)
        if (!user.isNullOrBlank()) map["spring.datasource.username"] = user
        if (!password.isNullOrBlank()) map["spring.datasource.password"] = password
        env.propertySources.addFirst(MapPropertySource("neon-database-url", map))
    }
}
