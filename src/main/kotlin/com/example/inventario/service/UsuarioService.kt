package com.example.inventario.service

import com.example.inventario.Model.Usuario
import com.example.inventario.Repository.UsuarioRepository
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
        if (usuarios.existsByNombreUsuario(req.nombreUsuario)){
            throw ReglaNegocioException("El usuario ${req.nombreUsuario} ya existe!")
        }
        return usuarios.save(
            Usuario(
                nombreUsuario = req.nombreUsuario.trim(),
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