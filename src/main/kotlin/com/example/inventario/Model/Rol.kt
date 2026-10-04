package com.example.inventario.Model

/**
 * Roles de acceso:
 *  ADMIN     -> todo, incluido eliminar productos y gestionar usuarios
 *  BODEGUERO -> agregar/editar productos y registrar entradas/salidas
 *  CONSULTA  -> solo lectura
 */


enum class Rol {
    ADMIN,
    BODEGUERO,
    CONSULTA
}