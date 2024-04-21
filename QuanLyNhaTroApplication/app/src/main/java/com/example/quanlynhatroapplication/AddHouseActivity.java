package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Class.House;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddHouseActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataNha;
    DatabaseReference dataThanhPho;
    DatabaseReference dataQuanHuyen;
    FirebaseStorage storage;
    StorageReference storageRef;
    ImageView imgHinh, imgCamera;
    TextInputEditText edtTenNha, edtSoTang, edtMoTa, edtDiaChi, edtGioMoCua, edtGioDongCua, edtGhiChu,edtNgayChuyenDi;
    ImageView btnLuu;
    Map<String, String> thanhPhoMaps;
    List<String> tenThanhPhoList;
    List<String> tenQuanHuyenList;
    ArrayAdapter<String> adapterThanhPho, adapterQuanHuyen;
    int REQUEST_CODE_FOLDER = 123;
    int REQUEST_CODE_CAMERA = 124;

    AutoCompleteTextView autoCompleteTinhThanh, autoCompleteQuanHuyen;
    int positionCheck = -1; // Kiểm tra tỉnh thành, quận huyện có được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");
        dataThanhPho = database.getReference("ThanhPho");
        dataQuanHuyen = database.getReference("QuanHuyen");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://demo2024-9bfa7.appspot.com");

        thanhPhoMaps = new HashMap<>();
        tenThanhPhoList = new ArrayList<>();
        tenQuanHuyenList = new ArrayList<>();

        layDuLieuThanhPho();

        AnhXa();

        adapterThanhPho = new ArrayAdapter<>(this, R.layout.list_item_drop_down, tenThanhPhoList);
        autoCompleteTinhThanh.setAdapter(adapterThanhPho);

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

        edtGioMoCua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonGio(edtGioMoCua);
            }
        });

        edtGioDongCua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonGio(edtGioDongCua);
            }
        });

        autoCompleteTinhThanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                for (Map.Entry m: thanhPhoMaps.entrySet()){
                    if(m.getValue().equals(item)){
                        LayDuLieuQuanHuyen(m.getKey().toString());

                        adapterQuanHuyen= new ArrayAdapter<>(AddHouseActivity.this, R.layout.list_item_drop_down, tenQuanHuyenList);
                        autoCompleteQuanHuyen.setAdapter(adapterQuanHuyen);
                    }
                }
            }
        });

        autoCompleteQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionCheck = position;
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNha = edtTenNha.getText().toString().trim();
                String soTang = edtSoTang.getText().toString().trim();
                String moTa = edtMoTa.getText().toString().trim();
                String diaChi = edtDiaChi.getText().toString().trim();
                String thanhPho = autoCompleteTinhThanh.getText().toString().trim();
                String quanHuyen = autoCompleteQuanHuyen.getText().toString().trim();
                String gioMoCua = edtGioMoCua.getText().toString().trim();
                String gioDongCua = edtGioDongCua.getText().toString().trim();
                String ghiChu = edtGhiChu.getText().toString().trim();
                String ngayChuyenDi = edtNgayChuyenDi.getText().toString().trim();

                if(imgHinh.getDrawable() == null){
                    showAlertDialog(AddHouseActivity.this, "Thông báo", "Vui lòng thêm hình ảnh");
                }else if(tenNha.equals("") || soTang.equals("") || moTa.equals("") || diaChi.equals("") || gioMoCua.equals("") || gioDongCua.equals("") || thanhPho.equals("") || quanHuyen.equals("")){
                    showAlertDialog(AddHouseActivity.this, "Thông báo", "Vui lòng nhập đủ thông tin");
                }else{
                    House house = new House();
                    house.setTenNha(tenNha);
                    house.setDiaChi(diaChi);
                    house.setGhiChu(ghiChu);
                    house.setMoTa(moTa);
                    house.setGioDongCua(gioDongCua);
                    house.setGioMoCua(gioMoCua);
                    house.setQuanHuyen(quanHuyen);
                    house.setTinhThanh(thanhPho);
                    house.setSoNgayChuyenDi(Integer.parseInt(ngayChuyenDi));
                    house.setSoTang(Integer.parseInt(soTang));
                    house.setId(dataNha.push().getKey());

                    ThemNha(house);
                }

            }
        });
    }

    private void ThemNha(House house) {
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
                Toast.makeText(AddHouseActivity.this, "Không thể lưu hình ảnh", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AddHouseActivity.this, "Thành công.", Toast.LENGTH_SHORT).show();

                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String linkHinh = uri+"";
                        house.setLinkHinh(linkHinh);
                        //Thêm nhà
                        dataNha.child(house.getId()).setValue(house, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error==null){
                                    Toast.makeText(AddHouseActivity.this, "Thêm nhà thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(AddHouseActivity.this, "Lỗi thêm nhà", Toast.LENGTH_SHORT).show();
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

    private void AnhXa() {
        imgHinh = findViewById(R.id.imageViewHinh);
        imgCamera = findViewById(R.id.imageViewCamera);

        edtTenNha = findViewById(R.id.edtTenPhong);
        edtSoTang = findViewById(R.id.edtSoTang);
        edtMoTa = findViewById(R.id.edtMoTa);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtGioMoCua = findViewById(R.id.edtSoPhongNgu);
        edtGioDongCua = findViewById(R.id.edtGioDongCua);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        edtNgayChuyenDi = findViewById(R.id.edtSoNgayChuyenDi);

        btnLuu = findViewById(R.id.btnThem);

        autoCompleteTinhThanh = findViewById(R.id.autoCompleteTinhThanh);
        autoCompleteQuanHuyen = findViewById(R.id.autoCompleteQuanHuyen);
    }

    private void ChonGio(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddHouseActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        calendar.set(0,0,0,hourOfDay, minute);
                        edt.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, gio, phut, true
        );
        timePickerDialog.show();
    }

    public void layDuLieuThanhPho(){
        tenThanhPhoList.clear();
        dataThanhPho.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                thanhPhoMaps.put(snapshot.getKey().toString(), snapshot.getValue().toString());
                tenThanhPhoList.add(snapshot.getValue().toString());
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

    private void LayDuLieuQuanHuyen(String keyThanhPho){
        tenQuanHuyenList.clear();
        dataQuanHuyen.child(keyThanhPho)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        tenQuanHuyenList.add(snapshot.getValue().toString());
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
}