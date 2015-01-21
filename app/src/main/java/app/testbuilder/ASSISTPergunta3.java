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

// TODO Update no SQLite ao finalizar
public class ASSISTPergunta3 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assistpergunta3);

        Button confirmButton = (Button) findViewById(R.id.button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
                int itemChecked = radioGroup.getCheckedRadioButtonId();
                Assist assist = new Assist();
                AssistDAO aDao = new AssistDAO(getApplicationContext());
                Teste teste_id = null;

                if (radioGroup.getCheckedRadioButtonId() != -1) {

                    try {
                        teste_id = aDao.getLastId();
                        String resposta = "";

                        resposta += itemChecked == R.id.radioButton1 ? 0 :
                                itemChecked == R.id.radioButton2 ? 1 : 2;

                        assist.setTeste_id(teste_id.getId());
                        assist.setP8(resposta);

                        Toast.makeText(getApplicationContext(), "Resultado:" + resposta, Toast.LENGTH_SHORT).show();
                        aDao.inserir(assist);
                    } catch (SQLException e) {
                        Log.e("ERROR:", e.getMessage());
                    }

                    Log.d("DEBUG", "BUTTON CLICKED");

                    Intent intent = new Intent(ASSISTPergunta3.this, Resultado.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Questão não respondida", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
