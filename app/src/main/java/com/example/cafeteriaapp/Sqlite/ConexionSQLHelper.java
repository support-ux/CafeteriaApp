package com.example.cafeteriaapp.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.cafeteriaapp.Entidades.ProductoVo;

import java.util.ArrayList;
import java.util.List;

public class ConexionSQLHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "productos.bd";
    private static final int VERSION_BD = 1;
    private static final String TABLA_PRODUCTOS = "CREATE TABLE PRODUCTOS (ID TEXT,NOMBRE TEXT,TOTAL TEXT)";
    private static final String TABLE_NAME = "PRODUCTOS";

    public ConexionSQLHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_PRODUCTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_PRODUCTOS);
        sqLiteDatabase.execSQL(TABLA_PRODUCTOS);
    }

    public void deleteAll()
    {
        SQLiteDatabase bd= getWritableDatabase();
        bd.delete(TABLE_NAME, null, null);
    }

    public void agregarProductos(String id, String nombre, String total){
        SQLiteDatabase bd= getWritableDatabase();
        if(bd!=null){
            bd.execSQL("INSERT INTO PRODUCTOS VALUES('"+id+"','"+nombre+"','"+total+"')");
            bd.close();
        }
    }

    public List<ProductoVo> mostrarLista(){
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM PRODUCTOS",null);
        List<ProductoVo> producto = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                producto.add(new ProductoVo(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return producto;
    }



    public int TotalServings(){
        int serving =0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT SUM(TOTAL) FROM PRODUCTOS", null);
        if(cursor.moveToFirst()) {
            serving = cursor.getInt(0);
        }
        return serving;
    }

    public void editarProductos(String id, String nombre, String total){
        SQLiteDatabase bd= getWritableDatabase();
        if(bd!=null){
            bd.execSQL("UPDATE PRODUCTO SET total ='"+total+"' WHERE id ='"+id+"'");
            bd.close();
        }
    }

    public void eliminarProductos(String id){
        SQLiteDatabase bd= getWritableDatabase();
        if(bd!=null){
            bd.execSQL("DELETE FROM PRODUCTO WHERE ID='"+id+"'");
            bd.close();
        }
    }


}
