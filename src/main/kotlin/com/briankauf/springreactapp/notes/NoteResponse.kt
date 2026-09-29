package com.briankauf.springreactapp.notes

data class NoteResponse(
    val id: Long,
    val title: String,
    val content: String,
)

fun Note.toResponse() = NoteResponse(
    id = requireNotNull(id),
    title = title,
    content = content,
)
