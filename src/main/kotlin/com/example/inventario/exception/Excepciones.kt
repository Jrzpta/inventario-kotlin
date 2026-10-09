package com.example.inventario.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

class NoEncontradoException(message: String) : RuntimeException(message)
class ReglaNegocioException(message: String) : RuntimeException(message)

data class ErrorResponse(val estado: Int, val mensaje: String, val detalles: List<String> = emptyList())


@RestControllerAdvice
class ManejadorGlobalErrores {
    @ExceptionHandler(NoEncontradoException::class)
    fun noEncontrado(e: NoEncontradoException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(404, e.message ?: "No Encontrado"))

    @ExceptionHandler(ReglaNegocioException::class)
    fun reglaNegocio(e: ReglaNegocioException)=
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorResponse(409, e.message ?: "Regla de negocio incumplida"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validacion(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse>{
        val detalles = e.bindingResult.fieldErrors.map{"${it.field}:${it.defaultMessage}"}
        return ResponseEntity.badRequest().body(ErrorResponse(400,"Datos invalidos", detalles))
    }

}