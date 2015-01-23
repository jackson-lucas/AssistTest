package app.testbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import app.testbuilder.br.com.TestBuilder.Model.Assist;

public class Resultado extends ActionBarActivity {

    Assist assist;
    int[] resultados;
    String[] substancias;

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

        showResult();

    }

    public void showResult() {
        TableLayout tabelaResulado = (TableLayout) findViewById(R.id.table_result);
        String p1;
        int resultado;
        String intervencao;

        TableRow.LayoutParams textLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        //textLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        p1 = assist.getP1();
        for(int index = 0, indexResultado = 0; index < p1.length(); index++) {
            if(p1.charAt(index) == '1') {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.setGravity(Gravity.CENTER_HORIZONTAL);

                TextView substancia = new TextView(this);
                substancia.setText(substancias[index]);
                substancia.setLayoutParams(textLayoutParams);

                resultado = resultados[indexResultado];
                Log.i("Resultado: ", resultado+"");
                indexResultado++;

                TextView pontuacao = new TextView(this);
                pontuacao.setText(resultado+"");
                pontuacao.setLayoutParams(textLayoutParams);

                if(substancias[index].equals("bebidas alcoólicas")) {
                    if(resultado < 11) {
                        intervencao = "Nenhuma intervenção";
                    } else if(resultado < 27) {
                        intervencao = "Intervenção breve";
                    } else {
                        intervencao = "Tratamento intensivo";
                    }
                } else {
                    if(resultado < 4) {
                        intervencao = "Nenhuma intervenção";
                    } else if(resultado < 27) {
                        intervencao = "Intervenção breve";
                    } else {
                        intervencao = "Tratamento intensivo";
                    }
                }

                TextView recomendacao = new TextView(this);
                recomendacao.setText(intervencao);
                recomendacao.setLayoutParams(textLayoutParams);

                tableRow.addView(substancia);
                tableRow.addView(pontuacao);
                tableRow.addView(recomendacao);

                tabelaResulado.addView(tableRow);
            };
        }

    }
}
