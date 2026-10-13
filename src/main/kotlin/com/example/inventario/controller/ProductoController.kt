package com.example.inventario.controller

import com.example.inventario.dto.ProductoRequest
import com.example.inventario.dto.ProductoResponse
import com.example.inventario.dto.toResponse
import com.example.inventario.service.MovimientoService
import com.example.inventario.service.ProductoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/productos")
class ProductoController(
    private val productos: ProductoService,
    private val movimientos: MovimientoService
) {
    @GetMapping
    fun listar(@RequestParam(required = false)buscar : String?):List<ProductoResponse> =
        productos.listar(buscar).map { it.toResponse() }

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Long): ProductoResponse = productos.obtener(id).toResponse()

    @PostMapping
    fun crear(@Valid @RequestBody req: ProductoRequest): ResponseEntity<ProductoResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(productos.crear(req).toResponse())

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @Valid @RequestBody req: ProductoRequest):
            ProductoResponse =
        productos.actualizar(id, req).toResponse()

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void>{
        productos.eliminar(id)
        return ResponseEntity.noContent().build()
    }


}