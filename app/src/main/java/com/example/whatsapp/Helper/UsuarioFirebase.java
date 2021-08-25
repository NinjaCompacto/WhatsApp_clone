package com.example.whatsapp.Helper;

import com.google.firebase.auth.FirebaseAuth;

import configfirebase.ConfiguraçãoFirebase;

public class UsuarioFirebase {

    public static String getIdUsuario () {
        FirebaseAuth usuario = ConfiguraçãoFirebase.getAuth();
        String email = usuario.getCurrentUser().getEmail();
        String idusuario = Base64Custom.codificarBase64(email);
        return idusuario;
    }

}
