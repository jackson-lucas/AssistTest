package app.testbuilder.br.com.TestBuilder.Model;

/**
 * Created by jacksonlima on 1/23/15.
 */
public class Substancia {
    private String nome, intervencao = "";
    private int resultado;

    public Substancia(String nome, int resultado) {
        this.nome = nome;
        this.resultado = resultado;

        if(nome.equals("bebidas alcoólicas")) {
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
}
