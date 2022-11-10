package com.example.obProyectoSpringBoot.service;

import com.example.obProyectoSpringBoot.dao.UsuarioDao;
import com.example.obProyectoSpringBoot.playload.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el Email: " + email));
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),usuario.getPassword(),new ArrayList<>());
    }
}
