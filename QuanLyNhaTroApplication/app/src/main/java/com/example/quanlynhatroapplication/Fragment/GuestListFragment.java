package com.example.quanlynhatroapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ChonNguoiThueAdapter;
import com.example.quanlynhatroapplication.Adapter.NguoiThueFragmentAdapter;
import com.example.quanlynhatroapplication.AddNguoiThueActivity;
import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.Customer;
import com.example.quanlynhatroapplication.Class.NguoiThue;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestListFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference dataNguoiThue;
    DatabaseReference dataKH;
    Room room;
    String houseId;
    ImageView btnThemNguoiThue;
    List<NguoiThue> nguoiThueList;
    RecyclerView rvNguoiThue;
    NguoiThueFragmentAdapter nguoiThueAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guest_list, container, false);

        AnhXa(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            room = (Room) bundle.getSerializable("room");
            houseId = bundle.getString("houseId");
        }
        database = FirebaseDatabase.getInstance();
        dataNguoiThue = database.getReference("Nha/"+houseId+"/Phong/"+room.getId()+"/NguoiThue");
        dataKH = database.getReference("KhachHang");

        nguoiThueList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
        rvNguoiThue.setLayoutManager(layoutManager);

        nguoiThueAdapter = new NguoiThueFragmentAdapter(this, nguoiThueList);
        rvNguoiThue.setAdapter(nguoiThueAdapter);

        setEvent(view);

        return view;
    }

    @Override
    public void onResume() {
        LayDuLieuNguoiThue();
        super.onResume();
    }

    private void LayDuLieuNguoiThue() {
        nguoiThueList.clear();
        dataNguoiThue.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                NguoiThue nguoiThue1 = snapshot.getValue(NguoiThue.class);
                nguoiThueList.add(nguoiThue1);
                nguoiThueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent(View view) {
        btnThemNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddNguoiThueActivity.class);
                intent.putExtra("room", room);
                intent.putExtra("houseId", houseId);
                startActivity(intent);
            }
        });
    }

    private void AnhXa(View view) {
        btnThemNguoiThue = view.findViewById(R.id.imageViewThemNguoiThue);
        rvNguoiThue = view.findViewById(R.id.recyclerViewNguoiThue);
    }

    public void openDialogChinhSuaNguoiThue(Context context, NguoiThue nguoiThue){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_nguoithue);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(false);

        TextInputEditText edtHoTen, edtSdt, edtEmail, edtTenPhong, edtNgaySinh, edtNoiSinh, edtCCCD, edtNgayCap, edtNoiCap;
        Button btnLuu, btnHuy;

        edtHoTen = dialog.findViewById(R.id.edtHoTen);
        edtSdt = dialog.findViewById(R.id.edtSoDienThoai);
        edtEmail = dialog.findViewById(R.id.edtEmail);
        edtTenPhong = dialog.findViewById(R.id.edtTenPhong);
        edtNgaySinh = dialog.findViewById(R.id.edtNgaySinh);
        edtNoiSinh = dialog.findViewById(R.id.edtNoiSinh);
        edtCCCD = dialog.findViewById(R.id.edtCanCuocCongDan);
        edtNgayCap = dialog.findViewById(R.id.edtNgayCap);
        edtNoiCap = dialog.findViewById(R.id.edtNoiCap);
        btnLuu = dialog.findViewById(R.id.buttonLuu);
        btnHuy = dialog.findViewById(R.id.buttonHuy);

        HienThiDuLieuDialog(edtHoTen, edtEmail, edtCCCD, edtNoiCap, edtTenPhong, edtSdt, edtNgaySinh, edtNgayCap, edtNoiSinh, btnLuu, btnHuy, nguoiThue);
        setEventDialog(nguoiThue, edtCCCD,edtNoiCap, edtNgaySinh,edtNgayCap,edtNoiSinh, btnLuu,btnHuy, dialog);

        dialog.show();
    }


    public void openDialogXoaNguoiThue(Context context, NguoiThue nguoiThue){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc muốn xóa người thuê " + nguoiThue.getHoTen() + " ?")
                .setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nguoiThueKey = nguoiThue.getId();
                                String emailNguoiThue = nguoiThue.getEmail();
                                dataNguoiThue.child(nguoiThueKey).removeValue();
                                nguoiThueList.remove(nguoiThue);
                                nguoiThueAdapter.notifyDataSetChanged();

                                //Chuyen trang thai KhachHang thanh chua thue
                                ChuyenTrangThaiKhachHang(emailNguoiThue);

                                Toast.makeText(context, "Xóa người thuê thành công", Toast.LENGTH_SHORT).show();

                                dialogInterface.dismiss();
                            }
                        }
                )
                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ChuyenTrangThaiKhachHang(String emailNguoiThue) {
        dataKH.orderByChild("email")
                .equalTo(emailNguoiThue)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            String khachHangKey = snapshot1.getKey();

                            Map<String, Object> updateKhachHang = new HashMap<>();
                            updateKhachHang.put("trangThaiThue", 0);

                            dataKH.child(khachHangKey).updateChildren(updateKhachHang);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void HienThiDuLieuDialog(TextInputEditText edtHoTen, TextInputEditText edtEmail, TextInputEditText edtCCCD,TextInputEditText edtNoiCap,TextInputEditText edtTenPhong,
                                TextInputEditText edtSdt, TextInputEditText edtNgaySinh, TextInputEditText edtNgayCap,TextInputEditText edtNoiSinh,
                                Button btnLuu, Button btnHuy, NguoiThue nguoiThue) {

        edtHoTen.setText(nguoiThue.getHoTen());
        edtSdt.setText(nguoiThue.getSoDienThoai());
        edtEmail.setText(nguoiThue.getEmail());
        edtTenPhong.setText(room.getTenPhong());
        edtNgaySinh.setText(nguoiThue.getNgaySinh());
        edtNoiSinh.setText(nguoiThue.getNoiSinh());
        edtCCCD.setText(nguoiThue.getCanCuocCongDan());
        edtNgayCap.setText(nguoiThue.getNgayCap());
        edtNoiCap.setText(nguoiThue.getNoiCap());
    }

    private void setEventDialog(NguoiThue nguoiThue, TextInputEditText edtCCCD,TextInputEditText edtNoiCap,
                                TextInputEditText edtNgaySinh, TextInputEditText edtNgayCap,TextInputEditText edtNoiSinh,
                          Button btnLuu, Button btnHuy, Dialog dialog) {

        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay(edtNgaySinh);
            }
        });

        edtNgayCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay(edtNgayCap);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaySinh = edtNgaySinh.getText().toString().trim();
                String noiSinh = edtNoiSinh.getText().toString().trim();
                String cccd = edtCCCD.getText().toString().trim();
                String ngayCap = edtNgayCap.getText().toString().trim();
                String noiCap = edtNoiCap.getText().toString().trim();
                if(ngaySinh.equals("") || noiSinh.equals("") || cccd.equals("") || ngayCap.equals("")|| noiCap.equals("")){
                    showAlertDialog(view.getContext(), "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                }else{
                    nguoiThue.setCanCuocCongDan(cccd);
                    nguoiThue.setNgayCap(ngayCap);
                    nguoiThue.setNgaySinh(ngaySinh);
                    nguoiThue.setNoiSinh(noiSinh);
                    nguoiThue.setNoiCap(noiCap);

                    dataNguoiThue.child(nguoiThue.getId()).setValue(nguoiThue, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error==null){
                                Toast.makeText(view.getContext(), "Cập nhật người thuê thành công.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(view.getContext(), "Lỗi cập nhật người thuê", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // Đóng hộp thoại khi nhấn OK
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ChonNgay(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year,month,dayOfMonth);
                        edt.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay
        );
        datePickerDialog.show();
    }
}