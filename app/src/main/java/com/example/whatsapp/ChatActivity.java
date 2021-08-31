package com.example.whatsapp;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.whatsapp.Model.Usuario;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

        private CircleImageView circleImageChat;
        private TextView textNome;
        private Usuario usuariodestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Configuração toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configurações iniciais
        textNome = findViewById(R.id.textNomeChat);
        circleImageChat = findViewById(R.id.circleImageChat);

        //recuperando dados do usuario da intent
        Bundle bundle = getIntent().getExtras();
        if (bundle !=  null){
            usuariodestinatario = (Usuario) bundle.getSerializable("chatcontato");
            textNome.setText(usuariodestinatario.getNome());
                if (usuariodestinatario.getFoto() != null){
                    Uri urlfoto = Uri.parse(usuariodestinatario.getFoto());
                    Glide.with(getApplication()).load(urlfoto).into(circleImageChat);
                }
        }

    }
}