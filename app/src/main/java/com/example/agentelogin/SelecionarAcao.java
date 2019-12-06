package com.example.agentelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.agentelogin.Controle.ManterBairro;
import com.example.agentelogin.Controle.ManterFamilia;
import com.example.agentelogin.Controle.ManterNecessidade;

public class SelecionarAcao extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public ListView aliaslistview;
    public String[] menu = new String[]{"Cadastrar Necessidade", "Cadastrar Bairro", "Cadastrar Familia", "Sair"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_acao);
        aliaslistview = findViewById(R.id.listviewSA);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, menu);
        aliaslistview.setAdapter(adapter);
        aliaslistview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent(getBaseContext(), ManterNecessidade.class);
                startActivity(intent);
                break;


            case 1:

                Intent intent2 = new Intent(getBaseContext(), ManterBairro.class);
                startActivity(intent2);
                break;

            case 2:
                Intent intent1 = new Intent(getBaseContext(), ManterFamilia.class);
                startActivity(intent1);
                break;

            case 3:
                finish();
                break;
            default:
                break;
        }

    }
}
