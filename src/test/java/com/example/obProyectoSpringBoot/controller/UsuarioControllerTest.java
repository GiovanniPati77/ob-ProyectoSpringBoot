package com.example.obProyectoSpringBoot.controller;

import com.example.obProyectoSpringBoot.playload.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.config.web.servlet.SecurityMarker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;




    @BeforeEach
    void setUp(){
        PasswordEncoder passwordEncoder;
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String json = """
                {
                    "nombre":"Massi",
                    "apellido":"Posada",
                    "email": "Massi@correo.com",
                    "password": "admin",
                    "repeat":"admin"
                }
                
                """;
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Usuario> response = testRestTemplate.exchange("/api/registrar",HttpMethod.POST,request,Usuario.class);

        Usuario result = response.getBody();
        assertEquals("Massi",result.getNombre());
        assertEquals("admin",result.getPassword());
    }

    @Test
    void getUsuarios() {
        ResponseEntity<Usuario[]> response = testRestTemplate.getForEntity("/api/usuarios",Usuario[].class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(200,response.getStatusCodeValue());

        List<Usuario> usuarios = Arrays.asList(response.getBody());

        System.out.println(usuarios.size());


    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        ResponseEntity<Usuario> response = testRestTemplate.getForEntity("/api/usuarios/1",Usuario.class);

    }


}