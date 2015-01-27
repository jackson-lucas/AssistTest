package app.testbuilder.br.com.TestBuilder.Utilities;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import app.testbuilder.br.com.TestBuilder.DAO.AssistDAO;
import app.testbuilder.br.com.TestBuilder.DAO.TesteDAO;
import app.testbuilder.br.com.TestBuilder.DAO.UsuarioDAO;
import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;
import app.testbuilder.br.com.TestBuilder.Model.Usuario;

public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    AssistDAO assistDAO;
    UsuarioDAO usuarioDAO;
    TesteDAO testeDAO;

    // constructor
    public JSONParser(Context context) {
        assistDAO = new AssistDAO(context);
        testeDAO = new TesteDAO(context);
        usuarioDAO = new UsuarioDAO(context);
    }
    
    public JSONArray getAllAssistAsJson() {
        List<Assist> assistList;
        JSONArray jsonArray = new JSONArray();

        try {
            assistList = assistDAO.getAllAssist();

            for(Assist assist : assistList) {
                jsonArray.put(assist.getAsJson());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getAllTestesAsJson() {
        JSONArray jsonArray = new JSONArray();
        List<Teste> testeList;
        try {
            testeList = testeDAO.getAllTestes();

            for(Teste teste : testeList) {
                jsonArray.put(teste.getAsJson());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray getAllUsuariosAsJson() {
        JSONArray jsonArray = new JSONArray();
        List<Usuario> usuarioList;

        try {
            usuarioList = usuarioDAO.getAllUsuarios();

            for(Usuario usuario : usuarioList) {
                jsonArray.put(usuario.getAsJson());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONObject getAll() {
        JSONArray assistJsonArray = getAllAssistAsJson();
        JSONArray testeJsonArray = getAllTestesAsJson();
        JSONArray usuarioJsonArray = getAllUsuariosAsJson();
        JSONObject allData = new JSONObject();

        try {
            allData.put("assist", assistJsonArray);
            allData.put("teste", testeJsonArray);
            allData.put("usuario", usuarioJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allData;
    }


}
