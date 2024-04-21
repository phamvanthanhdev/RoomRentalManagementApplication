package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    DatabaseReference mData;
    TextView btnLogin;
    Button btnSignup;
    EditText edtEmail, edtFullname, edtPhone, edtPassword;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mData = FirebaseDatabase.getInstance().getReference();

        AnhXa();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String hoten = edtFullname.getText().toString().trim();
                String sdt = edtPhone.getText().toString().trim();
                String matkhau = edtPassword.getText().toString().trim();
                if(email.equals("") || hoten.equals("") || sdt.equals("") || matkhau.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle("Thông báo lỗi")
                            .setMessage("Vui lòng nhập đầy đủ thông tin")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss(); // Đóng hộp thoại khi nhấn OK
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (matkhau.length() < 8 || matkhau.length() > 15) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle("Thông báo lỗi")
                            .setMessage("Mật khẩu từ 8 đến 15 kí tự")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss(); // Đóng hộp thoại khi nhấn OK
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else if (!validate(email)){
                    showAlertDialog(SignupActivity.this, "Thông báo lỗi", "Email chưa chính xác");
                }else {
                    User user = new User();
                    user.setEmail(email);
                    user.setHoTen(hoten);
                    user.setSdt(sdt);
                    user.setMatKhau(matkhau);

                    KiemTraEmailVaThemUser(user);
                }
            }
        });
    }

    private void AnhXa() {
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.buttonSignup);
        edtEmail = findViewById(R.id.editTextEmailSignup);
        edtFullname = findViewById(R.id.editTextFullnameSignup);
        edtPhone = findViewById(R.id.editTextPhonenumberSignup);
        edtPassword = findViewById(R.id.editTextPassSignup);
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

    public void KiemTraEmailVaThemUser(User user){

        mData.child("User")
                .orderByChild("email")
                .equalTo(user.getEmail())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() == 0) {
                            ThemUserMoi(user);
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        }else{
                            showAlertDialog(SignupActivity.this, "Thông báo lỗi", "Email đã tồn tại");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void ThemUserMoi(User user){
        mData.child("User").push().setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    showAlertDialog(SignupActivity.this, "Thông báo", "Thêm tài khoản thành công");
                }else{
                    showAlertDialog(SignupActivity.this, "Thông báo lỗi", "Lỗi thêm tài khoản");
                }
            }
        });
    }

}