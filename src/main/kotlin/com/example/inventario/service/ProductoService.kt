package com.example.inventario.service

import com.example.inventario.model.Producto
import com.example.inventario.repository.MovimientoRepository
import com.example.inventario.repository.ProductoRepository
import com.example.inventario.dto.ProductoRequest
import com.example.inventario.exception.NoEncontradoException
import com.example.inventario.exception.ReglaNegocioException

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service

class ProductoService(
    private val productos: ProductoRepository,
    private val movimientos: MovimientoRepository,
    @Value("\${inventario.stock-minimo-por-defecto:10}") private val stockMinimoPorDefecto: Int
){
    @Transactional(readOnly = true)
    fun listar(buscar: String? = null): List<Producto> =
        if(buscar.isNullOrBlank()) productos.findAll()
        else productos.findByNombreContainingIgnoreCase(buscar.trim())

    @Transactional(readOnly = true)
    fun obtener(id: Long): Producto =
        productos.findById(id).orElseThrow{ NoEncontradoException("PRODUCTO $id NO EXISTE") }

    @Transactional
    fun crear(req: ProductoRequest): Producto {
        val codigo = req.codigo.trim().uppercase()
        if(productos.existsByCodigo(codigo)){
            throw ReglaNegocioException("YA EXISTE UN PRODUCTO CON EL CODIGO $codigo")
        }
        return productos.save(
            Producto(
                codigo = codigo,
                nombre = req.nombre.trim(),
                descripcion = req.descripcion,
                stock = req.stock,
                stockMinimo = req.stockMinimo ?: stockMinimoPorDefecto
            )
        )

    }

    @Transactional
    fun actualizar(id: Long, req: ProductoRequest): Producto {
        val p = obtener(id)
        val codigo = req.codigo.trim().uppercase()
        if (codigo != p.codigo && productos.existsByCodigo(codigo)){
            throw ReglaNegocioException("YA EXISTE UN PRODUCTO CON EL CODIGO $codigo")
        }
        p.codigo = codigo
        p.nombre = req.nombre.trim()
        p.descripcion = req.descripcion
        p.stockMinimo = req.stockMinimo ?: p.stockMinimo
        return p // el stock solo cambia mediante movimientos
    }

    @Transactional
    fun eliminar(id: Long){
        val p = obtener(id)
        if(movimientos.existsByProductoId(id)){
            throw ReglaNegocioException(
                "NO SE PUEDE ELIMINAR EL PRODUCTO ${p.codigo}: TIENE MOVIMIENTOS REGISTRADOS" +
                        "SI YA NO LO VENDES, REGISTRA UNA SALIDA POR TODO SU STOCK"
            )
        }
        productos.delete(p)
    }



}