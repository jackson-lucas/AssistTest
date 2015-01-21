package app.testbuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.sql.SQLException;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;
import app.testbuilder.br.com.TestBuilder.Model.Usuario;

// TODO Update no SQLite ao finalizar
public class ASSISTPergunta1 extends ActionBarActivity {

    // TODO create a dialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assistpergunta1);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Nem mesmo quando você estava na escola?");

        alert.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Paciente quer continuar o teste
            }
        });
        alert.setPositiveButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Paciente nunca usou nenhum tipo de droga

                Toast.makeText(getApplicationContext(), "Parabéns por não utilizar drogas!", Toast.LENGTH_LONG).show();

                Intent intent;
                intent = new Intent(ASSISTPergunta1.this, Main.class);
                startActivity(intent);
                finish();
            }
        });

        alert.create();

        String[] perguntasAssist = getResources().getStringArray(R.array.perguntas_assist);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(perguntasAssist[0]);

        Button confirmButton = (Button) findViewById(R.id.button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "BUTTON CLICKED");
                Assist assist = new Assist();
                AssistDAO aDao = new AssistDAO(getApplicationContext());
                Teste teste_id = null;
                // Check checkboxes
                if(verifyCheckboxes()) {
                    try {
                        teste_id = aDao.getLastId();
                        String p1 = aDao.booleanToString(getSubstancias());
                        assist.setTeste_id(teste_id.getId());
                        assist.setP1(p1);
                        aDao.inserir(assist);
                    } catch (SQLException e) {
                        trace("ERROR:" + e.getMessage());
                    }

                    Intent intent = new Intent(ASSISTPergunta1.this, ASSISTPergunta2.class);
                    intent.putExtra("QUESTION", 1);
                    intent.putExtra("SUBSTANCIAS", getSubstancias());
                    intent.putExtra("ASSIST", assist);
                    startActivity(intent);
                    finish();
                } else {

                    alert.show();

                }
            }
        });
    }

    int getCheckBoxId(int index) {
      switch (index) {
          case 2:
              return R.id.checkBox2;
          case 3:
              return R.id.checkBox3;
          case 4:
              return R.id.checkBox4;
          case 5:
              return R.id.checkBox5;
          case 6:
              return R.id.checkBox6;
          case 7:
              return R.id.checkBox7;
          case 8:
              return R.id.checkBox8;
          case 9:
              return R.id.checkBox9;
          case 10:
              return R.id.checkBox10;
          default:
              return R.id.checkBox1;
      }
    };

    public boolean verifyCheckboxes() {
        for(int index = 1; index < 11; index++) {
            CheckBox checkBox = (CheckBox) findViewById(getCheckBoxId(index));
            if(checkBox.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public boolean[] getSubstancias() {
        boolean[] substanciasUsadas = new boolean[10];
        int[] checkboxes = {R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox4,
                R.id.checkBox5, R.id.checkBox6, R.id.checkBox7, R.id.checkBox8, R.id.checkBox9,
                R.id.checkBox10};

        for(int index = 0; index < checkboxes.length; index++) {
            CheckBox checkBox = (CheckBox) findViewById(checkboxes[index]);
            if(checkBox.isChecked()) {
                substanciasUsadas[index] = true;
            } else {
                substanciasUsadas[index] = false;
            }
        }
        return substanciasUsadas;
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
        // Modificao de action_settings para action_bar nao sei se foi correto
        if (id == R.id.action_bar) {
            return true;
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
