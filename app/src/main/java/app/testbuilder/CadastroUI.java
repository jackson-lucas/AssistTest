package app.testbuilder;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import junit.framework.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.DAO.UsuarioDAO;
import app.testbuilder.br.com.TestBuilder.Model.Teste;
import app.testbuilder.br.com.TestBuilder.Model.Usuario;

/**
 * Created by jcaf on 31/12/2014.
 */
public class CadastroUI extends ActionBarActivity {

    //Objetos das classes
    public Usuario user = null;
    public Teste test = null;
    public UsuarioDAO usuarioDao = null;
    public TesteDAO testDao = null;
    public boolean s1, s2;

    //Objetos do fragment
    private Button btnSalvar;
    private EditText etAvaliador, etCumpridor, etIdade;
    private RadioGroup rbGenero;

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
                user = new Usuario();
                Teste test = new Teste();
                usuarioDao = new UsuarioDAO(getApplicationContext());
                testDao = new TesteDAO(getApplicationContext());

                try {

                    //Inicializando as variaveis
                    user.setAvaliador(etAvaliador.getText().toString());
                    user.setCumpridor(etCumpridor.getText().toString());
                    user.setIdade(Integer.valueOf(etIdade.getText().toString()));
                    int genero = rbGenero.getCheckedRadioButtonId();

                    //condição
                    if (genero == R.id.rbMasculino) {
                        user.setGenero("M");
                    } else {
                        user.setGenero("F");
                    }

                    //Formata da data
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    user.setDt_cadastro(sdf.format(new Date()));

                    //Cria Usuario
                    s1 = usuarioDao.inserir(user);

                    test.setUsuario(usuarioDao.getLastId().getId());
                    test.setTipo("1");
                    test.setStatus("1");

                    //Cria Teste
                    s2 = testDao.inserir(test); //Criando o teste;

                } catch (SQLException e) {
                    trace("ErrorCadastro:" + e.getMessage());
                }

                Intent intent = new Intent(CadastroUI.this, ASSISTPergunta1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }
}
