package com.briankauf.springreactapp.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun healthReturnsOk() {
        mockMvc.get("/api/health").andExpect {
            status { isOk() }
            jsonPath("$.status") { value("ok") }
        }
    }

    @Test
    fun helloUsesNameQuery() {
        mockMvc.get("/api/hello") { param("name", "Brian") }.andExpect {
            status { isOk() }
            jsonPath("$.message") { value("Hello, Brian — from Kotlin Spring Boot") }
        }
    }
}
