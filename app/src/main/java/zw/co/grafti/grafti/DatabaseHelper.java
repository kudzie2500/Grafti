package zw.co.grafti.grafti;
/**
 * Created by Kudziee on 12/20/2016.
 */

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DatabaseHelper extends SQLiteOpenHelper{

    static String DatabaseName = "grafti.db";
    public static final int DatabaseVersion = 1;

    /**************************** Tables *********************************************/
    public static final String KEY_Table = "freelancers";
    public static final String KEY_ID = "_id";
    public static final String KEY_Name = "name";
    public static final String KEY_Phone = "phone";
    public static final String KEY_Email = "email";
    public static final String KEY_Locat = "location";
    public static final String KEY_CityTown ="ctyTown";
    public static final String KEY_Descr = "description";
    public static final String KEY_Cat = "category";
    public static final String KEY_Charge = "charge";
    public static final String KEY_Pass = "password";

    public DatabaseHelper(Context context){
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+KEY_Table+" ( "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_Name+" TEXT, "+KEY_Phone+" TEXT, "+KEY_Email+" TEXT, "+KEY_Locat+" TEXT, "+KEY_CityTown+" TEXT, "+KEY_Descr+" TEXT, "+KEY_Cat+" TEXT,"
                +KEY_Charge+" TEXT, "+KEY_Pass+" TEXT)";

        try{
            database.execSQL(CREATE_TABLE);
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldDatabase, int newDatabase){
        try {
            database.execSQL("DROP IF EXISTS "+DatabaseName);
            onCreate(database);
        }catch (Exception e){e.printStackTrace();}

    }

    /************************************************ CRUD FUNCTIONS ***************************************************/

    /************************************************* Adding new account ********************************************/
    public boolean newAccount(String data[]){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Name, data[1]);
        values.put(KEY_Phone, data[2]);
        values.put(KEY_Email, data[3]);
        values.put(KEY_Locat, data[4]);
        values.put(KEY_CityTown, data[5]);
        values.put(KEY_Descr, data[6]);
        values.put(KEY_Cat, data[7]);
        values.put(KEY_Charge, data[8]);
        values.put(KEY_Pass, data[9]);

        long result = db.insert(KEY_Table, null, values);

        return result > 0;
    }

    /*
    public boolean deleteAccont(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        String user = data;
        //statements to deleate the account then go back to main activity.

    }
    */

    /*
    public boolean updateAccount(String data[]){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
    }
    */

    /************************************for searching ***********************************************************/

    public Cursor fetchQuery(String skill, String cat){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(cat.equals("")){
            cursor = db.rawQuery("SELECT * FROM "+KEY_Table+" WHERE "+KEY_Descr+" LIKE '"+skill+"'", null);
        }else if (skill.equals(null) && cat.equals(null)){
            cursor = null;
        }else if (!cat.equals(null) && !skill.equals(null)){
            cursor = db.rawQuery("SELECT * FROM "+KEY_Table+" WHERE ("+KEY_Cat+"='"+cat+"' AND "+KEY_Descr+" LIKE '"+skill+"')",null);
        }
        return cursor;
    }

    /************************ for login activity ************************************************/

    public int authenticcate(String usr, String password){
        int id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+KEY_Table+" WHERE ("+KEY_Email+"= '"+usr+"' OR "+KEY_Phone+" ='"+usr+"') AND "+KEY_Pass+
                "'"+password+"'";

        Cursor cursor = db.rawQuery(qry, null);
        if (cursor!=null && cursor.getCount()>0 && cursor.getCount()<2)
            id=cursor.getColumnIndex(KEY_ID);
        return id;
    }

    /*************************************** updating passwords ******************************************/

    public boolean passUpdate(int id, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_Pass, password);
        int resullt = db.update(KEY_Table, value,KEY_ID+" = "+id, null);
        return resullt>0;
    }

    /************************************* Returns *************************************************/

    public String getName(int id){
        String name = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+KEY_Table+" WHERE "+KEY_ID+" = "+id;
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()){
            do{
                name= cursor.getString(cursor.getColumnIndexOrThrow(KEY_Name));
            }while (cursor.moveToNext());
        }
        return name;
    }

    public String getDescription(int id){
        String d = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+KEY_Table+" WHERE "+KEY_ID+" = "+id;
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()){
            do{
                d= cursor.getString(cursor.getColumnIndexOrThrow(KEY_Descr));
            }while (cursor.moveToNext());
        }
        return d;
    }

    public String getNumber(int id){
        String phone = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+KEY_Table+" WHERE "+KEY_ID+" = "+id;
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()){
            do{
                phone= cursor.getString(cursor.getColumnIndexOrThrow(KEY_Phone));
            }while (cursor.moveToNext());
        }
        return phone;
    }

    public String getMail(int id){
        String mail = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+KEY_Table+" WHERE "+KEY_ID+" = "+id;
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()){
            do{
                mail= cursor.getString(cursor.getColumnIndexOrThrow(KEY_Email));
            }while (cursor.moveToNext());
        }
        return mail;
    }

    /************************************ editing account ****************************************/
    public boolean accEdit(String[] data, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (data[0]!=null)
        values.put(KEY_Phone, data[1]);
        if (data[1]!=null)
            values.put(KEY_Email, data[1]);
        if (data[2]!=null)
            values.put(KEY_CityTown, data[2]);
        if (data[3]!=null)
            values.put(KEY_Locat, data[3]);
        if (data[4]!=null)
            values.put(KEY_Descr, data[4]);
        if (data[5]!=null)
            values.put(KEY_Cat, data[5]);
        if (data[6]!=null)
            values.put(KEY_Charge, data[6]);

        int result = db.update(KEY_Table, values,"WHERE "+KEY_ID+" = "+id, null);

        return result>0;
    }

    /*****************************************delete account**********************************************/
    public boolean deleteAcc(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(KEY_Table, KEY_ID+" = "+id, null) > 0;

    }
}
