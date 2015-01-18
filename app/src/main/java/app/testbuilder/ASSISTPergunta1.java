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

        String[] perguntasAssist = getResources().getStringArray(R.array.perguntas_assist);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(perguntasAssist[0]);

        Button confirmButton = (Button) findViewById(R.id.button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "BUTTON CLICKED");
                Intent intent = new Intent(ASSISTPergunta1.this, ASSISTPergunta2.class);
                intent.putExtra("QUESTION", 1);
                intent.putExtra("SUBSTANCIAS", getSubstancias());
                startActivity(intent);
                finish();
            }
        });

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
