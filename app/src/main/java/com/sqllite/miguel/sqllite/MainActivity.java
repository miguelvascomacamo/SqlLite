package com.sqllite.miguel.sqllite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper base_de_dados;

    EditText editName, editSurname, editMarks, editId;
    Button btnsave, btnread, btnupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editname);
        editSurname = (EditText) findViewById(R.id.editsurname);
        editMarks = (EditText) findViewById(R.id.editmarks);
        btnsave = (Button) findViewById(R.id.btnSave);
        btnread = (Button) findViewById(R.id.btnVerdata);
        btnupdate = (Button) findViewById(R.id.btnEditar);
        editId = (EditText) findViewById(R.id.edit_id);

        Conexao();
        saveData();
        readData();
        update();
    }



    private void Conexao () {

       base_de_dados = new DatabaseHelper(this);
    }

    private void saveData () {


     btnsave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
          boolean isInserted =  base_de_dados.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());

          if (isInserted == true) {
              Toast.makeText(MainActivity.this, "Dados Adicionados" , Toast.LENGTH_LONG).show();
          } else {
              Toast.makeText(MainActivity.this, "Erro ao Adicionar" , Toast.LENGTH_LONG).show();
          }
         }
     });
    }


    private void readData () {
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = base_de_dados.getAll();
                if (res.getCount() == 0) {
                    // show Message
                    showMessage("Error", "Nada Foi Encontrado");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID: " + res.getString(0)+ "\n");
                    buffer.append("NOME: " + res.getString(1)+ "\n");
                    buffer.append("APELIDO: " + res.getString(2)+ "\n");
                    buffer.append("MARKS: " + res.getString(3)+ "\n");
                }

                // Mostrar a Informacao
                showMessage("Informacao", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void update () {
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = base_de_dados.updateData(editId.getText().toString(), editName.getText().toString(), editSurname.toString(), editMarks.toString());
               if (isUpdate == true) {

                   Toast.makeText(MainActivity.this, "Dados Actualizados com Sucesso!" , Toast.LENGTH_LONG).show();
               } else {
                   Toast.makeText(MainActivity.this, "Erro ao Actualizar" , Toast.LENGTH_LONG).show();
               }
            }
        });
    }
}
