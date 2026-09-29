package com.briankauf.springreactapp.api

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DbController(private val jdbc: JdbcTemplate) {

    @GetMapping("/db")
    fun db(): Map<String, Any?> {
        val one = jdbc.queryForObject("SELECT 1", Int::class.java)
        return mapOf("database" to "up", "select1" to one)
    }
}
