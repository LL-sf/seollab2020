package com.example.seollap2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView; //스크롤 형식을 보여줌
    MyAdapter mAdapter; //mAdapter 함수 선언
    private RecyclerView.LayoutManager layoutManager; //스크롤 형식을 사용시 사용하는 도구
    EditText etText; //etText 함수 선언
    Button btnSend; //btnSend 함수 선언


    @Override
    protected void onCreate(Bundle savedInstanceState) { //채팅 구동문
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button btnFinish = (Button) findViewById(R.id.btnFinish); //btnFinish를 누르면 동작 끝 = 꺼짐
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            //스크롤 형식
            public void onClick(View v) {
                finish();
                btnSend = (Button)findViewById(R.id.btnSend); //btnSend 함수 아이디 찾기
                etText = (EditText)findViewById(R.id.etText); //etText 함수 아이디 찾기

                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view); // my_recycler_view 함수 아이디 찾기

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                //(대충 ture 값 건들지 말라는 소리)
                recyclerView.setHasFixedSize(true);

                recyclerView.setLayoutManager(layoutManager); //스크롤형식 사용시 필요한 코드

                // specify an adapter (see also next example)
                String[] myDataset = {"test1", "test2", "test3", "test4"}; // 중괄호 안 글씨는 테스트
                mAdapter = new MyAdapter(myDataset); //mAdapter 함수 리셋
                recyclerView.setAdapter(mAdapter); //리셋된 mAdapter 함수에 mAdapter선언

                btnSend.setOnClickListener(new View.OnClickListener() { //btnSend 함수를 사용하여 메세지 보내기
                    @Override
                    public void onClick(View v) {
                        String stText = etText.getText().toString();
                        Toast.makeText(ChatActivity.this, "MSG:", Toast.LENGTH_SHORT).show();
                        // Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("message");

                        myRef.setValue("Hello, World!");
                    }
                });
            }
        });
    }
}
