package com.example.inventario.Repository

import com.example.inventario.Model.Movimiento
import com.example.inventario.Model.Producto
import com.example.inventario.Model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductoRepository: JpaRepository<Producto, Long>{
    fun existsByCodigo(codigo: String): Boolean

    fun finByNombreContainingIgnoreCase(nombre: String): List<Producto>

    @Query("Select p from Producto p where p.stock <= p.stockMinimo order by p.stock asc")
    fun findBajoStock(): List<Producto>
}

interface UsuarioRepository: JpaRepository<Usuario, Long>{
    fun findByNombreUsuario(nombreUsuario: String): Usuario?
    fun existsByNombreUsuario(nombreUsuario: String): Boolean
}

interface MovimientoRepository: JpaRepository<Movimiento, Long>{
    @Query("select m from Movimiento m join fetch m.producto where m.producto.id = :productoId order by m.fecha desc")
    fun findByProductoIdOrderByFechaDesc(@Param("productoId")productoId: Long): List<Movimiento>
}