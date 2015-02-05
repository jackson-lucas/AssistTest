package app.testbuilder.br.com.TestBuilder.Model;

import app.testbuilder.R;

/**
 * Created by jacksonlima on 1/23/15.
 */
public class Substancia {
    private String nome, intervencao = "";
    private int resultado;
    private int color;

    public Substancia(String nome, int resultado) {
        this.nome = nome;
        this.resultado = resultado;

        if(resultado == -1) {
            intervencao = "Não Completado";
            color = R.color.gray;
            return;
        }

        if(nome.equals("bebidas alcoólicas")) {
            if(resultado < 11) {
                intervencao = "Nenhuma intervenção";
                color = R.color.green;
            } else if(resultado < 27) {
                intervencao = "Intervenção breve";
                color = R.color.yellow;
            } else {
                intervencao = "Tratamento intensivo";
                color = R.color.red;
            }
        } else {
            if(resultado < 4) {
                intervencao = "Nenhuma intervenção";
                color = R.color.green;
            } else if(resultado < 27) {
                intervencao = "Intervenção breve";
                color = R.color.yellow;
            } else {
                intervencao = "Tratamento intensivo";
                color = R.color.red;
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public String getIntervencao() {
        return intervencao;
    }

    public int getResultado() {
        return resultado;
    }

    public int getColor() {
        return color;
    }
}
