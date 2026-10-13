package com.example.inventario.service

import com.example.inventario.model.Usuario
import com.example.inventario.repository.UsuarioRepository
import com.example.inventario.dto.UsuarioRequest
import com.example.inventario.exception.NoEncontradoException
import com.example.inventario.exception.ReglaNegocioException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioService(
    private val usuarios : UsuarioRepository,
    private val encoder: PasswordEncoder
) {
    @Transactional(readOnly = true)
    fun listar(): List<Usuario> = usuarios.findAll()

    @Transactional
    fun crear(req: UsuarioRequest): Usuario{
        val nombre = req.nombreUsuario.trim()
        if (usuarios.existsByNombreUsuario(nombre)){
            throw ReglaNegocioException("El usuario ${nombre} ya existe!")
        }
        return usuarios.save(
            Usuario(
                nombreUsuario = nombre,
                claveHash = encoder.encode(req.clave)!!,
                rol = req.rol
            )
        )
    }
    @Transactional
    fun cambiarActivo(id: Long, activo: Boolean): Usuario{
        val u = usuarios.findById(id).orElseThrow{ NoEncontradoException("Usuario $id no existe!") }
        u.activo = activo
        return u
    }



}