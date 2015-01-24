package app.testbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Substancia;
import app.testbuilder.br.ufam.testbuilder.Utilities.ResultadoAdapter;

public class Resultado extends ActionBarActivity {

    Assist assist;
    int[] resultados;
    String[] substancias;
    ArrayList<Substancia> substanciasLista = new ArrayList<Substancia>();
    ResultadoAdapter adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        if(intent != null) {
            assist = intent.getParcelableExtra("ASSIST");

            resultados = assist.getResultado();
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

        substancias = getResources().getStringArray(R.array.substancias);
        Log.i("substancias", substancias.length+"");

        list = (ListView) findViewById(R.id.ListView12);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        // Mudando dinamicamente os tamanhos dos componentes visto que ScrollView não possîvel com ListView
        View view = findViewById(R.id.list_layout);

        view.getLayoutParams().height = (int) (displayMetrics.heightPixels * 0.7);
        view.setLayoutParams(view.getLayoutParams());

        showResult();

    }

    public void showResult() {
        String p1 = assist.getP1();
        substanciasLista.clear();

        for(int index = 0; index < p1.length(); index++) {
            if (p1.charAt(index) == '1') {
                substanciasLista.add(new Substancia(substancias[index], resultados[index]));
                Log.i("Substancia: ", substancias[index]);
            }
        }

        adapter = new ResultadoAdapter(this, substanciasLista);

        list.setAdapter(adapter);
    }
}
