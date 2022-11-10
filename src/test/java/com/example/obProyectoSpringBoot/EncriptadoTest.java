package com.example.obProyectoSpringBoot;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncriptadoTest {
    @Test
    void bcryptTest(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("admin");
        System.out.println(hashedPassword);

        boolean comparar = passwordEncoder.matches("admin",hashedPassword);
        System.out.println(comparar);

    }

}
