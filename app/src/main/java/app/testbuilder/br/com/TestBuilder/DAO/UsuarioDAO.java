package app.testbuilder.br.com.TestBuilder.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.Model.Usuario;

/**
 * Created by jcaf on 28/12/2014.
 */

@SuppressWarnings("UnusedAssignment")
public class UsuarioDAO {

    /* SQL INSERT, DELETE e UPDATE. */
    private static final String SQL_SELECT_ALL = "SELECT * FROM usuario ORDER BY id";
    private static final String SQL_SELECT_NOME = "SELECT * FROM usuario WHERE avaliador = ?";
    private static final String SQL_SELECT_ID = "SELECT * FROM usuario WHERE id = ?";
    private static final String SQL_LAST_ID = "SELECT MAX(id) FROM usuario";

    private BaseDAO dbHelper;
    private SQLiteDatabase db;

    /* Colunas base para resgatar dados */
    private String[] columns = {Usuario.KEY_ID, Usuario.KEY_avaliador, Usuario.KEY_cumpridor, Usuario.KEY_idade, Usuario.KEY_genero, Usuario.KEY_dt_cadastro};

    public UsuarioDAO(Context context) {
        dbHelper = new BaseDAO(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean inserir(Usuario u) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(u.KEY_avaliador, u.getAvaliador());
        values.put(u.KEY_cumpridor, u.getCumpridor());
        values.put(u.KEY_idade, u.getIdade());
        values.put(u.KEY_genero, u.getGenero());
//        values.put(u.KEY_dt_cadastro, getDateTime().getTime());
        values.put(u.KEY_dt_cadastro, u.getDt_cadastro());
        return (db.insert(u.TABLE, null, values) > 0);
    }

    public boolean update(Usuario u) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(u.KEY_avaliador, u.getAvaliador());
        values.put(u.KEY_cumpridor, u.getCumpridor());
        values.put(u.KEY_idade, u.getIdade());
        values.put(u.KEY_genero, u.getGenero());
        values.put(u.KEY_dt_cadastro, u.getDt_cadastro());
        String where = "id = ?";
        String[] whereArgs = {Integer.toString(u.getId())};
        return (db.update(Usuario.TABLE, values, where, whereArgs) > 0);
    }

    public boolean delete(int id) {
        return (db.delete(Usuario.TABLE, "id ='" + id + "'", null) > 0);
    }

    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> list = new LinkedList<Usuario>();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Usuario user = populaUsuario(cursor);
                list.add(user);
            }
        }
        Log.i("DEBUG:", list.toString());
        return (list);
    }

    //Converter o Cursor de dados no objeto POJO Usuario
    private Usuario populaUsuario(Cursor cursor) throws SQLException {
        Usuario toReturn = new Usuario();
        toReturn.setId(cursor.getInt(0));
        toReturn.setAvaliador(cursor.getString(1));
        toReturn.setCumpridor(cursor.getString(2));
        toReturn.setIdade(cursor.getInt(3));
        toReturn.setGenero(cursor.getString(4));
        toReturn.setDt_cadastro(cursor.getString(5));
        return toReturn;
    }

/*
    public List<Usuario> get(String nome) {
        List<Usuario> list = new ArrayList<Usuario>();
        Cursor cursor = db.rawQuery(SQL_SELECT_NOME, new String[]{String.valueOf(nome)});
        Usuario user = null;
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                user = new Usuario();
                user.setId(cursor.getInt(0));
                user.setAvaliador(cursor.getString(1));
                user.setCumpridor(cursor.getString(2));
                user.setIdade(cursor.getInt(3));
                user.setGenero(cursor.getString(4));
                user.setDt_cadastro(new Date(cursor.getLong(5)));
                list.add(user);
            }
        }
        return (list);
    }

    public Usuario getUsuarioById(int id) {
        Cursor cursor = db.rawQuery(SQL_SELECT_ID, new String[]{String.valueOf(id)});
        Usuario user = null;
        if (cursor.moveToFirst()) {
            user = new Usuario();
            user.setId(cursor.getInt(0));
            user.setAvaliador(cursor.getString(1));
            user.setCumpridor(cursor.getString(2));
            user.setIdade(cursor.getInt(3));
            user.setGenero(cursor.getString(4));
            user.setDt_cadastro(new Date(cursor.getLong(5)));
        }
        return user;
    }
    */

    public Usuario getLastId() throws SQLException {
        Cursor cursor = db.rawQuery(SQL_LAST_ID, null);
        Usuario user = null;
        if (cursor.moveToFirst()) {
            user = new Usuario();
            user.setId(cursor.getInt(0));
        }
        return user;
    }
}