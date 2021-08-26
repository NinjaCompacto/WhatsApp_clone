package com.example.whatsapp.Helper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.whatsapp.ConfiguracoesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import configfirebase.ConfiguraçãoFirebase;

public class UsuarioFirebase {

    public static String getIdUsuario () {
        FirebaseAuth usuario = ConfiguraçãoFirebase.getAuth();
        String email = usuario.getCurrentUser().getEmail();
        String idusuario = Base64Custom.codificarBase64(email);
        return idusuario;
    }

    public static FirebaseUser getUsuarioatual (){
        FirebaseAuth usuario = ConfiguraçãoFirebase.getAuth();
        return usuario.getCurrentUser();
    }

    public static Boolean atualizarNomeUsuario (String nome){
        try {
            FirebaseUser user = UsuarioFirebase.getUsuarioatual();
            //atualiza nome do usuario
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (!task.isSuccessful())
                    Log.d("Perfil", "Erro ao atualizar nome do usuario");
                }
            });

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean atualizaFotoUsuario (Uri url){
        try{
            FirebaseUser user = UsuarioFirebase.getUsuarioatual();
            //atualizando profile do usuario Firebase
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setPhotoUri(url).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar foto de perfil.");
                    }
                }
            });
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
