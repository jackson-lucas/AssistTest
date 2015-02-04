package app.testbuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Substancia;
import app.testbuilder.br.com.TestBuilder.Utilities.ResultadoAdapter;

public class Resultado extends ActionBarActivity {

    Assist assist;
    int[] resultados = null;
    String[] substancias;
    boolean suspenso;
    ArrayList<Substancia> substanciasLista = new ArrayList<Substancia>();
    ResultadoAdapter adapter;
    ListView list;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        if(intent != null) {
            assist = intent.getParcelableExtra("ASSIST");
            suspenso = intent.getBooleanExtra("SUSPENSO", false);

            if (!suspenso) {
                resultados = assist.getResultado();
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

        substancias = getResources().getStringArray(R.array.substancias);
        Log.i("substancias", substancias.length+"");

        list = (ListView) findViewById(R.id.ListView12);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        // Mudando dinamicamente os tamanhos dos componentes visto que ScrollView não possîvel com ListView
        View view = findViewById(R.id.list_layout);

        view.getLayoutParams().height = (int) (displayMetrics.heightPixels * 0.7);
        view.setLayoutParams(view.getLayoutParams());

        if(assist.getP1() != null) {
            showResult();
        }

        // Dialog
        alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View helpView = (View) inflater.inflate(R.layout.help_dialog, null);

        alert.setTitle("Ajuda");
        alert.setView(helpView);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alert.create();

    }

    public void showResult() {
        String p1 = assist.getP1();
        substanciasLista.clear();
        if(resultados!=null) {
            for(int index = 0; index < p1.length(); index++) {
                if (p1.charAt(index) == '1') {
                    substanciasLista.add(new Substancia(substancias[index], resultados[index]));
                    Log.i("Substancia: ", substancias[index]);
                }
            }
        } else {
            for(int index = 0; index < p1.length(); index++) {
                if (p1.charAt(index) == '1') {
                    substanciasLista.add(new Substancia(substancias[index], -1));
                    Log.i("Substancia: ", substancias[index]);
                }
            }
        }

        adapter = new ResultadoAdapter(this, substanciasLista);

        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);

        // TODO criar metodo para verificar se existe algo no BD
        //MenuItem menuItem = menu.findItem(R.id.action_send_data);
        //menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here, The action bar will automatically handle clicks on the
        Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();

        if (id == R.id.action_help) {
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

}
