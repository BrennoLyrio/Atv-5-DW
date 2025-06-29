package com.comunicacao.sistema;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.comunicacao.sistema.entidades.*;
import com.comunicacao.sistema.enumeracoes.PerfilUsuario;
import com.comunicacao.sistema.enumeracoes.TipoDocumento;
import com.comunicacao.sistema.enumeracoes.TipoVeiculo;
import com.comunicacao.sistema.repositorios.*;

@SpringBootApplication
public class SistemaApplication implements CommandLineRunner {

    @Autowired private RepositorioEmpresa repositorioEmpresa;
    @Autowired private RepositorioUsuario repositorioUsuario;
    @Autowired private RepositorioServico repositorioServico;
    @Autowired private RepositorioMercadoria repositorioMercadoria;
    @Autowired private RepositorioVenda repositorioVenda;

    public static void main(String[] args) {
        SpringApplication.run(SistemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("AutoCenter Brasil");
        empresa.setNomeFantasia("AutoCenter");
        empresa.setEndereco(endereco("SP", "Campinas", "Centro", "Av. Brasil", "500", "13000-000"));

        // Serviços
        Servico alinhamento = servico("Alinhamento", "Alinhamento de rodas", 80, empresa);
        Servico trocaOleo = servico("Troca de Óleo", "Troca com óleo sintético", 120, empresa);
        Servico balanceamento = servico("Balanceamento", "Balanceamento das rodas", 100, empresa);
        empresa.getServicos().addAll(List.of(alinhamento, trocaOleo, balanceamento));

        // Mercadorias
        Mercadoria pneu = mercadoria("Pneu Aro 17", "Novo", 500, 15, empresa);
        Mercadoria bateria = mercadoria("Bateria 70Ah", "Durável", 450, 8, empresa);
        Mercadoria oleo = mercadoria("Óleo 5W30", "Lubrificante", 60, 20, empresa);
        empresa.getMercadorias().addAll(List.of(pneu, bateria, oleo));

        // Usuários
        Usuario admin = criarUsuario("Admin", "admin", "123456", PerfilUsuario.ROLE_ADMIN, encoder, empresa);
        Usuario func1 = criarUsuario("João", "joao", "123456", PerfilUsuario.ROLE_FUNCIONARIO, encoder, empresa);
        Usuario func2 = criarUsuario("Maria", "maria", "123456", PerfilUsuario.ROLE_FUNCIONARIO, encoder, empresa);

        Usuario cli1 = criarUsuario("Carlos", "carlos", "123456", PerfilUsuario.ROLE_CLIENTE, encoder, empresa);
        Usuario cli2 = criarUsuario("Ana", "ana", "123456", PerfilUsuario.ROLE_CLIENTE, encoder, empresa);
        Usuario cli3 = criarUsuario("Pedro", "pedro", "123456", PerfilUsuario.ROLE_CLIENTE, encoder, empresa);

        Veiculo v1 = veiculo("Corolla", "AAA-1111", TipoVeiculo.SEDAN, cli1);
        Veiculo v2 = veiculo("HB20", "BBB-2222", TipoVeiculo.HATCH, cli2);
        Veiculo v3 = veiculo("T-Cross", "CCC-3333", TipoVeiculo.SUV, cli3);
        cli1.getVeiculos().add(v1);
        cli2.getVeiculos().add(v2);
        cli3.getVeiculos().add(v3);

        empresa.getUsuarios().addAll(List.of(admin, func1, func2, cli1, cli2, cli3));

        // Vendas
        Venda venda1 = new Venda();
        venda1.setCadastro(java.sql.Date.valueOf("2025-06-21"));
        venda1.setIdentificacao("V001");
        venda1.setCliente(cli1);
        venda1.setFuncionario(func1);
        venda1.setVeiculo(v1);
        venda1.setEmpresa(empresa);
        venda1.getServicos().add(alinhamento);
        venda1.getMercadorias().add(pneu);
        v1.getVendas().add(venda1);

        Venda venda2 = new Venda();
        venda2.setCadastro(java.sql.Date.valueOf("2025-06-21"));
        venda2.setIdentificacao("V002");
        venda2.setCliente(cli2);
        venda2.setFuncionario(func2);
        venda2.setVeiculo(v2);
        venda2.setEmpresa(empresa);
        venda2.getServicos().add(trocaOleo);
        venda2.getMercadorias().add(oleo);
        v2.getVendas().add(venda2);

        Venda venda3 = new Venda();
        venda3.setCadastro(java.sql.Date.valueOf("2025-06-21"));
        venda3.setIdentificacao("V003");
        venda3.setCliente(cli3);
        venda3.setFuncionario(func1);
        venda3.setVeiculo(v3);
        venda3.setEmpresa(empresa);
        venda3.getServicos().add(balanceamento);
        venda3.getMercadorias().add(bateria);
        v3.getVendas().add(venda3);

        empresa.getVendas().addAll(List.of(venda1, venda2, venda3));

        repositorioEmpresa.save(empresa);
    }

    private Usuario criarUsuario(String nome, String login, String senha, PerfilUsuario perfil, BCryptPasswordEncoder encoder, Empresa empresa) {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setNomeSocial(nome + " Social");
        u.setPerfis(Set.of(perfil));
        u.setEndereco(endereco("SP", "Cidade", "Bairro", "Rua X", "123", "00000-000"));
        u.getEmails().add(email(login + "@email.com"));
        u.getTelefones().add(telefone("11", "9" + new Random().nextInt(99999999)));
        u.getDocumentos().add(documento(String.valueOf(Math.abs(new Random().nextLong() % 100000000000L)), TipoDocumento.CPF));
        u.getCredenciais().add(credencial(login, senha, encoder));
        u.setEmpresa(empresa);
        return u;
    }

    private Servico servico(String nome, String desc, double valor, Empresa empresa) {
        Servico s = new Servico();
        s.setNome(nome);
        s.setDescricao(desc);
        s.setValor(valor);
        s.setEmpresa(empresa);
        return s;
    }

    private Mercadoria mercadoria(String nome, String desc, double valor, int qtd, Empresa empresa) {
        Mercadoria m = new Mercadoria();
        m.setNome(nome);
        m.setDescricao(desc);
        m.setValor(valor);
        m.setQuantidade(qtd);
        m.setCadastro(new Date());
        m.setFabricao(new Date());
        m.setValidade(new Date());
        m.setEmpresa(empresa);
        return m;
    }

    private Endereco endereco(String estado, String cidade, String bairro, String rua, String numero, String cep) {
        Endereco e = new Endereco();
        e.setEstado(estado);
        e.setCidade(cidade);
        e.setBairro(bairro);
        e.setRua(rua);
        e.setNumero(numero);
        e.setCodigoPostal(cep);
        return e;
    }

    private Telefone telefone(String ddd, String numero) {
        Telefone t = new Telefone();
        t.setDdd(ddd);
        t.setNumero(numero);
        return t;
    }

    private Email email(String endereco) {
        Email e = new Email();
        e.setEndereco(endereco);
        return e;
    }

    private Documento documento(String numero, TipoDocumento tipo) {
        Documento d = new Documento();
        d.setNumero(numero);
        d.setTipo(tipo);
        d.setDataEmissao(new Date());
        return d;
    }

    private CredencialUsuarioSenha credencial(String login, String senha, BCryptPasswordEncoder encoder) {
        CredencialUsuarioSenha c = new CredencialUsuarioSenha();
        c.setNomeUsuario(login);
        c.setSenha(encoder.encode(senha));
        c.setCriacao(new Date());
        c.setUltimoAcesso(new Date());
        c.setInativo(false);
        return c;
    }

    private Veiculo veiculo(String modelo, String placa, TipoVeiculo tipo, Usuario proprietario) {
        Veiculo v = new Veiculo();
        v.setModelo(modelo);
        v.setPlaca(placa);
        v.setTipo(tipo);
        v.setProprietario(proprietario);
        return v;
    }
}