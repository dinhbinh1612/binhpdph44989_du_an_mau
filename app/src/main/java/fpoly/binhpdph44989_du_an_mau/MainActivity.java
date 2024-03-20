package fpoly.binhpdph44989_du_an_mau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import fpoly.binhpdph44989_du_an_mau.dao.SachDAO;
import fpoly.binhpdph44989_du_an_mau.dao.ThuThuDAO;
import fpoly.binhpdph44989_du_an_mau.fragment.QLLoaiSachFragment;
import fpoly.binhpdph44989_du_an_mau.fragment.QLPhieuMuonFragment;
import fpoly.binhpdph44989_du_an_mau.fragment.QLSachFragment;
import fpoly.binhpdph44989_du_an_mau.fragment.QLThanhVienFragment;
import fpoly.binhpdph44989_du_an_mau.fragment.ThongKeDoanhThuFragment;
import fpoly.binhpdph44989_du_an_mau.fragment.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        View hearderLayout = navigationView.getHeaderView(0);
        TextView txtTen = hearderLayout.findViewById(R.id.txtTen);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.mQLPhieuMuon){
                    fragment = new QLPhieuMuonFragment();
                } else if (item.getItemId() == R.id.mQLLoaiSach) {
                    fragment = new QLLoaiSachFragment();
                } else if (item.getItemId() == R.id.mThoat){
                    Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.mDoiMatKhau) {
                    showDialogDoiMatKhau();
                } else if (item.getItemId() == R.id.mTop10) {
                    fragment = new ThongKeTop10Fragment();
                } else if (item.getItemId() == R.id.mDoanhThu) {
                    fragment = new ThongKeDoanhThuFragment();
                } else if (item.getItemId() == R.id.mQLThanhVien) {
                    fragment = new QLThanhVienFragment();
                } else if (item.getItemId() == R.id.mQLSach) {
                    fragment = new QLSachFragment();
                } else {
                    fragment = new QLPhieuMuonFragment();
                }

                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaiTK = sharedPreferences.getString("loaitaikhoan","");
        if (!loaiTK.equals("Admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mDoanhThu).setVisible(false);
            menu.findItem(R.id.mTop10).setVisible(false);
        }
        String hoten = sharedPreferences.getString("hoten","");
        txtTen.setText("Xin chào, "+ hoten);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setNegativeButton("Cập nhật",null).setPositiveButton("Hủy",null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau,null);
        EditText edtOldPass = view.findViewById(R.id.edtPassOld);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = edtOldPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();

                if (oldPass.equals("") || newPass.equals("") || reNewPass.equals("")){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (newPass.equals(reNewPass)){
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
                        String matt = sharedPreferences.getString("matt","");
                        // caapj nhaat
                        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                        int check = thuThuDAO.capNhatMatKhau(matt,oldPass,newPass);
                        if (check == 1){
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (check == 0) {
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Mật khẩu mới không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}