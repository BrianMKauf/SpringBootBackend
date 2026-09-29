package com.briankauf.springreactapp.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class Greeting(val message: String)

@RestController
@RequestMapping("/api")
class HelloController {
    @GetMapping("/hello")
    fun hello(@RequestParam(defaultValue = "world") name: String): Greeting =
        Greeting("Hello, $name — from Kotlin Spring Boot")
}
