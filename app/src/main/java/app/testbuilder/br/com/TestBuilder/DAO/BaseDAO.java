package app.testbuilder.br.com.TestBuilder.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jcaf on 27/12/2014.
 */

public class BaseDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vemepa.db";
    public static final int DATABASE_VERSION = 1;

    /**
     * *************** TABELA_USUARIO ************************
     */
    public static final String CREATE_USUARIO = "CREATE TABLE usuario (id integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "avaliador VARCHAR(100) NOT NULL," +
            "cumpridor VARCHAR(100) NOT NULL," +
            "idade INTEGER NOT NULL," +
            "genero CHAR(1) NOT NULL," +
            "dt_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    public static final String CREATE_TESTE = "CREATE TABLE teste (id integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "usuario_id  integer NOT NULL," +
            "tipo        char(1) NOT NULL DEFAULT 1," +
            "status      char(1) NOT NULL DEFAULT 1);";

    public BaseDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USUARIO);
        db.execSQL(CREATE_TESTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USUARIO");
        db.execSQL("DROP TABLE IF EXISTS TESTE");
        onCreate(db);
    }
}
