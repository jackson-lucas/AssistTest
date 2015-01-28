package app.testbuilder.br.com.TestBuilder.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

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
    private String dt_cadastro;

    public Usuario() {
    }

    public Usuario(String avaliador, String cumpridor, int idade, String genero, String dt_cadastro) {
        this.avaliador = avaliador;
        this.cumpridor = cumpridor;
        this.idade = idade;
        this.genero = genero;
        this.dt_cadastro = dt_cadastro;
    }

    //Insert;Update;Delete
    public Usuario(int id, String avaliador, String cumpridor, int idade, String genero, String dt_cadastro) {
        this.id = id;
        this.avaliador = avaliador;
        this.cumpridor = cumpridor;
        this.idade = idade;
        this.genero = genero;
        this.dt_cadastro = dt_cadastro;
    }

    public Usuario(JSONObject object){
        try {
            this.id = object.getInt("id");
            this.avaliador = object.getString("avaliador");
            this.cumpridor = object.getString("cumpridor");
            this.idade = object.getInt("idade");
            this.genero = object.getString("genero");
            this.dt_cadastro = object.getString("dt_cadastro");
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public void     setIdade(int idade) {
        this.idade = idade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(String dt_cadastro) {
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

    public String toJSONString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", getId());
            obj.put("avaliador", getAvaliador());
            obj.put("cumpridor", getCumpridor());
            obj.put("idade", getIdade());
            obj.put("genero", getGenero());
            obj.put("dt_cadastro", getDt_cadastro());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public JSONObject getAsJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("avaliador", avaliador);
            jsonObject.put("cumpridor", cumpridor);
            jsonObject.put("idade", idade);
            jsonObject.put("genero", genero);
            jsonObject.put("dt_cadastro", dt_cadastro);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static List<Usuario> fromJson(JSONArray jsonObjects) {
        List<Usuario> users = new LinkedList<Usuario>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                users.add(new Usuario(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}
