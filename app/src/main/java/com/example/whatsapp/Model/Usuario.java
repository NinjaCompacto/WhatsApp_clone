package com.example.whatsapp.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import configfirebase.ConfiguraçãoFirebase;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void salvar(){
        DatabaseReference databaseReference = ConfiguraçãoFirebase.getDatabaseReference();
        DatabaseReference usuario = databaseReference.child("usuarios").child(getId());

        usuario.setValue(this);
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
