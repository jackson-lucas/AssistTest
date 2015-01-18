package app.testbuilder;

import android.app.AlertDialog;
import android.app.DialogFragment;
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

import java.util.List;

// TODO Update no SQLite ao finalizar
public class ASSISTPergunta1 extends ActionBarActivity {

    List<Boolean> substanciasUsadas;

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
                Intent intent;
                // Paciente nunca usou nenhum tipo de droga
                intent = new Intent(ASSISTPergunta1.this, Main.class);
                intent.putExtra("INTEGRO", true);
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

                // Check checkboxes
                if(verifyCheckboxes()) {

                    Intent intent = new Intent(ASSISTPergunta1.this, ASSISTPergunta2.class);
                    intent.putExtra("QUESTION", 1);
                    intent.putExtra("SUBSTANCIAS", getSubstancias());
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
}
