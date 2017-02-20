package com.henrique.jwt.controller;

import com.henrique.jwt.entity.Usuario;
import com.henrique.jwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by henrique on 19/02/17.
 */
@RestController
public class UsuariosController {


    @Autowired
    private UsuarioRepository repository;

    @RequestMapping(value = "/api/usuarios", method = RequestMethod.GET)
    public List<Usuario> get() {
        return  repository.listar();
    }


}
