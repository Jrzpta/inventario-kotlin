package com.example.inventario.service

import com.example.inventario.Repository.ProductoRepository
import com.example.inventario.dto.AlertaBajoStock
import com.example.inventario.dto.toResponse
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlertaService(private val productos: ProductoRepository) {
    private val log = LoggerFactory.getLogger(javaClass)
    @Transactional(readOnly = true)
    fun bajoStock(): AlertaBajoStock{
        val lista = productos.findBajoStock().map { it.toResponse() }
        return AlertaBajoStock(total = lista.size, productos = lista)
    }

    @Scheduled(cron = "0 0 8 * * *")
    fun revisionDiaria(){
        val alerta = bajoStock()
        if (alerta.total >0){
            log.warn("HAY {} PRODUCTOS CON BAJO STOCK: {}", alerta.total,
                alerta.productos.joinToString { "${it.codigo}(${it.stock})" })
        }
    }

}