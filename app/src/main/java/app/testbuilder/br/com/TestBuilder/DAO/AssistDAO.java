package app.testbuilder.br.com.TestBuilder.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import app.testbuilder.br.com.TestBuilder.Model.Assist;
import app.testbuilder.br.com.TestBuilder.Model.Teste;


/**
 * Created by jcaf on 28/12/2014.
 */
public class AssistDAO {

    /* SQL INSERT, DELETE e UPDATE. */
    private static final String SQL_SELECT_ALL = "SELECT * FROM assist ORDER BY teste_id";
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

        String where = "id = ?";

        String[] whereArgs = {Integer.toString(a.getId())};

        return (db.update(Assist.TABLE, values, where, whereArgs) > 0);
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


}
