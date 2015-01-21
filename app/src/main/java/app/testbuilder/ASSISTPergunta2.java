package app.testbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;

// TODO checar listas antes de ir para proxima activity ao pressionar botao
public class ASSISTPergunta2 extends ActionBarActivity {

    private int perguntaIndex; // Pergunta 2 de 7
    private boolean[] substanciasUsadas;
    private int lastRespostaIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // START Retrieve data from another activity
        Intent intent = getIntent();

        if(intent != null) {
            substanciasUsadas = intent.getBooleanArrayExtra("SUBSTANCIAS");
            perguntaIndex = intent.getIntExtra("QUESTION", 1);
            perguntaIndex += 1; // 2 - 7
        }

        // START create radio groups
        setContentView(R.layout.activity_assistpergunta2);
        Toast.makeText(getApplicationContext(), "Pergunta: " + perguntaIndex, Toast.LENGTH_SHORT);
        createQuestion(perguntaIndex);

        createConfirmButton();
    }

    public boolean checkIfAllRadiosGroupAreSelected() {
        for(int index = 0; index < substanciasUsadas.length; index++) {
            if(substanciasUsadas[index]) {
                View v = (View) findViewById(getListGroupId(index));
                RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup1);
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    // TODO update on SQLite ao finalizar
    private void createConfirmButton() {
        // Criar botão
        RelativeLayout context = (RelativeLayout) findViewById(R.id.layout_pergunta2);

        View viewConfirmButton = (View) findViewById(R.id.confirmButton);

        Button confirmButton = (Button) viewConfirmButton.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "BUTTON CLICKED");
                Intent intent;
                Assist assist = new Assist();
                AssistDAO aDao = new AssistDAO(getApplicationContext());
                Teste teste_id = null;

                if(checkIfAllRadiosGroupAreSelected()) {

                    if(perguntaIndex < 7) {
                        try {
                            teste_id = aDao.getLastId();
                            String respostas = getAnswer();
                            assist.setTeste_id(teste_id.getId());

                            switch (perguntaIndex) {
                                case 2:
                                    assist.setP2(respostas);
                                    break;
                                case 3:
                                    assist.setP3(respostas);
                                    break;
                                case 4:
                                    assist.setP4(respostas);
                                    break;
                                case 5:
                                    assist.setP5(respostas);
                                    break;
                                case 6:
                                    assist.setP6(respostas);
                                    break;
                            }
                            aDao.inserir(assist);
                        } catch (SQLException e) {
                            Log.e("ERROR:", e.getMessage());
                        }

                        intent = new Intent(ASSISTPergunta2.this, ASSISTPergunta2.class);
                        intent.putExtra("QUESTION", perguntaIndex);
                        intent.putExtra("SUBSTANCIAS", substanciasUsadas);

                        startActivity(intent);
                        finish();
                    } else {
                        intent = new Intent(ASSISTPergunta2.this, ASSISTPergunta3.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Existem questões não respondidas", Toast.LENGTH_LONG).show();
                }

            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.BELOW, getListGroupId(lastRespostaIndex));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);


        viewConfirmButton.setLayoutParams(params);
    }

    // Switchssauro
    private int parseRadioIdToAnswerValue(int perguntaIndex, int radioId) {
        if(radioId == R.id.radioButton1) {
            return 0;
        }

        switch (perguntaIndex) {
            case 2:
                switch (radioId) {
                    case R.id.radioButton2:
                        return 2;
                    case R.id.radioButton3:
                        return 3;
                    case R.id.radioButton4:
                        return 4;
                    case R.id.radioButton5:
                        return 6;
                }
                break;
            case 3:
                switch (radioId) {
                    case R.id.radioButton2:
                        return 3;
                    case R.id.radioButton3:
                        return 4;
                    case R.id.radioButton4:
                        return 5;
                    case R.id.radioButton5:
                        return 6;
                }
                break;
            case 4:
                switch (radioId) {
                    case R.id.radioButton2:
                        return 4;
                    case R.id.radioButton3:
                        return 5;
                    case R.id.radioButton4:
                        return 6;
                    case R.id.radioButton5:
                        return 7;
                }
                break;
            case 5:
                switch (radioId) {
                    case R.id.radioButton2:
                        return 5;
                    case R.id.radioButton3:
                        return 6;
                    case R.id.radioButton4:
                        return 7;
                    case R.id.radioButton5:
                        return 8;
                }
                break;
            case 6:
            case 7:
                switch (radioId) {
                    case R.id.radioButton2:
                        return 6;
                    case R.id.radioButton3:
                        return 3;
                }
                break;
        }
        // NUNCA deverá chegar aqui
        return -1;
    }

    private String getAnswer() {
        String resultado = "";

        for(int index = 0; index < substanciasUsadas.length; index++) {
            if(substanciasUsadas[index]) {
                View v = (View) findViewById(getListGroupId(index));
                RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup1);
                resultado += parseRadioIdToAnswerValue(perguntaIndex, radioGroup.getCheckedRadioButtonId());
            } else {
                resultado += "0";
            }
        }
        Toast.makeText(getApplicationContext(), "Resultado:" + resultado, Toast.LENGTH_SHORT).show();
        return resultado;
    }

    private int getListGroupId(int index) {
        int id;
        switch (index) {
            case 1:
                id = R.id.list_group1;
                break;
            case 2:
                id = R.id.list_group2;
                break;
            case 3:
                id = R.id.list_group3;
                break;
            case 4:
                id = R.id.list_group4;
                break;
            case 5:
                id = R.id.list_group5;
                break;
            case 6:
                id = R.id.list_group6;
                break;
            case 7:
                id = R.id.list_group7;
                break;
            case 8:
                id = R.id.list_group8;
                break;
            case 9:
                id = R.id.list_group9;
                break;
            case 10:
                id = R.id.list_group10;
                break;
            default:
                id = R.id.list_group0;
        }
        return id;
    }

    private int getRadioButtonId(int index) {
        int id;
        switch (index) {
            case 1:
                id = R.id.radioButton2;
                break;
            case 2:
                id = R.id.radioButton3;
                break;
            case 3:
                id = R.id.radioButton4;
                break;
            case 4:
                id = R.id.radioButton5;
                break;
            default:
                id = R.id.radioButton1;
        }
        return id;
    }

    private void createQuestion(int questionNumber) {
        int questionIndex = questionNumber - 1;
        String[] substancias = getResources().getStringArray(R.array.substancias);
        RelativeLayout context = (RelativeLayout) findViewById(R.id.layout_pergunta2);
        String[] respostas = perguntaIndex < 6 ?
                getResources().getStringArray(R.array.respostas_tipo1)
                : getResources().getStringArray(R.array.respostas_tipo2);

        TextView question = (TextView) context.findViewById(R.id.question);

        question.setText(getResources().getStringArray(R.array.perguntas_assist)[questionIndex]);

        for(int index = 0; index < substancias.length; index++) {
            if(substanciasUsadas[index]) {

                View list = perguntaIndex < 6 ?
                        getLayoutInflater().inflate(R.layout.list_group, null)
                        : getLayoutInflater().inflate(R.layout.list_group_type2, null);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                if(lastRespostaIndex == -1) {
                    params.addRule(RelativeLayout.BELOW, R.id.question);
                } else {
                    params.addRule(RelativeLayout.BELOW, getListGroupId(lastRespostaIndex));
                }
                lastRespostaIndex = index;

                list.setId(getListGroupId(index));
                list.setLayoutParams(params);

                TextView header = (TextView) list.findViewById(R.id.radioGroupHeader);
                header.setText(substancias[index]);

                RadioGroup radioGroup = (RadioGroup) list.findViewById(R.id.radioGroup1);

                for(int index2 = 0; index2 < respostas.length; index2++) {
                    Log.d("INDEX2", index2+"");
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(getRadioButtonId(index2));

                    radioButton.setText(respostas[index2]);
                }

                context.addView(list);
            }
        }


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
        if (id == R.id.action_bar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
