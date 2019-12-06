package com.example.agentelogin.Modelo;

public class Familia {
    private String _id;
    private String nome;
    private String cpf;
    private String celular;
    private Necessidade necessidade;
    private Bairro bairro;
    public  Familia(){

    }

    @Override
    public String toString() {
        return "Representante da Familia:" + '\n' +
                " Nome: " + nome + '\n' +
                " CPF: " + cpf + '\n' +
                " Celular: " + celular + '\n' +
                " Bairro: " + bairro + '\n' +
                " Necessidade: " + necessidade;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Necessidade getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(Necessidade necessidade) {
        this.necessidade = necessidade;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
}
