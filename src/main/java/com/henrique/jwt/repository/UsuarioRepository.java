package com.henrique.jwt.repository;

import com.henrique.jwt.entity.Usuario;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by henrique on 19/02/17.
 */
@Service
public class UsuarioRepository {


    private static List<Usuario> usuarios;

    static {

        Usuario u1 = new Usuario();
        u1.setEmail("teste1@gmail.com");
        u1.setIdade(23);
        u1.setNome("Henrique Goncalves Barreto");
        u1.setSenha("123456");
        u1.setRoles(Arrays.asList("ROLE_USESR", "ROLE_ADMIN"));

        Usuario u2 = new Usuario();
        u2.setEmail("teste2@gmail.com");
        u2.setIdade(32);
        u2.setNome("Anderson the spider silva");
        u2.setSenha("123456");
        u2.setRoles(Arrays.asList("ROLE_USESR", "ROLE_ADMIN"));

        Usuario u3 = new Usuario();
        u3.setEmail("teste3@gmail.com");
        u3.setIdade(44);
        u3.setNome("Vitor Belfort");
        u3.setSenha("123456");
        u3.setRoles(Arrays.asList("ROLE_USESR", "ROLE_ADMIN"));

        usuarios = new ArrayList<Usuario>();
        usuarios.addAll(Arrays.asList(u1, u2, u3));

    }


    public Usuario buscarPorUsuario(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(username))
                return usuario;
        }
        return null;
    }


    public List<Usuario> listar() {
        return usuarios;
    }

}
