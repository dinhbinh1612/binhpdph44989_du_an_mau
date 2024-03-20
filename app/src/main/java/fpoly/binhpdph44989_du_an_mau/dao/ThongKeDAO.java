package fpoly.binhpdph44989_du_an_mau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpoly.binhpdph44989_du_an_mau.database.DbHelper;
import fpoly.binhpdph44989_du_an_mau.model.Sach;

public class ThongKeDAO {
    DbHelper dbHelper;

    public ThongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public ArrayList<Sach> getTop10(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.masach, sc.tensach, COUNT(pm.masach) FROM PHIEUMUON pm, SACH sc WHERE pm.masach = sc.masach GROUP BY pm.masach, sc.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10",null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int getDoanhThu(String ngaybatdau,String ngayketthuc){ //2022/09/30
        ngaybatdau = ngaybatdau.replace("/","");
        ngayketthuc = ngayketthuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(TIENTHUE) FROM PHIEUMUON WHERE substr(ngay,7)||substr(ngay,4,2)||substr(ngay,1,2) BETWEEN ? AND ?",new String[]{ngaybatdau,ngayketthuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
