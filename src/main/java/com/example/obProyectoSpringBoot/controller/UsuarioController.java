package com.example.obProyectoSpringBoot.controller;

import com.example.obProyectoSpringBoot.dao.UsuarioDao;
import com.example.obProyectoSpringBoot.jwt.JwtTokenUtil;
import com.example.obProyectoSpringBoot.playload.JwtResponse;
import com.example.obProyectoSpringBoot.playload.LoginEntity;
import com.example.obProyectoSpringBoot.playload.MessageResponse;
import com.example.obProyectoSpringBoot.playload.Usuario;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UsuarioController {

    private final UsuarioDao usuarioDao;
    @Autowired
    private final AuthenticationManager authManager;
    private final PasswordEncoder encoder;
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    public UsuarioController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authManager, UsuarioDao usuarioDao, PasswordEncoder encoder) {
        this.usuarioDao = usuarioDao;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    private final Logger Log = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("api/registrar")
    @ApiOperation(value = "Registro Usuarios",
            notes = "Registra Usuario y verifica que Email y Nombre no existan en base de datos")
    public ResponseEntity<MessageResponse> postUsuario(@RequestBody Usuario usuario) {
        if (usuarioDao.existsByNombre(usuario.getNombre())){
            return ResponseEntity.badRequest().body(new  MessageResponse("Error: Nombre de Usuario ya existe"));
        }
        if (usuarioDao.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email ya existe"));
        }
        if (usuario.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Nombre es un camplo obligatorio"));
        }
        if (usuario.getApellido().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Apellido es un campo obligatorio"));
        }
        if (usuario.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email es un campo obligatorio"));
        }
        if (usuario.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Password no puede estar vacio"));
        }
        if (usuario.getRepeat().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Este campo no puede estar vacio"));
        }

        Usuario usuario1 = new Usuario(usuario.getNombre()
                , usuario.getApellido(), usuario.getEmail(), encoder.encode(usuario.getPassword()),
                encoder.encode(usuario.getRepeat()));

        usuarioDao.save(usuario1);
        System.out.println(usuario);
        return ResponseEntity.ok(new MessageResponse("Usuario creado con exito"));
    }


    @GetMapping("api/usuarios")
    @ApiOperation(value = "Metodo Get",
            notes = "Trae las lista de usuarios que se encuentran en base de datos ")
    public List<Usuario> getUsuarios() {
        System.out.println(usuarioDao.findAll());
        return usuarioDao.findAll();
    }

    @DeleteMapping("api/usuarios/{id}")
    @ApiOperation(value = "Metodo delete Usuarios",
            notes = "Se realiza Delete segun su Id")
    public ResponseEntity<Usuario> delete(@PathVariable Long id) {
        if (!usuarioDao.existsById(id)) {
            Log.warn("Trying to delete a non existent");
            return ResponseEntity.notFound().build();
        }
        usuarioDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("api/login")
    @ApiOperation(value = "Medoto Login",
            notes = "Se Verifica el Email y la Contraseña para enviar el token de JWT y dar verificar acceso de Usuario")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginEntity loginEntity) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginEntity.getEmail(), loginEntity.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println(jwt);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }


}