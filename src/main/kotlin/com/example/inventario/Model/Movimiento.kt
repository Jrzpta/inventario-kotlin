package com.example.inventario.Model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

enum class TipoMovimiento {
    ENTRADA,
    SALIDA
}

@Entity
@Table(name = "movimientos")
class Movimiento(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id")
    var producto: Producto,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var tipo: TipoMovimiento,

    @Column(nullable = false)
    var cantidad: Int,

    @Column(nullable = false, length = 40)
    var usuario: String?,

    @Column(length = 255)
    var motivo: String? = null,

    @Column(nullable = false)
    var fecha: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)