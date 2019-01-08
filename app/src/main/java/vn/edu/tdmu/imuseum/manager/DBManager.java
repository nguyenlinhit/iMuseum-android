package vn.edu.tdmu.imuseum.manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.edu.tdmu.imuseum.model.ArtifactSave;

/**
 * Created by nvulinh on 11/20/17.
 *
 */

public class DBManager extends SQLiteOpenHelper{

    public static final String LOG_TAG = DBManager.class.getSimpleName();
    private static String DATABASE_NAME = "imuseum";
    public static final String TABLE_NAME = "Artifacts";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String NAME  = "name";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final int VERSION = 1;

    private Context context;
    private String pathDatabase = "";
    private final String TAG = "DBManager";
    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                                ID + " integer primary key, " +
                                TITLE + " TEXT, " +
                                NAME + " TEXT, " +
                                DESCRIPTION + " TEXT, " +
                                IMAGE + " TEXT)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        pathDatabase = context.getFilesDir().getParent() + "/databases/" + DATABASE_NAME;
        Log.e("LOG_TAG", pathDatabase);
        File f = context.getDatabasePath(DATABASE_NAME);
        Log.e(LOG_TAG, String.valueOf(f.length()/1024));
    }


    public SQLiteDatabase openDatabase(){
        return SQLiteDatabase.openDatabase(pathDatabase, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public boolean KiemTraDB(){
        SQLiteDatabase kiemTraDB = null;
        try{
            kiemTraDB = SQLiteDatabase.openDatabase(pathDatabase, null, SQLiteDatabase.OPEN_READONLY);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(kiemTraDB !=null){
            kiemTraDB.close();
        }
        return kiemTraDB != null;
    }

    public void queryData(){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(SQLQuery);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        Log.e(TAG, "Create New Database ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.e(TAG, "onUpgrade: ");
    }

    public List<ArtifactSave> getAllFavoriteArtifact(){
        List<ArtifactSave> list = new ArrayList<>();

        String query = "select * from Artifacts";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                ArtifactSave artifactSave = new ArtifactSave();
                artifactSave.setId(cursor.getInt(0));
                artifactSave.setTitle(cursor.getString(1));
                artifactSave.setName(cursor.getString(2));
                artifactSave.setDescription(cursor.getString(3));
                artifactSave.setImage(cursor.getString(4));
                list.add(artifactSave);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean isExits(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + ID + " = " + id,null);
        boolean exits = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exits;
    }

    public ArtifactSave getOneArtifact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + ID + " = " + id,null);
        cursor.moveToFirst();
        ArtifactSave artifactSave = new ArtifactSave();
        artifactSave.setId(cursor.getInt(0));
        artifactSave.setTitle(cursor.getString(1));
        artifactSave.setName(cursor.getString(2));
        artifactSave.setDescription(cursor.getString(3));
        artifactSave.setImage(cursor.getString(4));
        Log.d(TAG, "get Artifact Successfuly");
        return artifactSave;
    }

    public void addArtifactSave(int id, String title, String name, String description, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(TITLE, title);
        values.put(NAME, name);
        values.put(DESCRIPTION, description);
        values.put(IMAGE, image);

        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "add Artifact Successfuly");

    }

    public int deleteArtifactSave(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, ID + " = " + id,null);

    }

}
