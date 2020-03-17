package com.example.seollap2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity"; //로그인, 회원가입 태그 선언
    private FirebaseAuth mAuth; //mAuth함수 선언
    ProgressBar progressBar; //로딩바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth(파이어베이스 전용 함수?)
        mAuth = FirebaseAuth.getInstance();

        //
        final EditText etId = (EditText) findViewById(R.id.etId);
       final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            //아이디, 비번 안치면 뜨는 설명어 (로그인)
            public void onClick(View v) {
                String stEmail = etId.getText().toString();
                String stPassword = etPassword.getText().toString();
                if (stEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "아이디(이메일)을 입력세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (stPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE); //로그인시 로딩바 시작, 생성 명령어
                //로그인
                mAuth.signInWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // 로그인시 로딩바 끝, 삭제 명령어
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    // ( 로그인 성공시, 사용자 정보(이메일, 이름)업데이트)(미포함)
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String stUserEmail = user.getEmail();
                                    String stUserName = user.getDisplayName();
                                    Log.d(TAG, "stUserEmail : " + stUserEmail + ", stUsername : " + stUserName);
                                    Intent in = new Intent(MainActivity.this, ChatActivity.class);
                                    startActivity(in);
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // (로그인 실패시 사용자에게 뜨는 알림)
                                    Log.w(TAG, "signInWithEmail:실패", task.getException());
                                    Toast.makeText(MainActivity.this, "인증 실패.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }
//                Toast.makeText (MainActivity.this, "Login", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(MainActivity.this, ChatActivity.class);
                                startActivity(in);
                            }
                        });

                //회원가입
                Button btnRegister = (Button) findViewById(R.id.btnRegister);
                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stEmail = etId.getText().toString(); //이메일 수집
                        String stPassword = etPassword.getText().toString(); //비밀번호 수집
                        if (stEmail.isEmpty()) { //이메일 칸이 비어있을 때 뜨는 알림
                            Toast.makeText(MainActivity.this, "이메일을 입력세요", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (stPassword.isEmpty()) { //비밀번호칸이 비어있을 때 뜨는 알림
                            Toast.makeText(MainActivity.this, "비밀번호를 입력세요", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        progressBar.setVisibility(View.VISIBLE); //회원가입시 로딩바 시작, 생성 명령어
//                Toast.makeText(MainActivity.this, "Email : "+stEmail+", Password : "+stPassword, Toast.LENGTH_LONG).show();
                        mAuth.createUserWithEmailAndPassword(stEmail, stPassword) //새로운 이메일, 비밀번호 파이어베이스에 전송
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE); //회원가입시 로딩바 끝, 삭제 명령어
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            //(회원가입 성공시 뜨는 알림)
                                            Log.d(TAG, "createUserWithEmail:계정 만들기 성공");
                                            FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            //(회원가입 실패시 뜨는 알림)
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                        }

                                        // ...
                                    }
                                });
                    }
                });
            }
        });
    }
}
