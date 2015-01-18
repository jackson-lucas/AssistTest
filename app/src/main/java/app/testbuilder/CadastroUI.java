package app.testbuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import app.testbuilder.br.com.TestBuilder.DAO.UsuarioDAO;
import app.testbuilder.br.com.TestBuilder.Model.Usuario;

/**
 * Created by jcaf on 31/12/2014.
 */
public class CadastroUI extends Activity {

    //Objetos do fragment
    private Button btnSalvar;
    private EditText etAvaliador, etCumpridor, etIdade;
    private RadioGroup rbGenero;

    //Objetos das classes
    private Usuario user = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        etAvaliador = (EditText) findViewById(R.id.etAvaliador);
        etCumpridor = (EditText) findViewById(R.id.etCumpridor);
        etIdade = (EditText) findViewById(R.id.etIdade);
        rbGenero = (RadioGroup) findViewById(R.id.rdGenero);
        btnSalvar = (Button) findViewById(R.id.btnsalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avaliador = etAvaliador.getText().toString();

                Intent intent = new Intent(CadastroUI.this, ASSISTPergunta1.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void salvar(View view) {



    }


    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }




}
