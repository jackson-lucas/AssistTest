package app.testbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.SQLException;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;

public class ASSISTPergunta3 extends ActionBarActivity {

    //DAO's
    public Assist assist;
    public AssistDAO aDao;
    public Teste teste;
    public TesteDAO tDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assistpergunta3);

        // START Retrieve data from another activity
        Intent intent = getIntent();

        if(intent != null) {
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

                        aDao.update(assist);
                    } catch (SQLException e) {
                        Log.e("ERROR:", e.getMessage());
                    }

                    Log.d("DEBUG", "BUTTON CLICKED");

                    Intent intent = new Intent(ASSISTPergunta3.this, Resultado.class);
                    intent.putExtra("ASSIST", assist);
                    intent.putExtra("SUSPENSO", false);
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
        getMenuInflater().inflate(R.menu.menu_perguntas, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        teste = new Teste();
        tDao = new TesteDAO(getApplicationContext());
        if (id == R.id.action_suspend) {
            Intent intent = new Intent(ASSISTPergunta3.this, Resultado.class);
            intent.putExtra("ASSIST", assist);
            intent.putExtra("SUSPENSO", true);
            //Atualiza o teste para CANCELADO, caso o cumpridor desista de fazê-lo;
            try {
                teste.setId(tDao.getLastId().getId());
                teste.setStatus("0");
                tDao.update(teste);
            } catch (SQLException e) {
                trace("ERROR:" + e.getMessage());
            }

            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }

}
