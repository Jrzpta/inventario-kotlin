package com.example.inventario.dto

import com.example.inventario.model.Movimiento
import com.example.inventario.model.Producto
import com.example.inventario.model.Rol
import com.example.inventario.model.TipoMovimiento
import com.example.inventario.model.Usuario
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime


data class ProductoRequest(
    @field:NotBlank @field:Size(max = 40) val codigo: String,
    @field:NotBlank @field:Size(max = 120) val nombre: String,
    @field:Size(max = 255) val descripcion: String? = null,
    @field:Min(0) val stock: Int = 0,
    @field:Min(0) val stockMinimo: Int? = null
    )

data class ProductoResponse(
    val id: Long,
    val codigo: String,
    val nombre: String,
    val descripcion: String?,
    val stock: Int,
    val stockMinimo: Int,
    val bajoStock: Boolean
)

fun Producto.toResponse() = ProductoResponse(
    id = id!!, codigo = codigo, nombre = nombre, descripcion = descripcion,
    stock = stock, stockMinimo = stockMinimo, bajoStock = BajoStock()
)

data class MovimientoRequest(
    val tipo: TipoMovimiento,
    @field:Min(1) val cantidad: Int,
    @field:Size(max = 255) val motivo: String? = null
)

data class MovimientoResponse(
    val id: Long,
    val productoId: Long,
    val tipo: TipoMovimiento,
    val cantidad: Int,
    val usuario: String,
    val motivo: String?,
    val fecha: LocalDateTime
    )


fun Movimiento.toResponse() =  MovimientoResponse(
    id = id!!, productoId = producto.id!!, tipo = tipo, cantidad = cantidad,
    usuario = usuario, motivo = motivo, fecha = fecha
)

data class UsuarioRequest(
    @field:NotBlank @field:Size(min = 3, max = 40) val nombreUsuario: String,
    @field:NotBlank @field:Size(min= 6, max = 72) val clave: String,
    val rol: Rol
)

data class UsuarioResponse(val id: Long, val nombreUsuario: String, val rol: Rol, val activo: Boolean)

fun Usuario.toResponse() =  UsuarioResponse(id!!, nombreUsuario, rol, activo)

data class AlertaBajoStock(val total: Int, val productos: List<ProductoResponse>)








