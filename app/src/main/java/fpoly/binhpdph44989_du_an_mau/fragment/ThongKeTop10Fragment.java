package fpoly.binhpdph44989_du_an_mau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpoly.binhpdph44989_du_an_mau.R;
import fpoly.binhpdph44989_du_an_mau.adapter.Top10Adapter;
import fpoly.binhpdph44989_du_an_mau.dao.ThongKeDAO;
import fpoly.binhpdph44989_du_an_mau.model.Sach;

public class ThongKeTop10Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongketop10,container,false);
        RecyclerView recyclerViewTop10 = view.findViewById(R.id.recyclerTop10);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        ArrayList<Sach> list = thongKeDAO.getTop10();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTop10.setLayoutManager(linearLayoutManager);
        Top10Adapter adapter = new Top10Adapter(getContext(),list);
        recyclerViewTop10.setAdapter(adapter);
        return view;
    }
}
