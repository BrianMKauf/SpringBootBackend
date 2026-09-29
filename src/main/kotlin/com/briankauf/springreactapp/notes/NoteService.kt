package com.briankauf.springreactapp.notes

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoteService(private val notes: NoteRepository) {

    fun list(): List<NoteResponse> = notes.findAll().map { it.toResponse() }

    fun get(id: Long): NoteResponse =
        notes.findById(id).orElseThrow { NoteNotFoundException(id) }.toResponse()

    @Transactional
    fun create(request: NoteRequest): NoteResponse =
        notes.save(Note(title = request.title, content = request.content)).toResponse()

    @Transactional
    fun update(id: Long, request: NoteRequest): NoteResponse {
        val note = notes.findById(id).orElseThrow { NoteNotFoundException(id) }
        note.title = request.title
        note.content = request.content
        return notes.save(note).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        if (!notes.existsById(id)) throw NoteNotFoundException(id)
        notes.deleteById(id)
    }
}
