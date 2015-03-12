package app.testbuilder.br.com.TestBuilder.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Teste {

    /**
     * Labels table name
     */

    public static final String TABLE = "teste";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_USUARIO = "usuario_id";
    public static final String KEY_TIPO = "tipo";
    public static final String KEY_STATUS = "status";

    int id;
    int usuario;
    String tipo = "1";
    String status = "1";

    public Teste() {
    }

    public Teste(int id) {
        this.id = id;
    }

    public Teste(String tipo, String status) {
        this.tipo = tipo;
        this.status = status;
    }

    public Teste(int usuario, String tipo, String status) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.status = status;
    }

    public Teste(int id, int usuario, String tipo, String status) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject getAsJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", id);
            jsonObject.put("usuario_id", usuario);
            jsonObject.put("tipo", tipo);
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teste teste = (Teste) o;

        if (id != teste.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Teste{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", tipo='" + tipo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
