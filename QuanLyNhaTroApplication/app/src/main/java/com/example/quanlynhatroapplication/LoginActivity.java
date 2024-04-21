package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quanlynhatroapplication.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference mData;
    TextView btnSignup;
    Button btnLogin;
    EditText edtEmail, edtPassword;
    final static User userLogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mData = FirebaseDatabase.getInstance().getReference();

        AnhXa();

        edtEmail.setText("admin@gmail.com");
        edtPassword.setText("12345678");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String matkhau = edtPassword.getText().toString().trim();
                if(email.equals("") || matkhau.equals("")){
                    showAlertDialog(LoginActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin.");
                }else{
                    LayUserDangNhap(email, matkhau);
                }
            }
        });
    }

    private void LayUserDangNhap(String email, String matkhau) {
        mData.child("User")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                if (user != null && user.getMatKhau().equals(matkhau)) {
                                    //userLogin.setHoTen(user.getHoTen());
                                    //userLogin.setEmail(user.getEmail());
                                    //userLogin.setSdt(user.getSdt());
                                    //userLogin.setMatKhau(user.getMatKhau());
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    // Mật khẩu không khớp
                                    showAlertDialog(LoginActivity.this, "Thông báo", "Thông tin tài khoản, mật khẩu không chính xác.");
                                }
                            }
                        }else{
                            showAlertDialog(LoginActivity.this, "Thông báo", "Thông tin tài khoản, mật khẩu không chính xác.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void AnhXa() {
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.buttonLogin);
        edtEmail = findViewById(R.id.editTextEmailLogin);
        edtPassword = findViewById(R.id.editTextPassLogin);
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