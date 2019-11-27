package com.example.agentelogin.Controle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agentelogin.Modelo.Bairro;
import com.example.agentelogin.Modelo.Familia;
import com.example.agentelogin.Modelo.Necessidade;
import com.example.agentelogin.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManterFamilia extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText aliasnome, aliascpf, aliascelular;
    TextView alias_id;
    Button aliasalvar, aliasapagar;
    ListView aliaslista;
    Spinner aliasnecessidade, aliasbairro;

    private List<Familia> familias = new ArrayList<Familia>();
    private ArrayAdapter<Familia> familiaArrayAdapter;

    private List<Necessidade> necessidades = new ArrayList<Necessidade>();
    private ArrayAdapter<Necessidade> necessidadeArrayAdapter;

    private List<Bairro> bairros = new ArrayList<Bairro>();
    private ArrayAdapter<Bairro> bairroArrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Familia familia;
    Bairro bairro;
    Necessidade necessidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_familia);

        alias_id = (TextView) findViewById(R.id.textViewID);
        aliasnome = (EditText) findViewById(R.id.editNome);
        aliascpf = (EditText) findViewById(R.id.editCPF);
        aliascelular = (EditText) findViewById(R.id.editCelular);
        aliasalvar = (Button) findViewById(R.id.buttonsalvarN);
        aliasapagar = (Button) findViewById(R.id.buttonapagar);
        aliaslista = (ListView) findViewById(R.id.listviewfamilia);
        aliasnecessidade = findViewById(R.id.spinnernecessidade);
        aliasbairro = findViewById(R.id.spinnerbairros);
        familia = new Familia();
        aliaslista.setOnItemClickListener(this);

        inicializarFirebase();
        eventoDatabase();


        aliasalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (familia.get_id() == null) {
                    familia = new Familia();
                    familia.set_id(databaseReference.push().getKey());

                }

                    familia.setNome(aliasnome.getText().toString());
                    familia.setCpf(aliascpf.getText().toString());
                    familia.setCelular(aliascelular.getText().toString());
                    familia.setBairro(bairro);
                    familia.setNecessidade(necessidade);
                    databaseReference.child("Familia").child(familia.get_id()).setValue(familia);
                    Toast.makeText(getBaseContext(), "Dados Gravados com Sucesso", Toast.LENGTH_SHORT).show();
                    limparCampos();
                }

        });

        aliasapagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Familia").child(familia.get_id()).removeValue();
                Toast.makeText(getBaseContext(), "Dados Excluídos com Sucesso", Toast.LENGTH_SHORT).show();
                familia = new Familia();
                limparCampos();
            }

        });
    }
//    @Override
//    public boolean onCreateOptionsMenu (Menu menu){
//        getMenuInflater().inflate(R.menu.menu_manter, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id= item.getItemId();
//        if(id==R.id.ItemLocalizar){
//            familias = databaseReference.child();
//            familia=new Familia();
//            familia.set_id(familias.get(0).get_id());
//            familia.setNome(familias.get(0).getNome());
//            familia.setCpf(familias.get(0).getCpf());
//            familia.setCelular(familias.get(0).getCpf());
//            familia.setBairro(familias.get(0).getBairro());
//            familia.setNecessidade(familias.get(0).getNecessidade());
//
//        }
//        return true;
//    }

    private void eventoDatabase() {
        databaseReference.child("Familia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                familias.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Familia familia = objSnapshot.getValue(Familia.class);
                    familias.add(familia);
                }
                familiaArrayAdapter = new ArrayAdapter<Familia>(ManterFamilia.this, android.R.layout.simple_list_item_1, familias);
                aliaslista.setAdapter(familiaArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.child("Necessidade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                necessidades.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Necessidade necessidade = objSnapshot.getValue(Necessidade.class);
                    necessidades.add(necessidade);
                }
                necessidadeArrayAdapter = new ArrayAdapter<Necessidade>(ManterFamilia.this, android.R.layout.simple_list_item_1, necessidades);
                aliasnecessidade.setAdapter(necessidadeArrayAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        aliasnecessidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                necessidade = (Necessidade) parent.getAdapter().getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        databaseReference.child("Bairro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bairros.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Bairro bairro = objSnapshot.getValue(Bairro.class);
                    bairros.add(bairro);
                }
                bairroArrayAdapter = new ArrayAdapter<Bairro>(ManterFamilia.this, android.R.layout.simple_list_item_1, bairros);
                aliasbairro.setAdapter(bairroArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        aliasbairro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bairro = (Bairro) parent.getAdapter().getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        verificarparametro();
        // VERIFICAR PARAMETRO########################
//        aliasbairro.setSelection(getIndex(aliasbairro, familia.getBairro().toString()));
//        aliasnecessidade.setSelection(getIndex(aliasnecessidade, familia.getNecessidade().toString()));

    }

    private void verificarparametro() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("Objeto") == null) {
            // Toast.makeText(this, "Vazio", Toast.LENGTH_SHORT).show();/
            // como o metodo verificarparametro está sendo chamado no oncreate ou no onstart,
            // é necessário verificar com o if acima se o objeto está vazio (novo) ou se o objeto vem de um clique
            // em uma lista.
            familia = new Familia();
        } else {
            //  Toast.makeText(this, "Cheio", Toast.LENGTH_SHORT).show();
            familia = (Familia) intent.getSerializableExtra("Objeto"); // nome do parametro recebido é Objeto...
            alias_id.setText(familia.get_id().toString());
            aliasnome.setText(familia.getNome());
            aliascpf.setText(familia.getCpf());
            aliascelular.setText(familia.getCelular());
            aliasbairro.setSelection(getIndex(aliasbairro, familia.getBairro().toString()));
            aliasnecessidade.setSelection(getIndex(aliasnecessidade, familia.getNecessidade().toString()));
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ManterFamilia.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        familia = (Familia) parent.getItemAtPosition(position);
        alias_id.setText(familia.get_id().toString());
        aliasnome.setText(familia.getNome().toString());
        aliascpf.setText(familia.getCpf().toString());
        aliascelular.setText(familia.getCelular().toString());
    }


    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }


    private void limparCampos() {
        alias_id.setText("");
        aliasnome.setText("");
        aliascpf.setText("");
        aliascelular.setText("");
    }


}