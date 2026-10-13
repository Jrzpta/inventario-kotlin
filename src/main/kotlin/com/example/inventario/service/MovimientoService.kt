package com.example.inventario.service

import com.example.inventario.model.Movimiento
import com.example.inventario.model.TipoMovimiento
import com.example.inventario.repository.MovimientoRepository
import com.example.inventario.dto.MovimientoRequest
import com.example.inventario.exception.ReglaNegocioException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MovimientoService(
    private val productoService: ProductoService,
    private val movimientos : MovimientoRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun registrar(productoId: Long, req: MovimientoRequest, usuario: String): Movimiento{
        val producto = productoService.obtener(productoId)

        when(req.tipo){
            TipoMovimiento.ENTRADA -> producto.stock += req.cantidad
            TipoMovimiento.SALIDA -> {
                if (req.cantidad > producto.stock){
                    throw ReglaNegocioException(
                        "Stock insuficiente: hay ${producto.stock} y se piden ${req.cantidad}"
                    )
                }
                producto.stock -= req.cantidad
            }
        }
        if (producto.BajoStock()){
            log.warn("BAJO STOCK: {} ({}) QUEDÓ EN {} (minimo {})",
                producto.nombre, producto.codigo, producto.stock, producto.stockMinimo)
        }
        return movimientos.save(
            Movimiento(
                producto = producto, tipo = req.tipo, cantidad = req.cantidad,
                usuario = usuario, motivo = req.motivo
            )
        )
    }
    @Transactional(readOnly = true)
    fun historial(productoId: Long): List<Movimiento>{
        productoService.obtener(productoId)
        return movimientos.findByProductoIdOrderByFechaDesc(productoId)
    }
}