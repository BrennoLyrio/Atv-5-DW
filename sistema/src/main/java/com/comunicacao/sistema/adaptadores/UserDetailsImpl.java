package com.comunicacao.sistema.adaptadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.comunicacao.sistema.entidades.Credencial;
import com.comunicacao.sistema.entidades.CredencialUsuarioSenha;
import com.comunicacao.sistema.entidades.Usuario;
import com.comunicacao.sistema.enumeracoes.PerfilUsuario;

@SuppressWarnings("serial")
public class UserDetailsImpl implements UserDetails {
    private Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> autoridades = new ArrayList<>();
        for (PerfilUsuario perfil : usuario.getPerfis()) {
            autoridades.add(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
        }
        return autoridades;
    }

    @Override
    public String getPassword() {
        for (Credencial credencial : usuario.getCredenciais()) {
            if (credencial instanceof CredencialUsuarioSenha) {
                return ((CredencialUsuarioSenha) credencial).getSenha();
            }
        }
        return null;
    }

    @Override
    public String getUsername() {
        for (Credencial credencial : usuario.getCredenciais()) {
            if (credencial instanceof CredencialUsuarioSenha) {
                return ((CredencialUsuarioSenha) credencial).getNomeUsuario();
            }
        }
        return null;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
