package app.testbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;

public class ASSISTPergunta3 extends ActionBarActivity {

    private int testeId = 1; // Preciso saber no BD um número possível para representar erro
    Assist assist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assistpergunta3);

        // START Retrieve data from another activity
        Intent intent = getIntent();

        if(intent != null) {
            testeId = intent.getIntExtra("TESTE_ID", 1);
            assist = intent.getParcelableExtra("ASSIST");
        }

        Button confirmButton = (Button) findViewById(R.id.button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
                int itemChecked = radioGroup.getCheckedRadioButtonId();
                AssistDAO aDao = new AssistDAO(getApplicationContext());

                if (radioGroup.getCheckedRadioButtonId() != -1) {

                    try {
                        //assist = aDao.getLastId();
                        String resposta = "";

                        resposta += itemChecked == R.id.radioButton1 ? 0 : itemChecked == R.id.radioButton2 ? 1 : 2;

                        //assist.setTeste_id(testeId);
                        assist.setP8(resposta);

                        Toast.makeText(getApplicationContext(), "Resultado:" + resposta, Toast.LENGTH_SHORT).show();
                        aDao.update(assist);
                    } catch (SQLException e) {
                        Log.e("ERROR:", e.getMessage());
                    }

                    Log.d("DEBUG", "BUTTON CLICKED");

                    Intent intent = new Intent(ASSISTPergunta3.this, Resultado.class);
                    intent.putExtra("ASSIST", assist);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Questão não respondida", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
