package com.example.whatsapp;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.whatsapp.Helper.Base64Custom;
import com.example.whatsapp.Helper.UsuarioFirebase;
import com.example.whatsapp.Model.Mensagem;
import com.example.whatsapp.Model.Usuario;
import com.example.whatsapp.adapter.ChatAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import configfirebase.ConfiguraçãoFirebase;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
        //firebase
        private DatabaseReference firebasereference = ConfiguraçãoFirebase.getDatabaseReference();
        private DatabaseReference mensagensref;
        private ChildEventListener childEventListenerMensagens;
        private ValueEventListener valueEventListenerMensagens;
        //XML
        private CircleImageView circleImageChat;
        private TextView textNome;
        private Usuario usuariodestinatario;
        private EditText ediTextMessagem;
        //id dos usuarios
        private String idusuarioremetente, idusuariodestinatario;
       //RecyclerView
        private RecyclerView recyclerMensagens;
        private ChatAdapter chatAdapter;
        private List<Mensagem> mensagens = new ArrayList<>();

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
        ediTextMessagem = findViewById(R.id.editTextMessagem);
        idusuarioremetente = UsuarioFirebase.getIdUsuario();
        recyclerMensagens = findViewById(R.id.recyclerMensagens);

        //recuperando dados do usuariodestinatario
        Bundle bundle = getIntent().getExtras();
        if (bundle !=  null){
            usuariodestinatario = (Usuario) bundle.getSerializable("chatcontato");
            textNome.setText(usuariodestinatario.getNome());
                if (usuariodestinatario.getFoto() != null){
                    Uri urlfoto = Uri.parse(usuariodestinatario.getFoto());
                    Glide.with(getApplication()).load(urlfoto).into(circleImageChat);
                }
            //recuperar id do usuario destinatario
            idusuariodestinatario = Base64Custom.codificarBase64(usuariodestinatario.getEmail());
        }

        //atualizarMensagens();

        //configurando adapter
        chatAdapter = new ChatAdapter(mensagens,getApplicationContext());
        //configurando recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerMensagens.setLayoutManager(layoutManager);
        recyclerMensagens.setHasFixedSize(true);
        recyclerMensagens.setAdapter(chatAdapter);

        mensagensref = firebasereference.child("mensagem").child(idusuarioremetente).child(idusuariodestinatario);


    }

    public void enviar (View view) {
        String Textomensagem = ediTextMessagem.getText().toString();

        //verifica se a mensagem tem algum conteudo
        if (!Textomensagem.isEmpty()){
            Mensagem msg = new Mensagem();
            msg.setMensagem(Textomensagem);
            msg.setIdUsuario(idusuarioremetente);
            //salvando para remetente
            salvarMensagem(idusuarioremetente,idusuariodestinatario,msg);
            //salvando para destinatario
            salvarMensagem(idusuariodestinatario,idusuarioremetente,msg);

        }
        else{
            Toast.makeText(ChatActivity.this,"Digite uma mensagem para enviar", Toast.LENGTH_SHORT).show();;
        }


    }

    private void salvarMensagem (String idRemetente, String idDestinatario , Mensagem mensagem){
        mensagensref = firebasereference.child("mensagem");

        //salvar mensagem para remetente e destinatario
        mensagensref.child(idRemetente).child(idDestinatario).push().setValue(mensagem);
        ediTextMessagem.setText("");

    }

    @Override
    protected void onStart() {
        super.onStart();
        //atualizarMensagens();
        recuperarMensagens();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mensagensref.removeEventListener(childEventListenerMensagens);
    }

    private void recuperarMensagens () {



        childEventListenerMensagens = mensagensref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot snapshot,  String previousChildName) {
                Mensagem mensagem = snapshot.getValue(Mensagem.class);
                mensagens.add(mensagem);
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged( DataSnapshot snapshot,  String previousChildName) {

            }

            @Override
            public void onChildRemoved( DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot snapshot,  String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

}