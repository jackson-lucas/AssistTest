package app.testbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.DAO.UsuarioDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;
import app.testbuilder.br.com.TestBuilder.Model.Usuario;

/**
 * Created by jcaf on 31/12/2014.
 */
public class CadastroUI extends ActionBarActivity {

    public boolean s1, s2;

    //Objetos do fragment
    private Button btnSalvar;
    private EditText etAvaliador, etCumpridor, etIdade;
    private RadioGroup rbGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etAvaliador = (EditText) findViewById(R.id.etAvaliador);
        etCumpridor = (EditText) findViewById(R.id.etCumpridor);
        etIdade = (EditText) findViewById(R.id.etIdade);
        rbGenero = (RadioGroup) findViewById(R.id.rdGenero);
        btnSalvar = (Button) findViewById(R.id.btnsalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            Usuario user = new Usuario();
            Teste test = new Teste();
            UsuarioDAO usuarioDao = new UsuarioDAO(getApplicationContext());
            TesteDAO testDao = new TesteDAO(getApplicationContext());
            AssistDAO aDao = new AssistDAO(getApplicationContext());
            Assist assist = new Assist();

            @Override
            public void onClick(View v) {
                String avaliador = etAvaliador.getText().toString();
                String cumpridor = etCumpridor.getText().toString();

                if (avaliador.isEmpty()) {
                    etAvaliador.setError("Informe um nome para Avaliador");
                    etAvaliador.requestFocus();
                } else if (cumpridor.isEmpty()) {
                    etCumpridor.setError("Informe um nome para Cumpridor");
                    etCumpridor.requestFocus();
                } else if (!isInteger(etIdade.getText().toString())) {
                    etIdade.setError("Informe uma idade para Cumpridor");
                    etIdade.requestFocus();
                } else {
                    try {
                        //Inicializando as variaveis
                        user.setAvaliador(avaliador);
                        user.setCumpridor(cumpridor);
                        user.setIdade(Integer.parseInt(etIdade.getText().toString()));
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

                        assist.setTeste_id(testDao.getLastId().getId());
                        aDao.inserir(assist);

                    } catch (SQLException e) {
                        trace("ErrorCadastro:" + e.getMessage());
                    }

                    Intent intent = new Intent(CadastroUI.this, ASSISTPergunta1.class);
                    intent.putExtra("ASSIST", assist);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException n) {
            return false;
        }
        return true;
    }
}
