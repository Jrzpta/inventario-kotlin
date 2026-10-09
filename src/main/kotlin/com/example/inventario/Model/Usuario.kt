package com.example.inventario.Model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "usuarios")
class Usuario(
    @Column(nullable = false, unique = true, length = 40)
    var nombreUsuario: String,

    @Column(nullable = false, length = 100)
    var claveHash: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var rol: Rol,


    @Column(nullable = false)
    var activo: Boolean = true,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
}