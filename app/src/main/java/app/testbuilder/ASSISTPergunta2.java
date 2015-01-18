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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// TODO checar listas antes de ir para proxima activity ao pressionar botao
public class ASSISTPergunta2 extends ActionBarActivity {

    private int perguntaIndex; // Pergunta 2 de 8
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
        }

        // START create radio groups
        setContentView(R.layout.activity_assistpergunta2);

        createQuestion(perguntaIndex);

        createConfirmButton();
    }

    // TODO update on SQLite ao finalizar
    private void createConfirmButton() {
        // Criar botão
        RelativeLayout context = (RelativeLayout) findViewById(R.id.layout_pergunta2);

        View viewConfirmButton = (View) getLayoutInflater().inflate(R.layout.confirm_button, null);

        Button confirmButton = (Button) viewConfirmButton.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "BUTTON CLICKED");
                Intent intent;

                if(perguntaIndex+1 < 7) {

                    intent = new Intent(ASSISTPergunta2.this, ASSISTPergunta2.class);
                    intent.putExtra("QUESTION", perguntaIndex+1);
                    intent.putExtra("SUBSTANCIAS", substanciasUsadas);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(ASSISTPergunta2.this, ASSISTPergunta3.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.BELOW, getListGroupId(lastRespostaIndex));

        viewConfirmButton.setLayoutParams(params);

        context.addView(viewConfirmButton);
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

    private void createQuestion(int questionIndex) {
        String[] substancias = getResources().getStringArray(R.array.substancias);
        RelativeLayout context = (RelativeLayout) findViewById(R.id.layout_pergunta2);
        String[] respostas = perguntaIndex < 5 ?
                getResources().getStringArray(R.array.respostas_tipo1)
                : getResources().getStringArray(R.array.respostas_tipo2);

        TextView question = (TextView) context.findViewById(R.id.question);

        question.setText(getResources().getStringArray(R.array.perguntas_assist)[questionIndex]);

        for(int index = 0; index < substancias.length; index++) {
            if(substanciasUsadas[index]) {

                View list = perguntaIndex < 5 ?
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
