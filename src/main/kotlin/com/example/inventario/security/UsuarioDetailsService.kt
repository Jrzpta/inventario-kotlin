package com.example.inventario.security

import com.example.inventario.repository.UsuarioRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UsuarioDetailsService(private val usuarios: UsuarioRepository): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val u = usuarios.findByNombreUsuario(username)
            ?: throw UsernameNotFoundException("USUARIO NO ENCONTRADO")
        return User.withUsername(u.nombreUsuario)
            .password(u.claveHash)
            .roles(u.rol.name)
            .disabled(!u.activo)
            .build()

    }
}