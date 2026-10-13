package com.example.inventario.config

import com.example.inventario.model.Rol
import com.example.inventario.model.Usuario
import com.example.inventario.repository.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Configuration
@EnableScheduling
class SchedulingConfig

@Component
class DatosIniciales(
    private val usuarios: UsuarioRepository,
    private val encoder: PasswordEncoder,
    @Value("\${inventario.admin.usuario}") private val adminUsuario: String,
    @Value("\${inventario.admin.clave}") private val adminClave: String
    ): ApplicationRunner {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun run (args: ApplicationArguments){
        if (!usuarios.existsByNombreUsuario(adminUsuario)) {
            usuarios.save(Usuario(adminUsuario, encoder.encode(adminClave)!!, Rol.ADMIN))
            log.info("Usuario administrador '{} creado. Cambia la clave en produccion.",
                adminUsuario)
        }
    }
}