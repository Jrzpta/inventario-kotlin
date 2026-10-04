package com.example.inventario.Model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "productos")
class Producto(
    @Column(nullable = false, unique = true, length = 40)
    var codigo: String,

    @Column(nullable = false, length = 120)
    var nombre: String,

    @Column(nullable = false, length = 255)
    var descripcion: String? = null,

    @Column(nullable = false)
    var Stock: Int = 0,

    @Column(name = "stock_minimo",nullable = false)
    var stockMinimo: Int = 5,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    ) {
    fun BajoStock():
            Boolean = Stock <= stockMinimo
}