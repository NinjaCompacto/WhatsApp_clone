package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.whatsapp.Helper.Permissao;

public class ConfiguracoesActivity extends AppCompatActivity {

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        Permissao.validarPermissoes(permissoesNecessarias, this,1);

        //ToolBar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);

        //adiciona botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoresultado: grantResults){
            if (permissaoresultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

        private void alertaValidacaoPermissao(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permissões Negadas");
            builder.setMessage("Para utilizar o APP é preciso aceitar todas as permissões");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }


}