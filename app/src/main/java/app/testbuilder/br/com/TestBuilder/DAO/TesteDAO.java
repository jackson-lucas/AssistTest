package app.testbuilder.br.com.TestBuilder.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.Model.Teste;


/**
 * Created by jcaf on 28/12/2014.
 */
public class TesteDAO {

    /* SQL INSERT, DELETE e UPDATE. */
    private static final String SQL_SELECT_ALL = "SELECT * FROM teste ORDER BY avaliador";
    private static final String SQL_SELECT_NOME = " SELECT usuario.avaliador FROM teste INNER JOIN usuario ON (teste.usuario_id = usuario.id) WHERE teste.id = ?";
    private static final String SQL_SELECT_ID = "SELECT * FROM teste WHERE id = ?";



    private BaseDAO dbHelper;
    private SQLiteDatabase db;

    /* Colunas base para resgatar dados */
    private String[] columns = {Teste.KEY_ID, Teste.KEY_USUARIO, Teste.KEY_TIPO, Teste.KEY_STATUS};

    public TesteDAO(Context context) {
        dbHelper = new BaseDAO(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean save(Teste t) {
        Log.d("Adicionado", t.toString());
        ContentValues values = new ContentValues();
        values.put(t.KEY_USUARIO, t.getUsuario());
        values.put(t.KEY_TIPO, t.getTipo());
        values.put(t.KEY_STATUS, t.getStatus());
        return (db.insert(t.TABLE, null, values)> 0) ;
    }

    public boolean delete(int id) {
        //Usuario u = new Usuario();
        //Log.d("Deletado -->", u.toString());
        return (db.delete(Teste.TABLE, "id ='" + id + "'", null) > 0);
    }

    public List<Teste> get(String nome) {
        List<Teste> list = new ArrayList<Teste>();
        Cursor cursor = db.rawQuery(SQL_SELECT_NOME, new String[]{String.valueOf(nome)});
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Teste test = new Teste();
                test.setId(cursor.getInt(0));
                test.setUsuario(cursor.getInt(1));
                test.setTipo(cursor.getString(3));
                test.setStatus(cursor.getString(4));
                list.add(test);
            }
        }
        return (list);
    }

    public List<Teste> getAll() {
        List<Teste> list = new LinkedList<Teste>();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Teste test = new Teste();
                test.setId(cursor.getInt(0));
                test.setUsuario(cursor.getInt(1));
                test.setTipo(cursor.getString(3));
                test.setStatus(cursor.getString(4));
                list.add(test);
            }
        }
        Log.d("getAll()",list.toString());
        return (list);
    }

    public Teste getTesteById(int id) {
        Cursor cursor = db.rawQuery(SQL_SELECT_ID, new String[]{String.valueOf(id)});
        Teste test = new Teste();
        if (cursor.moveToFirst()) {
            test.setId(cursor.getInt(0));
            test.setUsuario(cursor.getInt(1));
            test.setTipo(cursor.getString(3));
            test.setStatus(cursor.getString(4));
        }
        return test;
    }
}
