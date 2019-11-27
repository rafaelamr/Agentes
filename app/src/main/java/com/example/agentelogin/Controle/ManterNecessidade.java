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

public class ManterNecessidade extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public EditText aliasnecessidade;
    public TextView alias_idN;
    public Button aliasalvarN, aliasapagarN;
    public ListView aliaslistaN;

    private List<Necessidade> necessidades = new ArrayList<Necessidade>();
    private ArrayAdapter<Necessidade> necessidadeArrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Necessidade necessidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_necessidade);

        alias_idN = (TextView) findViewById(R.id.textViewIdN);
        aliasnecessidade = (EditText) findViewById(R.id.editTextNecessidade);
        aliasalvarN = (Button) findViewById(R.id.buttonsalvarN);
        aliasapagarN = (Button) findViewById(R.id.buttonapagarN);
        aliaslistaN = (ListView) findViewById(R.id.listviewnecessidade);
        aliaslistaN.setOnItemClickListener(this);
        necessidade = new Necessidade();

        inicializarFirebase();
        eventoDatabase();


        aliasalvarN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (necessidade.get_id()==null) {
                    necessidade = new Necessidade();
                    necessidade.set_id(databaseReference.push().getKey());
                }

                necessidade.setNecessidade((aliasnecessidade.getText().toString()));
                databaseReference.child("Necessidade").child(necessidade.get_id()).setValue(necessidade);
                Toast.makeText(getBaseContext(), "Dados Gravados com Sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        });

        aliasapagarN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Necessidade").child(necessidade.get_id()).removeValue();
                Toast.makeText(getBaseContext(), "Dados Excluídos com Sucesso", Toast.LENGTH_SHORT).show();
                necessidade = new Necessidade();
                limparCampos();
            }

        });
    }

    private void eventoDatabase() {
        databaseReference.child("Necessidade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                necessidades.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Necessidade necessidade = objSnapshot.getValue(Necessidade.class);
                    necessidades.add(necessidade);
                }
                necessidadeArrayAdapter = new ArrayAdapter<Necessidade>(ManterNecessidade.this, android.R.layout.simple_list_item_1, necessidades);
                aliaslistaN.setAdapter(necessidadeArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ManterNecessidade.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        necessidade = (Necessidade) parent.getItemAtPosition(position);
        alias_idN.setText(necessidade.get_id().toString());
        aliasnecessidade.setText(necessidade.getNecessidade().toString());
    }

    private void limparCampos() {
        alias_idN.setText("");
        aliasnecessidade.setText("");
    }
}
