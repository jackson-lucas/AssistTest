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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Substancia;
import app.testbuilder.br.com.TestBuilder.Model.Teste;
import app.testbuilder.br.com.TestBuilder.Utilities.ResultadoAdapter;

public class Resultado extends ActionBarActivity {

    int[] resultados = null;
    String[] substancias;
    boolean suspenso;
    ArrayList<Substancia> substanciasLista = new ArrayList<Substancia>();
    ResultadoAdapter adapter;
    ListView list;
    String observacao = "";

    //DAO's
    public Assist assist;
    public AssistDAO aDao;
    public Teste teste;
    public TesteDAO tDao;

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

        aDao = new AssistDAO(getApplicationContext());

        if(suspenso) {
            Log.i("RESULTADO", assist.getId() + " <- ID");
            assist.setP1("0000000000");
            assist.setP2("0000000000");
            assist.setP3("0000000000");
            assist.setP4("0000000000");
            assist.setP5("0000000000");
            assist.setP6("0000000000");
            assist.setP7("0000000000");
            assist.setP8("0");
            assist.setObs(observacao);

            try {

                aDao.update(assist);

            } catch (SQLException e) {
                trace("ERROR:" +e.getMessage());
            }
        }

        //Confirma o butão resultado
        Button confirmButton = (Button) findViewById(R.id.button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "BUTTON FINALIZAR CLICKED");

                // Não existem dados para serem limpados. os dados apresentados são os do teste atual! (Jackson)

                try {
                    assist.setObs(observacao);
                    aDao.update(assist);

                } catch (SQLException e) {
                    trace("ERROR:" +e.getMessage());
                }

                Intent intent = new Intent(Resultado.this, Main.class);
                intent.putExtra("RESULTADO", true);
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
    }

    public void createObservacaoDialog() {
        final EditText editText = new EditText(this);
        editText.setId(R.id.editText);
        editText.setText(observacao);



        AlertDialog.Builder observacaoDialog = new AlertDialog.Builder(this);
        observacaoDialog.setTitle("Observação")
            .setView(editText)
            .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditText editText = (EditText) ((AlertDialog) dialog).findViewById(R.id.editText);
                    observacao = editText.getText().toString();
                    dialog.dismiss();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            })
            .create()
            .show();
    }

    public void createAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View helpView = (View) inflater.inflate(R.layout.help_dialog, null);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Ajuda")
            .setView(helpView)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            })
            .create()
            .show();
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
            createAlertDialog();
        } else if (id == R.id.action_observacao) {
            createObservacaoDialog();
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
