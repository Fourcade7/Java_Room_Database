package com.pr7.java_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText1,editText2;
    Button button;
    RecyclerView recyclerView;

    //Database
    UserDao userDao;
    ArrayList<User> userArrayList;
    User user;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview1);
        button=findViewById(R.id.button1);
        editText1=findViewById(R.id.edittext1);
        editText2=findViewById(R.id.edittext2);

        //create database and connecting
        userDao= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,
                "user.db").allowMainThreadQueries().build().userDao();
        readallusers();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=new User();
                user.setFirstName(editText1.getText().toString());
                user.setLastName(editText2.getText().toString());
                userDao.insert(user);
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                readallusers();

            }
        });
    }

    public void readallusers(){

        userArrayList= (ArrayList<User>) userDao.readallusers();
        userAdapter=new UserAdapter(userArrayList,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(userAdapter);
    }


}