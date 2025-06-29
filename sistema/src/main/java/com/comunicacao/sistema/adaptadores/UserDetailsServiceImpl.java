package com.comunicacao.sistema.adaptadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.comunicacao.sistema.entidades.Credencial;
import com.comunicacao.sistema.entidades.CredencialUsuarioSenha;
import com.comunicacao.sistema.entidades.Usuario;
import com.comunicacao.sistema.repositorios.RepositorioUsuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RepositorioUsuario repositorio;

    private Usuario obterPorNome(String nomeUsuario) {
        List<Usuario> usuarios = repositorio.findAll();
        for (Usuario usuario : usuarios) {
            for (Credencial credencial : usuario.getCredenciais()) {
                if (credencial instanceof CredencialUsuarioSenha) {
                    if (((CredencialUsuarioSenha) credencial).getNomeUsuario().equals(nomeUsuario)) {
                        return usuario;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.obterPorNome(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(usuario);
    }
}
