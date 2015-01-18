package app.testbuilder.br.com.TestBuilder.Model;

/**
 * Created by jcaf on 27/12/2014.
 */
public class Assist {
    long id;
    String[] p;
    Teste teste;

    public Assist() {
    }

    public Assist(long id) {
        this.id = id;
    }

    public Assist(long id, String[] p, Teste teste) {
        this.id = id;
        this.p = p;
        this.teste = teste;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getP() {
        return p;
    }

    public void setP(String[] p) {
        this.p = p;
    }

    public Teste getTeste() {
        return teste;
    }

    public void setTeste(Teste teste) {
        this.teste = teste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assist assist = (Assist) o;

        if (id != assist.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
