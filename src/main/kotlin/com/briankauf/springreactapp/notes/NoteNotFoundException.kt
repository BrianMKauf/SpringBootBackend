package com.briankauf.springreactapp.notes

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NoteNotFoundException(id: Long) : RuntimeException("Note $id not found")
