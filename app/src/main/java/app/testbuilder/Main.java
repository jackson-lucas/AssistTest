package app.testbuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.Utilities.JSONParser;

/* TODO Falta deletar os dados BD quando for enviado com sucesso para servidor
    (não fazer antes do servidor estiver funcionando, para não ficar criando manualmente os dados p/ teste)
 */
public class Main extends ActionBarActivity {

    private final String URL = "http://www.testbuilder.com.br/testbuilder/post.php";
    Button btnIniciar;
    public JSONParser jsonParser;
    ProgressDialog progressDialog;
    AlertDialog.Builder alert;
    boolean venhoDeResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // START Retrieve data from another activity
        Intent intent = getIntent();

        if(intent != null) {
            venhoDeResultado = intent.getBooleanExtra("RESULTADO", false);
        }

        btnIniciar = (Button) findViewById(R.id.btnTeste);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, CadastroUI.class);
                startActivity(i);
                finish();
            }
        });

        jsonParser = new JSONParser(this);

        createProgressDialog();

        alert = new AlertDialog.Builder(this);

        alert.setTitle("Enviar Dados?");
        alert.setMessage("Existem dados para enviar, deseja enviá-los?");

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                // call AsynTask to perform network operation on separate thread
                if(isConnected()) {
                    progressDialog.show();
                    //new HttpAsyncTask().execute("http://www.mocky.io/v2/54c7b3a41f6a71fe111514c9");
                    new HttpAsyncTask().execute(URL);
                } else {
                    Toast.makeText(getBaseContext(), "Não conectado a internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alert.create();

        if(venhoDeResultado) {
            alert.show();
        }
    }

    public void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Enviando dados...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*
            BUG: Existe um erro ao tentar utilizar assistDAO.getAllAssist()
            java.lang.IllegalStateException: Couldn't read row 0, col 10 from CursorWindow.  Make sure the Cursor is initialized correctly before accessing data from it.
            AsssitDAO.java:126
            AsssitDAO.java:101

            Troquei para testeDAO pois estou fazendo outros testes e preciso do Main funcionando
         */
        TesteDAO testeDAO = new TesteDAO(this);
        try {
            MenuItem menuItem = menu.findItem(R.id.action_send_data);
            if (testeDAO.getAllTestes().size() == 0) {
                menuItem.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here, The action bar will automatically handle clicks on the
        Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();

        if (id == R.id.action_send_data) {
            // call AsynTask to perform network operation on separate thread
            if(isConnected()) {
                progressDialog.show();
                //new HttpAsyncTask().execute("http://www.mocky.io/v2/54c7b3a41f6a71fe111514c9");
                new HttpAsyncTask().execute(URL);
            } else {
                Toast.makeText(getBaseContext(), "Não conectado a internet!", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    // TODO(Ideal) server deve retornar uma mensagem de erro quando: JSON incompativel. E msg de sucesso quando compatível
    // TODO(a ser feito) server deve retornar uma mensagem de recebido. Erros em JSON devem ser verificados por nós
    // iso-8859-1 o servidor está lendo assim? e não UTF-8?
    // TODO fazer JSON para enviar todos os dados do banco de dados
    // Methods for HTTPRequest
    public static String POST(String url, JSONParser jsonParser){
        InputStream inputStream = null;
        String result = "";

        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);


            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                String usuarios = jsonParser.getAllUsuariosAsJson().toString();
                String testes = jsonParser.getAllTestesAsJson().toString();
                String assist = jsonParser.getAllAssistAsJson().toString();

                Log.i("USUARIOS", usuarios);
                Log.i("TESTES", testes);
                Log.i("ASSIST", assist);

                params.add(new BasicNameValuePair("usuarios", usuarios));
                params.add(new BasicNameValuePair("testes", testes));
                params.add(new BasicNameValuePair("assist", assist));

                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse response = httpclient.execute(httpPost);

                inputStream = response.getEntity().getContent();

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Resultado Nulo";

        } catch (Exception e) {
            Log.i("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0],jsonParser);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.i("HTTP REQUEST RESULTADO", result);

            progressDialog.dismiss();

            if(result.equals("OK")) {
                Toast.makeText(getBaseContext(), "Dados enviados com sucesso!", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getBaseContext(), "Erro ao tentar enviar dados!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        Log.i("HTTP REQUEST RESULTADO", "");
        while((line = bufferedReader.readLine()) != null) {
            result = line;
            Log.i("", result);
        }
        Log.i("HTTP REQUEST RESULTADO FIM", "");

        inputStream.close();
        return result;
    }
}
