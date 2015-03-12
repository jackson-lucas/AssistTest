package app.testbuilder.br.com.TestBuilder.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.Model.Teste;


public class TesteDAO {

    /* SQL INSERT, DELETE e UPDATE. */
    private static final String SQL_SELECT_ALL = "SELECT * FROM teste ORDER BY id";
    private static final String SQL_SELECT_NOME = " SELECT usuario.avaliador FROM teste INNER JOIN usuario ON (teste.usuario_id = usuario.id) WHERE teste.id = ?";
    private static final String SQL_SELECT_ID = "SELECT * FROM teste WHERE id = ?";
    private static final String SQL_LAST_ID = "SELECT MAX(id) FROM teste";

    private BaseDAO dbHelper;
    private SQLiteDatabase db;

    /**
     *  Colunas base para resgatar dados
     */
    private String[] columns = {Teste.KEY_ID, Teste.KEY_USUARIO, Teste.KEY_TIPO, Teste.KEY_STATUS};

    public TesteDAO(Context context)  {
        dbHelper = new BaseDAO(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean inserir(Teste t) throws SQLException {
        Log.d("Adicionado", t.toString());
        ContentValues values = new ContentValues();
        values.put(t.KEY_USUARIO, t.getUsuario());
        values.put(t.KEY_TIPO, t.getTipo());
        values.put(t.KEY_STATUS, t.getStatus());
        return (db.insert(t.TABLE, null, values)> 0) ;
    }

    public boolean update(Teste t) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(t.KEY_USUARIO, t.getUsuario());
        values.put(t.KEY_TIPO, t.getTipo());
        values.put(t.KEY_STATUS, t.getStatus());
        String where = "id = ?";
        String[] whereArgs = {Integer.toString(t.getId())};
        return (db.update(t.TABLE, values, where, whereArgs) > 0);
    }

    public boolean delete(int id) {
        return (db.delete(Teste.TABLE, "id ='" + id + "'", null) > 0);
    }

    public List<Teste> get(String nome) throws SQLException {
        List<Teste> list = new ArrayList<Teste>();
        Cursor cursor = db.rawQuery(SQL_SELECT_NOME, new String[]{String.valueOf(nome)});
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Teste test = populaTeste(cursor);
                list.add(test);
            }
        }
        return (list);
    }

    public List<Teste> getAllTestes()  throws SQLException {
        List<Teste> list = new LinkedList<Teste>();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            Teste test = populaTeste(cursor);
            list.add(test);

            while (cursor.moveToNext()) {
                test = populaTeste(cursor);
                list.add(test);
            }
        }
        Log.d("getAll()",list.toString());
        return (list);
    }

    public Teste getTesteById(int id) throws SQLException{
        Cursor cursor = db.rawQuery(SQL_SELECT_ID, new String[]{String.valueOf(id)});
        Teste test = new Teste();
        if (cursor.moveToFirst()) {
            test.setId(cursor.getInt(0));
            test.setUsuario(cursor.getInt(1));
            test.setTipo(cursor.getString(2));
            test.setStatus(cursor.getString(3));
        }
        return test;
    }

    public Teste getLastId() throws SQLException{
        Cursor cursor = db.rawQuery(SQL_LAST_ID, null);
        Teste test = null;
        if (cursor.moveToFirst()) {
            test = new Teste();
            test.setId(cursor.getInt(0));
        }
        return test;
    }

    /**
     * Converter o Cursor de dados no objeto POJO Teste
     */
    private Teste populaTeste(Cursor cursor) throws SQLException {
        final Teste toReturn = new Teste();
        toReturn.setId(cursor.getInt(0));
        toReturn.setUsuario(cursor.getInt(1));
        toReturn.setTipo(cursor.getString(2));
        toReturn.setStatus(cursor.getString(3));
        return toReturn;
    }

}
