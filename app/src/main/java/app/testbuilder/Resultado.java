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

import org.json.JSONObject;

import java.util.List;

import app.testbuilder.br.com.TestBuilder.Model.Assist;

// TODO Update no SQLite ao finalizar
public class Resultado extends ActionBarActivity {

    Assist assist;
    int[] resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        if(intent != null) {
            assist = intent.getParcelableExtra("ASSIST");

            resultado = assist.getResultado();

            for(int result : resultado) {

                Log.i("resultado: ", result+"");
            }
        }

        Button confirmButton = (Button) findViewById(R.id.button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "BUTTON FINALIZAR CLICKED");
                Intent intent = new Intent(Resultado.this, Main.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
