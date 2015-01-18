package app.testbuilder.br.com.TestBuilder.Model;

import java.util.Date;

/**
 * Created by jcaf on 27/12/2014.
 */
public class Usuario {

    // Labels table name
    public static final String TABLE = "usuario";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_avaliador = "avaliador";
    public static final String KEY_cumpridor = "cumpridor";
    public static final String KEY_idade = "idade";
    public static final String KEY_genero = "genero";
    public static final String KEY_dt_cadastro = "dt_cadastro";

    private int id;
    private String avaliador;
    private String cumpridor;
    private int idade;
    private String genero;
    private Date dt_cadastro;

    public Usuario() {
    }

    public Usuario(String avaliador, String cumpridor, int idade, String genero, Date dt_cadastro) {
        this.avaliador = avaliador;
        this.cumpridor = cumpridor;
        this.idade = idade;
        this.genero = genero;
        this.dt_cadastro = dt_cadastro;
    }

    //Insert;Update;Delete
    public Usuario(int id, String avaliador, String cumpridor, int idade, String genero, Date dt_cadastro) {
        this.id = id;
        this.avaliador = avaliador;
        this.cumpridor = cumpridor;
        this.idade = idade;
        this.genero = genero;
        this.dt_cadastro = dt_cadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(String avaliador) {
        this.avaliador = avaliador;
    }

    public String getCumpridor() {
        return cumpridor;
    }

    public void setCumpridor(String cumpridor) {
        this.cumpridor = cumpridor;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        if (id != usuario.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", avaliador='" + avaliador + '\'' +
                ", cumpridor='" + cumpridor + '\'' +
                ", idade=" + idade +
                ", genero='" + genero + '\'' +
                ", dt_cadastro=" + dt_cadastro +
                '}';
    }

}
