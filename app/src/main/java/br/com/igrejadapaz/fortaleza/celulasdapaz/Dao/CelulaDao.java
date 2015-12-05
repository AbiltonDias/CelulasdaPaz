package br.com.igrejadapaz.fortaleza.celulasdapaz.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import br.com.igrejadapaz.fortaleza.celulasdapaz.Bean.CelulaBean;

/**
 * Created by Samuel Santiago on 05/12/2015.
 */
public class CelulaDao extends SQLiteOpenHelper{
    public static final int VERSAO = 1;
    public static final String TABELA = "celula";
    public static final String DATABASE = "CELULA.DB";

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public CelulaDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Create a helper object to create, open, and/or manage a database.
     * The database is not actually created or opened until one of
     * {@link #getWritableDatabase} or {@link #getReadableDatabase} is called.
     * <p>
     * <p>Accepts input param: a concrete instance of {@link DatabaseErrorHandler} to be
     * used to handle corruption when sqlite reports database corruption.</p>
     *
     * @param context      to use to open or create the database
     * @param name         of the database file, or null for an in-memory database
     * @param factory      to use for creating cursor objects, or null for the default
     * @param version      number of the database (starting at 1); if the database is older,
     *                     {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                     newer, {@link #onDowngrade} will be used to downgrade the database
     * @param errorHandler the {@link DatabaseErrorHandler} to be used when sqlite reports database
     */
    public CelulaDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public CelulaDao(Context context){
        super(context, DATABASE, null, VERSAO);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA
                + "(id INTEGER PRIMARY KEY, "
                + "nome TEXT, "
                + "endereco TEXT, "
                + "liderNome TEXT, "
                + "telefoneInformacao TEXT, "
                + "diaHora TEXT, "
                + "latitude DOUBLE, "
                + "longitude DOUBLE,"
                + "semanaID INTEGER, "
                + "tipoID INTEGER, "
                + "redeID INTEGER)";
        db.execSQL(sql);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }


    public void reset() {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        onCreate(db);
        db.close();
    }

    public void inserirRegistro(CelulaBean celula) {
        ContentValues valores = new ContentValues();

        valores.put("nome", celula.getNome());
        valores.put("endereco", celula.getEndereco());
        valores.put("liderNome", celula.getLiderNome());
        valores.put("telefoneInformacao", celula.getTelefoneInformacao());
        valores.put("diaHora", celula.getDiaHora());
        valores.put("latitude", celula.getLatitude());
        valores.put("longitude", celula.getLongitude());
        valores.put("semanaID", celula.getSemanaID());
        valores.put("tipoID", celula.getTipoID());
        valores.put("redeID", celula.getRedeID());

        getWritableDatabase().insert(TABELA, null, valores);
    }

    public void removerRegistro(CelulaBean celula) {
        String[] args = {Integer.toString(celula.getId())};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    public void editarRegistro(CelulaBean celula) {
        ContentValues valores = new ContentValues();

        valores.put("nome", celula.getNome());
        valores.put("endereco", celula.getEndereco());
        valores.put("liderNome", celula.getLiderNome());
        valores.put("telefoneInformacao", celula.getTelefoneInformacao());
        valores.put("diaHora", celula.getDiaHora());
        valores.put("latitude", celula.getLatitude());
        valores.put("longitude", celula.getLongitude());
        valores.put("semanaID", celula.getSemanaID());
        valores.put("tipoID", celula.getTipoID());
        valores.put("redeID", celula.getRedeID());

        String[] args = new String[]{Integer.toString(celula.getId())};

        getWritableDatabase().update(TABELA, valores, "id=?", args);
    }

    public CelulaBean getCelula(int id) {
        String sql = "Select * from celula where id =" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        CelulaBean celula = new CelulaBean();

        try {
            while (cursor.moveToNext()) {
                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }

        return celula;
    }

    public ArrayList<CelulaBean> consultarCelulas() {

        ArrayList<CelulaBean> celulaList = new ArrayList<CelulaBean>();
        String sql = "Select * from celula order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                celulaList.add(celula);
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
        return celulaList;
    }

    public ArrayList<CelulaBean> consultarCelulasTipoId(int tipoID) {

        ArrayList<CelulaBean> celulaList = new ArrayList<CelulaBean>();
        String sql = "Select * from celula where tipoID =" + tipoID + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                celulaList.add(celula);
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
        return celulaList;
    }

    public ArrayList<CelulaBean> consultarCelulasTipoIdSemanaId(int tipoID, int semanaID) {

        ArrayList<CelulaBean> celulaList = new ArrayList<CelulaBean>();
        String sql = "Select * from celula where semanaID =" + semanaID + " and tipoID =" + tipoID + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                celulaList.add(celula);
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
        return celulaList;
    }

    public ArrayList<CelulaBean> consultarCelulasSemana() {

        ArrayList<CelulaBean> celulaList = new ArrayList<CelulaBean>();
        String sql = "Select * from celula where semanaID !=7 order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                celulaList.add(celula);
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
        return celulaList;
    }

    public ArrayList<CelulaBean> consultarCelulasSemanaTipoId(int tipoID) {

        ArrayList<CelulaBean> celulaList = new ArrayList<CelulaBean>();
        String sql = "Select * from celula where semanaID <7" + " and tipoID =" + tipoID + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                celulaList.add(celula);
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
        return celulaList;
    }

    public ArrayList<CelulaBean> consultarCelulasSabadoTipoId(int tipoID) {

        ArrayList<CelulaBean> celulaList = new ArrayList<CelulaBean>();
        String sql = "Select * from celula where semanaID =7" + " and tipoID =" + tipoID + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                celulaList.add(celula);
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
        return celulaList;
    }

    public ArrayList<CelulaBean> consultarCelulasSabado() {

        ArrayList<CelulaBean> celulaList = new ArrayList<CelulaBean>();
        String sql = "Select * from celula where semanaID =7 order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                celulaList.add(celula);
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
        return celulaList;
    }

    public void getMarkers(GoogleMap mMap) {

        String sql = "Select * from celula order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                mMap.addMarker(celula.getMarkerOptions());
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
    }

    public void getMarkersID(GoogleMap mMap, int id) {

        String sql = "Select * from celula where id = " + id + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                mMap.addMarker(celula.getMarkerOptions());
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
    }

    public void getMarkersTipoId(GoogleMap mMap, int tipoID) {

        String sql = "Select * from celula where tipoID =" + tipoID + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                mMap.addMarker(celula.getMarkerOptions());
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
    }

    public void getMarkersSemana(GoogleMap mMap) {

        String sql = "Select * from celula where semanaID !=7 order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                mMap.addMarker(celula.getMarkerOptions());
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
    }

    public void getMarkersSemanaTipoId(GoogleMap mMap, int tipoID) {

        String sql = "Select * from celula where semanaID !=7 and tipoID = " + tipoID + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                mMap.addMarker(celula.getMarkerOptions());
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
    }

    public void getMarkersSabado(GoogleMap mMap) {

        String sql = "Select * from celula where semanaID =7 order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                mMap.addMarker(celula.getMarkerOptions());
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
    }

    public void getMarkersSabadoTipoId(GoogleMap mMap, int tipoID) {

        String sql = "Select * from celula where semanaID =7 and tipoID =" + tipoID + " order by nome";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                CelulaBean celula = new CelulaBean();

                celula.setId(cursor.getInt(0));
                celula.setNome(cursor.getString(1));
                celula.setEndereco(cursor.getString(2));
                celula.setLiderNome(cursor.getString(3));
                celula.setTelefoneInformacao(cursor.getString(4));
                celula.setDiaHora(cursor.getString(5));
                celula.setLatitude(cursor.getDouble(6));
                celula.setLongitude(cursor.getDouble(7));
                celula.setSemanaID(cursor.getInt(8));
                celula.setTipoID(cursor.getInt(9));
                celula.setRedeID(cursor.getInt(10));

                mMap.addMarker(celula.getMarkerOptions());
            }
        } catch (android.database.SQLException sqle) {
        } finally {
            cursor.close();
        }
    }
}
