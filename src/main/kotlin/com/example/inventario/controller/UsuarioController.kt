package com.example.inventario.controller

import com.example.inventario.dto.AlertaBajoStock
import com.example.inventario.dto.UsuarioRequest
import com.example.inventario.dto.UsuarioResponse
import com.example.inventario.dto.toResponse
import com.example.inventario.service.AlertaService
import com.example.inventario.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(private val usuarios : UsuarioService) {

    @GetMapping
    fun listar(): List<UsuarioResponse> = usuarios.listar().map{it.toResponse()}

    @PostMapping
    fun crear(@Valid @RequestBody req: UsuarioRequest): ResponseEntity<UsuarioResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(usuarios.crear(req).toResponse())

    @PatchMapping("/{id}/api/activo")
    fun cambiarActivo(@PathVariable id: Long, @RequestParam valor: Boolean): UsuarioResponse =
        usuarios.cambiarActivo(id,valor).toResponse()
}

@RestController
@RequestMapping("/api/alertas")
class AlertaController(private val alertas: AlertaService) {
    @GetMapping("/bajo-stock")
    fun bajoStock(): AlertaBajoStock = alertas.bajoStock()
}