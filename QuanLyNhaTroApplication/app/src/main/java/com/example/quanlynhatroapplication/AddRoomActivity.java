package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ServiceAdapter;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Class.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataPhong;
    DatabaseReference dataDV;
    FirebaseStorage storage;
    StorageReference storageRef;
    RecyclerView rcDichVu;
    List<Service> dichVuList;
    List<Service> dichVuListChecked;
    TextInputEditText edtTenPhong, edtGiaPhong, edtTang, edtDienTich, edtGioiHanNguoiThue,edtSoPhongNgu, edtSoPhongKhach, edtMoTa, edtLuuY, edtTienCoc;
    CheckBox cbNu, cbNam, cbKhac;
    ImageView btnLuu, imgHinh, imgCamera;
    int REQUEST_CODE_FOLDER = 123;
    int REQUEST_CODE_CAMERA = 124;
    ServiceAdapter serviceAdapter2;
    House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        Intent intent = getIntent();
        house = (House) intent.getSerializableExtra("house");

        database = FirebaseDatabase.getInstance();
        dataPhong = database.getReference("Nha/"+house.getId()+"/Phong");
        dataDV = database.getReference("DichVu");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://demo2024-9bfa7.appspot.com");


        AnhXa();

        dichVuList = new ArrayList<>();
        dichVuListChecked = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rcDichVu.setLayoutManager(layoutManager);
        serviceAdapter2 = new ServiceAdapter(this, dichVuList, dichVuListChecked);
        rcDichVu.setAdapter(serviceAdapter2);

        setEvent();
    }

    private void setEvent() {
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

                if(imgHinh.getDrawable() == null){
                    showAlertDialog(AddRoomActivity.this, "Thông báo", "Vui lòng thêm hình ảnh");
                }else if(!cbNam.isChecked() && !cbNu.isChecked() && !cbKhac.isChecked()){
                    showAlertDialog(AddRoomActivity.this, "Thông báo", "Vui lòng chọn đối tượng thuê phòng");
                }else if(tenPhong.equals("") || giaPhong.equals("") || tang.equals("") ||  dienTich.equals("") ||
                        gioiHanNguoiThue.equals("") || soPhongNgu.equals("") || soPhongKhach.equals("") || moTa.equals("") || luuY.equals("") || tienCoc.equals("")){
                    showAlertDialog(AddRoomActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                }else{
                    Room room = new Room();
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

                    List<String> listGioiTinh = new ArrayList<>();
                    if (cbNam.isChecked()) listGioiTinh.add("Nam");
                    if (cbNu.isChecked()) listGioiTinh.add("Nữ");
                    if (cbKhac.isChecked()) listGioiTinh.add("Khác");
                    room.setGioiTinh(listGioiTinh);

                    room.setDichVuPhong(dichVuListChecked);

                    ThemPhong(room);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        LayDuLieuDichVu();
        super.onResume();
    }

    private void LayDuLieuDichVu() {
        dichVuList.clear();
        dataDV.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Service dichVu = snapshot.getValue(Service.class);
                        dichVuList.add(dichVu);
                        serviceAdapter2.notifyDataSetChanged();
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

    private void AnhXa() {
        //lvDichVu = findViewById(R.id.listViewDichVu);
        rcDichVu = findViewById(R.id.recyclerViewDichVu);
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtGiaPhong = findViewById(R.id.edtGiaPhong);
        edtTang = findViewById(R.id.edtTangSo);
        edtDienTich = findViewById(R.id.edtDienTich);
        edtGioiHanNguoiThue = findViewById(R.id.edtGioiHanNguoiThue);
        edtMoTa = findViewById(R.id.edtMoTa);
        edtLuuY = findViewById(R.id.edtLuuY);
        edtSoPhongNgu = findViewById(R.id.edtSoPhongNgu);
        edtSoPhongKhach = findViewById(R.id.edtSoPhongKhach);
        edtTienCoc = findViewById(R.id.edtTienCoc);
        cbNu = findViewById(R.id.checkBoxNu);
        cbNam = findViewById(R.id.checkBoxNam);
        cbKhac = findViewById(R.id.checkBoxKhac);
        btnLuu = findViewById(R.id.btnThem);
        imgHinh = findViewById(R.id.imageViewHinh);
        imgCamera = findViewById(R.id.imageViewCamera);
    }

    private void ThemPhong(Room room) {
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
                Toast.makeText(AddRoomActivity.this, "Không thể lưu hình ảnh", Toast.LENGTH_SHORT).show();
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
                        room.setId(dataPhong.push().getKey());

                        //Thêm Phòng vào Nhà
                        dataPhong.child(room.getId()).setValue(room, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error==null){
                                    Toast.makeText(AddRoomActivity.this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(AddRoomActivity.this, "Lỗi thêm phòng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data!=null) {
            Uri selectedImageUri = data.getData();
            imgHinh.setImageURI(selectedImageUri);
        }
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data!=null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}