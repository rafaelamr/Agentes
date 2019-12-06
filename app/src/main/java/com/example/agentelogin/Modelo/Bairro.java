package com.example.agentelogin.Modelo;

public class Bairro {
    private String _id;
    private String bairro;
    private String pntEntrega;

    public Bairro(){
    }


    @Override
    public String toString() {  return  bairro;}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getPntEntrega() {
        return pntEntrega;
    }

    public void setPntEntrega(String pntEntrega) {
        this.pntEntrega = pntEntrega;
    }
}
