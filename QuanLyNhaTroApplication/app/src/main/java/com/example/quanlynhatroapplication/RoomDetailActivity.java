package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlynhatroapplication.Adapter.RoomDetailAdaper;
import com.example.quanlynhatroapplication.Adapter.ServiceAdapter;
import com.example.quanlynhatroapplication.Adapter.ServiceAdapter2;
import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.HopDong;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.Fragment.BillFragment;
import com.example.quanlynhatroapplication.Fragment.GuestListFragment;
import com.example.quanlynhatroapplication.Fragment.RoomDetailFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomDetailActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FirebaseDatabase database;
    DatabaseReference dataNha;
    DatabaseReference dataHopDong;
    DatabaseReference dataNguoiThue;
    TextView txtTenPhong;
    ImageView btnHopDong, btnBack, btnEdit, btnDelete;
    Room room;
    House house = new House();
    String houseId;
    List<String> tenNguoiDaiDien;
    ArrayAdapter<String> adapterNguoiDaiDien;

    List<Service> dichVuList;
    List<Service> dichVuListChecked;
    DatabaseReference dataPhong;
    DatabaseReference dataDV;
    FirebaseStorage storage;
    StorageReference storageRef;
    int REQUEST_CODE_FOLDER = 123;
    int REQUEST_CODE_CAMERA = 124;
    ServiceAdapter serviceAdapter;
    RecyclerView rcDichVu;
    ImageView imgHinh, imgCamera;
    int isNewImage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Intent intent = getIntent();
        room = (Room) intent.getSerializableExtra("room");
        houseId = intent.getStringExtra("houseId");

        dichVuList = new ArrayList<>();
        dichVuListChecked = new ArrayList<>();

        AnhXa();
        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");
        dataHopDong = database.getReference("HopDong");
        dataNguoiThue = database.getReference("Nha/"+houseId+"/Phong/"+room.getId()+"/NguoiThue");

        dataPhong = database.getReference("Nha/"+houseId+"/Phong");
        dataDV = database.getReference("DichVu");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://demo2024-9bfa7.appspot.com");

        tenNguoiDaiDien = new ArrayList<>();
        LayTenNguoiThue();

        LayDuLieuNha();
        //Toast.makeText(this, "houseId "+houseId, Toast.LENGTH_SHORT).show();

        tabLayout.setupWithViewPager(viewPager);
        RoomDetailAdaper adaper = new RoomDetailAdaper(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Bundle bundle = new Bundle();
        bundle.putSerializable("room", (Serializable) room);
        bundle.putString("houseId", houseId);

        GuestListFragment guestListFragment = new GuestListFragment();
        guestListFragment.setArguments(bundle);

        adaper.addFragment(guestListFragment, "Người thuê");

        RoomDetailFragment roomDetailFragment = new RoomDetailFragment();
        roomDetailFragment.setArguments(bundle);

        adaper.addFragment(roomDetailFragment, "Chi tiết phòng");

        BillFragment billFragment = new BillFragment();
        billFragment.setArguments(bundle);

        adaper.addFragment(billFragment, "Hóa đơn");

        viewPager.setAdapter(adaper);

        setEvent();
    }

    @Override
    protected void onResume() {
        //LayDuLieuNha();
        LayDuLieuDichVu();
        super.onResume();
    }

    private void setEvent(){
        btnHopDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogThemHopDong();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogChinhSuaPhong();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogXoaPhong();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data!=null) {
            Uri selectedImageUri = data.getData();
            imgHinh.setImageURI(selectedImageUri);
            isNewImage = 1;
        }
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data!=null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
            isNewImage = 1;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void openDialogChinhSuaPhong(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_room);

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

        TextInputEditText edtTenPhong, edtGiaPhong, edtTang, edtDienTich, edtGioiHanNguoiThue,edtSoPhongNgu, edtSoPhongKhach, edtMoTa, edtLuuY, edtTienCoc;
        CheckBox cbNu, cbNam, cbKhac;
        Button btnLuu, btnHuy;

        rcDichVu = dialog.findViewById(R.id.recyclerViewDichVu);
        edtTenPhong = dialog.findViewById(R.id.edtTenPhong);
        edtGiaPhong = dialog.findViewById(R.id.edtGiaPhong);
        edtTang = dialog.findViewById(R.id.edtTangSo);
        edtDienTich = dialog.findViewById(R.id.edtDienTich);
        edtGioiHanNguoiThue = dialog.findViewById(R.id.edtGioiHanNguoiThue);
        edtMoTa = dialog.findViewById(R.id.edtMoTa);
        edtLuuY = dialog.findViewById(R.id.edtLuuY);
        edtSoPhongNgu = dialog.findViewById(R.id.edtSoPhongNgu);
        edtSoPhongKhach = dialog.findViewById(R.id.edtSoPhongKhach);
        edtTienCoc = dialog.findViewById(R.id.edtTienCoc);
        cbNu = dialog.findViewById(R.id.checkBoxNu);
        cbNam = dialog.findViewById(R.id.checkBoxNam);
        cbKhac = dialog.findViewById(R.id.checkBoxKhac);
        btnLuu = dialog.findViewById(R.id.buttonLuu);
        btnHuy = dialog.findViewById(R.id.buttonHuy);
        imgHinh = dialog.findViewById(R.id.imageViewHinh);
        imgCamera = dialog.findViewById(R.id.imageViewCamera);

        LayDuLieuDichVu();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rcDichVu.setLayoutManager(layoutManager);
        serviceAdapter = new ServiceAdapter(this, dichVuList, dichVuListChecked);
        rcDichVu.setAdapter(serviceAdapter);


        edtTenPhong.setText(room.getTenPhong());
        edtGiaPhong.setText(String.valueOf(room.getGiaPhong()));
        edtTang.setText(String.valueOf(room.getTangSo()));
        edtDienTich.setText(String.valueOf(room.getDienTich()));
        edtGioiHanNguoiThue.setText(String.valueOf(room.getGioiHanNguoiThue()));
        edtMoTa.setText(room.getMoTa());
        edtLuuY.setText(room.getLuuY());
        edtSoPhongNgu.setText(String.valueOf(room.getSoPhongNgu()));
        edtSoPhongKhach.setText(String.valueOf(room.getSoPhongKhach()));
        edtTienCoc.setText(String.valueOf(room.getTienCoc()));
        for (String doiTuong: room.getGioiTinh()) {
            if(doiTuong.equals("Nam")) cbNam.setChecked(true);
            if(doiTuong.equals("Nữ")) cbNu.setChecked(true);
            if(doiTuong.equals("Khác")) cbKhac.setChecked(true);
        }
        Glide.with(this).load(room.getLinkHinh()).into(imgHinh);

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
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
                String tenPhong = edtTenPhong.getText().toString().trim();
                String giaPhong = edtGiaPhong.getText().toString().trim();
                String tang = edtTang.getText().toString().trim();
                String dienTich = edtDienTich.getText().toString().trim();
                String gioiHanNguoiThue = edtGioiHanNguoiThue.getText().toString().trim();
                String soPhongNgu = edtSoPhongNgu.getText().toString().trim();
                String soPhongKhach = edtSoPhongKhach.getText().toString().trim();
                String moTa = edtMoTa.getText().toString().trim();
                String luuY = edtLuuY.getText().toString().trim();
                String tienCoc = edtTienCoc.getText().toString().trim();

                if(!cbNam.isChecked() && !cbNu.isChecked() && !cbKhac.isChecked()){
                    showAlertDialog(RoomDetailActivity.this, "Thông báo", "Vui lòng chọn đối tượng thuê phòng");
                }else if(tenPhong.equals("") || giaPhong.equals("") || tang.equals("") ||  dienTich.equals("") ||
                        gioiHanNguoiThue.equals("") || soPhongNgu.equals("") || soPhongKhach.equals("") || moTa.equals("") || luuY.equals("") || tienCoc.equals("")){
                    showAlertDialog(RoomDetailActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                }else{
                    room.setTenPhong(tenPhong);
                    room.setGiaPhong(Integer.parseInt(giaPhong));
                    room.setTangSo(Integer.parseInt(tang));
                    room.setDienTich(Double.parseDouble(dienTich));
                    room.setGioiHanNguoiThue(Integer.parseInt(gioiHanNguoiThue));
                    room.setSoPhongNgu(Integer.parseInt(soPhongNgu));
                    room.setSoPhongKhach(Integer.parseInt(soPhongKhach));
                    room.setMoTa(moTa);
                    room.setLuuY(luuY);
                    room.setTienCoc(Integer.parseInt(tienCoc));
                    room.setDichVuPhong(dichVuListChecked);

                    List<String> listGioiTinh = new ArrayList<>();
                    if (cbNam.isChecked()) listGioiTinh.add("Nam");
                    if (cbNu.isChecked()) listGioiTinh.add("Nữ");
                    if (cbKhac.isChecked()) listGioiTinh.add("Khác");
                    room.setGioiTinh(listGioiTinh);

                    if(isNewImage == 1)
                        UploadHinhAnh(room);

                    String roomKey = room.getId();
                    // Cập nhật thông tin
                    Map<String, Object> updatePhong = new HashMap<>();
                    updatePhong.put("tenPhong", room.getTenPhong());
                    updatePhong.put("giaPhong", room.getGiaPhong());
                    updatePhong.put("tangSo", room.getTangSo());
                    updatePhong.put("dienTich", room.getDienTich());
                    updatePhong.put("gioiHanNguoiThue", room.getGioiHanNguoiThue());
                    updatePhong.put("soPhongNgu", room.getSoPhongNgu());
                    updatePhong.put("soPhongKhach", room.getSoPhongKhach());
                    updatePhong.put("moTa", room.getMoTa());
                    updatePhong.put("luuY", room.getLuuY());
                    updatePhong.put("tienCoc", room.getTienCoc());
                    updatePhong.put("linkHinh", room.getLinkHinh());
                    updatePhong.put("dichVuPhong", room.getDichVuPhong());
                    updatePhong.put("gioiTinh", room.getGioiTinh());

                    dataPhong.child(roomKey).updateChildren(updatePhong, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error!=null){
                                showAlertDialog(RoomDetailActivity.this, "Thông báo", "Lỗi cập nhật phòng.");
                            }else{
                                Toast.makeText(RoomDetailActivity.this, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show();
                                //finish();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    private void UploadHinhAnh(Room room) {
        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = storageRef.child("image"+calendar.getTimeInMillis()+".png");
        // Get the data from an ImageView as bytes
        imgHinh.setDrawingCacheEnabled(true);
        imgHinh.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgHinh.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(RoomDetailActivity.this, "Không thể lưu hình ảnh", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AddHouseActivity.this, "Thành công.", Toast.LENGTH_SHORT).show();

                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String linkHinh = uri+"";
                        room.setLinkHinh(linkHinh);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("AAA", "Failed to get uri");
                    }
                });

            }
        });
    }

    private void LayDuLieuDichVu() {
        dichVuList.clear();
        dataDV.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Service dichVu = snapshot.getValue(Service.class);
                dichVuList.add(dichVu);
                //serviceAdapter.notifyDataSetChanged();
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


    public void openDialogXoaPhong(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc muốn xóa " + room.getTenPhong() + " ?")
                .setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String roomKey = room.getId();
                                dataPhong.child(roomKey).removeValue();

                                Toast.makeText(RoomDetailActivity.this, "Xóa phòng thành công", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                finish();
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

    private void openDialogThemHopDong() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_hopdong);

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

        AutoCompleteTextView completeNguoiDaiDien = dialog.findViewById(R.id.autoCompleteNguoiDaiDien);
        TextInputEditText edtTenNha = dialog.findViewById(R.id.edtTenNha);
        TextInputEditText edtTenPhong = dialog.findViewById(R.id.edtTenPhong);
        TextInputEditText edtCamKetNguoiThue = dialog.findViewById(R.id.edtCamKetNguoiThue);
        TextInputEditText edtTienPhong = dialog.findViewById(R.id.edtTienPhong);
        TextInputEditText edtTienCoc = dialog.findViewById(R.id.edtTienCoc);
        TextInputEditText edtNgayBatDau = dialog.findViewById(R.id.edtNgayBatDauTinhTien);
        TextInputEditText edtNgayTao = dialog.findViewById(R.id.edtNgayTaoHopDong);
        TextInputEditText edtKyHan = dialog.findViewById(R.id.edtKiHanThanhToan);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);
        Button btnThem = dialog.findViewById(R.id.buttonThem);

        adapterNguoiDaiDien = new ArrayAdapter<>(this, R.layout.list_item_drop_down, tenNguoiDaiDien);
        completeNguoiDaiDien.setAdapter(adapterNguoiDaiDien);

        edtTenPhong.setText(room.getTenPhong());
        edtTenNha.setText(house.getTenNha());
        edtTienPhong.setText(String.valueOf(room.getGiaPhong()));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        edtNgayTao.setText(simpleDateFormat.format(calendar.getTime()));
        RecyclerView rvDichVu = dialog.findViewById(R.id.recyclerViewDichVu);

        rvDichVu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Service> dichVuList = room.getDichVuPhong();
        ServiceAdapter2 serviceAdapter2 = new ServiceAdapter2(this, dichVuList);
        rvDichVu.setAdapter(serviceAdapter2);

        setEventDialog(edtNgayBatDau, btnHuy, btnThem, dialog);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTenNguoiDaiDien = completeNguoiDaiDien.getText().toString().trim();
                String tenNha = edtTenNha.getText().toString().trim();
                String tenPhong = edtTenPhong.getText().toString().trim();
                String camKetNguoiThue = edtCamKetNguoiThue.getText().toString().trim();
                String tienPhong = edtTienPhong.getText().toString().trim();
                String tienCoc = edtTienCoc.getText().toString().trim();
                String ngayBatDau = edtNgayBatDau.getText().toString().trim();
                String ngayTao = edtNgayTao.getText().toString().trim();
                String kyHan = edtKyHan.getText().toString().trim();
                if(tenNha.equals("") || tenPhong.equals("") || camKetNguoiThue.equals("") ||
                        tienPhong.equals("") || tienCoc.equals("") || ngayBatDau.equals("") ||
                        ngayTao.equals("") || kyHan.equals("")){
                    showAlertDialog(RoomDetailActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin.");
                }else{
                    HopDong hopDong = new HopDong();
                    hopDong.setTenNha(tenNha);
                    hopDong.setIdNha(houseId);
                    hopDong.setTenPhong(tenPhong);
                    hopDong.setIdPhong(room.getId());
                    hopDong.setCamKetNguoiThue(Integer.parseInt(camKetNguoiThue));
                    hopDong.setTienPhong(tienPhong);
                    hopDong.setTienCoc(tienCoc);
                    hopDong.setNgayBatDauTinhTien(ngayBatDau);
                    hopDong.setNgayKyHopDong(ngayTao);
                    hopDong.setKyHanThanhToan(kyHan);
                    hopDong.setHoTen(hoTenNguoiDaiDien);
                    hopDong.setDichVu(dichVuList);
                    hopDong.setId(dataHopDong.push().getKey());
                    dataHopDong.child(hopDong.getId()).setValue(hopDong, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error == null){
                                Toast.makeText(RoomDetailActivity.this, "Thêm hợp đồng thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                showAlertDialog(RoomDetailActivity.this, "Thông báo", "Lỗi thêm hợp đồng.");
                            }
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    public void setEventDialog(TextInputEditText edtNgayBatDau, Button btnHuy, Button btnThem, Dialog dialog){
        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay(edtNgayBatDau);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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


    private void AnhXa() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        txtTenPhong = findViewById(R.id.textViewTenPhong);
        btnHopDong = findViewById(R.id.imageViewHopDong);
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void ChonNgay(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(RoomDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year,month, dayOfMonth);
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void LayDuLieuNha() {
        dataNha.orderByChild("id")
                .equalTo(houseId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item:snapshot.getChildren()) {
                            House nha = item.getValue(House.class);
                            house.setId(nha.getId());
                            house.setTenNha(nha.getTenNha());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void LayTenNguoiThue() {
        dataNguoiThue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tenNguoiDaiDien.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    tenNguoiDaiDien.add(item.child("hoTen").getValue().toString());
                    Log.d("AAA", item.child("hoTen").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}