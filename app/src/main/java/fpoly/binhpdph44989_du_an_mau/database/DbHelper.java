package fpoly.binhpdph44989_du_an_mau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context,"DANGKYMONHOC",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbThuThu = "CREATE TABLE THUTHU(matt text primary key, hoten text, matkhau text, loaitaikhoan text)";
        sqLiteDatabase.execSQL(dbThuThu);

        String dbThanhVien = "CREATE TABLE THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text)";
        sqLiteDatabase.execSQL(dbThanhVien);

        String dbLoaiSach = "CREATE TABLE LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        sqLiteDatabase.execSQL(dbLoaiSach);

        String dbSach = "CREATE TABLE SACH(masach integer primary key autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        sqLiteDatabase.execSQL(dbSach);

        String dbPhieuMuon = "CREATE TABLE PHIEUMUON(mapm integer primary key autoincrement, matt text references THUTHU(matt),matv integer references THANHVIEN(matv),masach integer references SACH(masach),ngay text,trasach integer,tienthue integer)";
        sqLiteDatabase.execSQL(dbPhieuMuon);

        // data mau
        sqLiteDatabase.execSQL("INSERT INTO LOAISACH(tenloai) VALUES('Thiếu nhi'), ('Tiểu thuyết'), ('Khoa học')");
        sqLiteDatabase.execSQL("INSERT INTO SACH(tensach, giathue, maloai) VALUES('Dế mèn phiêu lưu ký', 20000, 1), ('Ông già và biển cả', 10000, 2), ('Tế bào gốc', 30000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO THUTHU(matt, hoten, matkhau,loaitaikhoan) VALUES('nguoidung01', 'Phạm Văn Nam', '123', 'Admin'), ('nguoidung02', 'Nguyễn Văn Trung', '123', 'Thủ Thư'), ('nguoidung03', 'Bùi Gia Thành', '123', 'Thủ Thư')");
        sqLiteDatabase.execSQL("INSERT INTO THANHVIEN(hoten, namsinh) VALUES('Trần Thị Hương', '2000'), ('Nguyễn Văn An', '1998'), ('Lê Thị Minh', '2002')");
        // tra sach 1: da tra, 0: chua tra
        sqLiteDatabase.execSQL("INSERT INTO PHIEUMUON(matt, matv, masach, ngay, trasach, tienthue) VALUES('nguoidung01', 1, 1, '2024/02/15', 1, 20000), ('nguoidung02', 2, 2, '2024/02/14', 1, 10000), ('nguoidung03', 3, 3, '2024/02/13', 0, 30000)");

        //    sqLiteDatabase.execSQL("INSERT INTO ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THUTHU");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(sqLiteDatabase);
        }
    }
}
