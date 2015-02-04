package app.testbuilder.br.com.TestBuilder.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import app.testbuilder.br.com.TestBuilder.Model.Assist;


/**
 * Created by jcaf on 28/12/2014.
 */
public class AssistDAO {

    /* SQL INSERT, DELETE e UPDATE. */
    private static final String SQL_SELECT_ALL = "SELECT * FROM assist ORDER BY id";
    private static final String SQL_SELECT_ID = "SELECT * FROM assist WHERE id = ?";
    private static final String SQL_LAST_ID = "SELECT MAX(id) FROM assist";

    private BaseDAO dbHelper;
    private SQLiteDatabase db;

    /* Colunas base para resgatar dados */
    private String[] columns = {Assist.KEY_ID, Assist.KEY_TESTE, Assist.KEY_P1, Assist.KEY_P2, Assist.KEY_P3,
            Assist.KEY_P4, Assist.KEY_P5, Assist.KEY_P6, Assist.KEY_P7, Assist.KEY_P8};

    public AssistDAO(Context context) {
        dbHelper = new BaseDAO(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean inserir(Assist a) throws SQLException  {
        ContentValues values = new ContentValues();

        values.put(a.KEY_TESTE, a.getTeste_id());
        values.put(a.KEY_P1, a.getP1());
        values.put(a.KEY_P2, a.getP2());
        values.put(a.KEY_P3, a.getP3());
        values.put(a.KEY_P4, a.getP4());
        values.put(a.KEY_P5, a.getP5());
        values.put(a.KEY_P6, a.getP6());
        values.put(a.KEY_P7, a.getP7());
        values.put(a.KEY_P8, a.getP8());

        return (db.insert(a.TABLE, null, values) > 0);
    }

    public boolean update(Assist a) throws SQLException {
        int id = a.getId();
        ContentValues values = new ContentValues();

        values.put(a.KEY_TESTE, a.getTeste_id());
        values.put(a.KEY_P1, a.getP1());
        values.put(a.KEY_P2, a.getP2());
        values.put(a.KEY_P3, a.getP3());
        values.put(a.KEY_P4, a.getP4());
        values.put(a.KEY_P5, a.getP5());
        values.put(a.KEY_P6, a.getP6());
        values.put(a.KEY_P7, a.getP7());
        values.put(a.KEY_P8, a.getP8());

        return (db.update(Assist.TABLE, values, Assist.KEY_ID + " = " + id, null) > 0);
    }

    public boolean delete(int id) {
        return (db.delete(Assist.TABLE, "id ='" + id + "'", null) > 0);
    }

    public Assist getLastId() throws SQLException{
        Cursor cursor = db.rawQuery(SQL_LAST_ID, null);
        Assist toReturn = null;
        if (cursor.moveToFirst()) {
            toReturn = new Assist();
            toReturn.setId(cursor.getInt(0));
        }
        return toReturn;
    }

    public String booleanToString(boolean[] array) {
        String inicial = "";
        for (int index = 0; index < array.length; index++) {
            inicial += array[index] ? 1 : 0;
        }
        return inicial;
    }

    public List<Assist> getAllAssist() throws SQLException {
        List<Assist> list = new LinkedList<Assist>();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            Assist assist = populaAssist(cursor);
            list.add(assist);

            while (cursor.moveToNext()) {
                assist = populaAssist(cursor);
                list.add(assist);
            }
        }
        Log.d("getAll()", list.toString());
        return (list);
    }

    //Converter o Cursor de dados no objeto POJO Assist
    private Assist populaAssist(Cursor cursor) throws SQLException {
        final Assist toReturn = new Assist();
        toReturn.setId(cursor.getInt(0));
        toReturn.setTeste_id(cursor.getInt(1));
        toReturn.setP1(cursor.getString(2));
        toReturn.setP2(cursor.getString(3));
        toReturn.setP3(cursor.getString(4));
        toReturn.setP4(cursor.getString(5));
        toReturn.setP5(cursor.getString(6));
        toReturn.setP6(cursor.getString(7));
        toReturn.setP7(cursor.getString(8));
        toReturn.setP8(cursor.getString(9));
        return toReturn;
    }
}
