package com.briankauf.springreactapp.notes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NoteControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var notes: NoteRepository

    private val mapper = jacksonObjectMapper()

    @BeforeEach
    fun clear() {
        notes.deleteAll()
    }

    @Test
    fun listStartsEmpty() {
        mockMvc.get("/api/notes").andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(0) }
        }
    }

    @Test
    fun createThenRead() {
        val body = mapper.writeValueAsString(mapOf("title" to "First", "content" to "Hello"))
        val created = mockMvc.post("/api/notes") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }.andExpect {
            status { isCreated() }
            jsonPath("$.title") { value("First") }
            jsonPath("$.content") { value("Hello") }
            jsonPath("$.id") { exists() }
        }.andReturn()

        val id = mapper.readTree(created.response.contentAsString).get("id").asLong()

        mockMvc.get("/api/notes/$id").andExpect {
            status { isOk() }
            jsonPath("$.title") { value("First") }
        }
    }

    @Test
    fun createRejectsBlankTitle() {
        mockMvc.post("/api/notes") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(mapOf("title" to "", "content" to "x"))
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun updateExisting() {
        val saved = notes.save(Note(title = "Old", content = "A"))
        mockMvc.put("/api/notes/${saved.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(mapOf("title" to "New", "content" to "B"))
        }.andExpect {
            status { isOk() }
            jsonPath("$.title") { value("New") }
            jsonPath("$.content") { value("B") }
        }
    }

    @Test
    fun updateMissingIs404() {
        mockMvc.put("/api/notes/999") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(mapOf("title" to "New", "content" to "B"))
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun deleteExisting() {
        val saved = notes.save(Note(title = "Gone", content = ""))
        mockMvc.delete("/api/notes/${saved.id}").andExpect {
            status { isNoContent() }
        }
        mockMvc.get("/api/notes/${saved.id}").andExpect {
            status { isNotFound() }
        }
    }
}
