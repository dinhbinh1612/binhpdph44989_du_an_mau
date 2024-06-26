package fpoly.binhpdph44989_du_an_mau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.binhpdph44989_du_an_mau.dao.ThuThuDAO;

public class DangNhapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnDangNhap = findViewById(R.id.btnDangNhap);
        ThuThuDAO thuThuDAO = new ThuThuDAO(this);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                if (thuThuDAO.checkDangNhap(user, pass)) {

                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(DangNhapActivity.this, "Thông tin đăng nhâp không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}