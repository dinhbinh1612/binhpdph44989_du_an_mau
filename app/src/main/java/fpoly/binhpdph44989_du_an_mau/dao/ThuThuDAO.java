package fpoly.binhpdph44989_du_an_mau.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.binhpdph44989_du_an_mau.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;
    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }

    // dang nhap
    public boolean checkDangNhap(String matt, String matkhau) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? and matkhau = ?", new String[]{matt, matkhau});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt", cursor.getString(0));
            editor.putString("hoten",cursor.getString(1));
            editor.putString("loaitaikhoan", cursor.getString(3));
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public int capNhatMatKhau(String userName, String oldPass, String newPass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{userName, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?", new String[]{userName});
            if (check == -1)
                return -1;
            return 1;
        }
        return 0;
    }
}
