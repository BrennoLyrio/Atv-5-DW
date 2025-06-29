package com.comunicacao.sistema.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import com.comunicacao.sistema.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Usuario extends RepresentationModel<Usuario> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String nomeSocial;
    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Telefone> telefones = new HashSet<>();
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Documento> documentos = new HashSet<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Email> emails = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<PerfilUsuario> perfis = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Credencial> credenciais = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private Set<Mercadoria> mercadorias = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Venda> vendas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "proprietario", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Veiculo> veiculos = new HashSet<>();

    

    @JsonIgnore
    @ManyToOne
    private Empresa empresa; 

    public boolean temPerfil(String perfil) {
        return this.getPerfis().stream().anyMatch(p -> p.name().equals(perfil));
    }
}
