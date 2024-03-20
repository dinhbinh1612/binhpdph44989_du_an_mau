package fpoly.binhpdph44989_du_an_mau.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import fpoly.binhpdph44989_du_an_mau.R;
import fpoly.binhpdph44989_du_an_mau.adapter.SachAdapter;
import fpoly.binhpdph44989_du_an_mau.dao.LoaiSachDAO;
import fpoly.binhpdph44989_du_an_mau.dao.SachDAO;
import fpoly.binhpdph44989_du_an_mau.model.LoaiSach;
import fpoly.binhpdph44989_du_an_mau.model.Sach;

public class QLSachFragment extends Fragment {
    SachDAO sachDAO;
    RecyclerView recyclerSach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);
        recyclerSach = view.findViewById(R.id.recyclerQLSach);
        sachDAO = new SachDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }
    private void loadData(){
        ArrayList<Sach> list = sachDAO.getDSDauSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSach.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), list, getDSLoaiSach(),sachDAO);
        recyclerSach.setAdapter(adapter);
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);
        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), getDSLoaiSach(), android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLoaiSach.setAdapter(simpleAdapter);
        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tensach = edtTenSach.getText().toString();
                int tien = Integer.parseInt(edtTien.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");
                boolean check = sachDAO.themSachMoi(tensach,tien,maloai);
                if (check){
                    Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private ArrayList<HashMap<String, Object>> getDSLoaiSach() {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiSach loai : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", loai.getId());
            hs.put("tenloai", loai.getTenLoai());
            listHM.add(hs);
        }
        return listHM;
    }
}
