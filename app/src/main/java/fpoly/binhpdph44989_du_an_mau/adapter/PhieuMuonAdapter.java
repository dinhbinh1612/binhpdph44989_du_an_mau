package fpoly.binhpdph44989_du_an_mau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.temporal.Temporal;
import java.util.ArrayList;

import fpoly.binhpdph44989_du_an_mau.R;
import fpoly.binhpdph44989_du_an_mau.dao.PhieuMuonDAO;
import fpoly.binhpdph44989_du_an_mau.model.PhieuMuon;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{

    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieumuon,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        holder.txtMaPM.setText("Mã PM: " + phieuMuon.getMapm());
        holder.txtMaTV.setText("Mã TV: " + phieuMuon.getMatv());
        holder.txtTenTV.setText("Tên TV: " + phieuMuon.getTentv());
        holder.txtMaTT.setText("Mã TT: " + phieuMuon.getMatt());
        holder.txtTenTT.setText("Tên TT: " + phieuMuon.getTentt());
        holder.txtMaSach.setText("Mã Sách: " + phieuMuon.getMasach());
        holder.txtTenSach.setText("Tên Sách: " + phieuMuon.getTensach());
        holder.txtNgay.setText("Ngày: " + phieuMuon.getNgay());
        String trangthai ="";
        if (phieuMuon.getTrasach() == 1){
            trangthai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.INVISIBLE);
        }else {
            trangthai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText("Trạng Thái: " + trangthai);
        holder.txtTien.setText("Tiền: " + phieuMuon.getTienthue());

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemTra = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
                if (kiemTra){
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPM,txtMaTV,txtTenTV,txtMaTT,txtTenTT,txtMaSach,txtTenSach,txtNgay,txtTrangThai,txtTien;
        Button btnTraSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaPM = itemView.findViewById(R.id.txtMaPM);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtMaTT = itemView.findViewById(R.id.txtMaTT);
            txtTenTT = itemView.findViewById(R.id.txtTenTT);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTien = itemView.findViewById(R.id.txtTien);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}
