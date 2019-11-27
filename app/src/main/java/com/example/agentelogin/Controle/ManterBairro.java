package com.example.agentelogin.Controle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agentelogin.Modelo.Bairro;
import com.example.agentelogin.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManterBairro extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public EditText aliasbairro;
    public TextView alias_idB;
    public Button aliasalvarB, aliasapagarB;
    public ListView aliaslistaB;

    private List<Bairro> bairros = new ArrayList<Bairro>();
    private ArrayAdapter<Bairro> bairroArrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Bairro bairro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_bairro);

        alias_idB = (TextView) findViewById(R.id.textViewIdB);
        aliasbairro = (EditText) findViewById(R.id.editTextBairro);
        aliasalvarB = (Button) findViewById(R.id.buttonsalvarN);
        aliasapagarB = (Button) findViewById(R.id.buttonapagarN);
        aliaslistaB = (ListView) findViewById(R.id.listviewnecessidade);
        aliaslistaB.setOnItemClickListener(this);
        bairro = new Bairro();

        inicializarFirebase();
        eventoDatabase();


        aliasalvarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bairro.get_id()==null) {
                    bairro = new Bairro();
                    bairro.set_id(databaseReference.push().getKey());
                }

                bairro.setBairro((aliasbairro.getText().toString()));
                databaseReference.child("Bairro").child(bairro.get_id()).setValue(bairro);
                Toast.makeText(getBaseContext(), "Dados Gravados com Sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        });

        aliasapagarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Bairro").child(bairro.get_id()).removeValue();
                Toast.makeText(getBaseContext(), "Dados Excluídos com Sucesso", Toast.LENGTH_SHORT).show();
                bairro = new Bairro();
                limparCampos();
            }

        });
    }

    private void eventoDatabase() {
        databaseReference.child("Bairro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bairros.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Bairro bairro = objSnapshot.getValue(Bairro.class);
                    bairros.add(bairro);
                }
                bairroArrayAdapter = new ArrayAdapter<Bairro>(ManterBairro.this, android.R.layout.simple_list_item_1, bairros);
                aliaslistaB.setAdapter(bairroArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ManterBairro.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
       // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        bairro = (Bairro) parent.getItemAtPosition(position);
        alias_idB.setText(bairro.get_id().toString());
        aliasbairro.setText(bairro.getBairro().toString());
    }

    private void limparCampos() {
        alias_idB.setText("");
        aliasbairro.setText("");
    }
}
