package com.briankauf.springreactapp.notes

import jakarta.validation.constraints.NotBlank

data class NoteRequest(
    @field:NotBlank val title: String,
    val content: String = "",
)
