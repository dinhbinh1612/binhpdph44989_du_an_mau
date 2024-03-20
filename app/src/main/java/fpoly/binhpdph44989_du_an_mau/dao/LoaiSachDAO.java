package fpoly.binhpdph44989_du_an_mau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.binhpdph44989_du_an_mau.database.DbHelper;
import fpoly.binhpdph44989_du_an_mau.model.LoaiSach;

public class LoaiSachDAO {
    DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //lay ds loai sach
    public ArrayList<LoaiSach> getDSLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // theem loai sach
    public boolean themLoaiSach(String tenLoai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenLoai);
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        if (check == -1)
            return false;
        return true;
    }

    // xoa loai sach
    // 1 xoa thanh cong
    // 0 xoa that bai
    // -1 the loai da ton tai
    public int xoaLoaiSach(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = sqLiteDatabase.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(id)});
        if (check == -1)
            return 0;
        return 1;

    }

    public boolean thayDoiLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenLoai());
        long check = sqLiteDatabase.update("LOAISACH", contentValues, "maloai = ?", new String[]{String.valueOf(loaiSach.getId())});
        if (check == -1)
            return false;
        return true;

    }
}
