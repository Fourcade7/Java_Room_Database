package com.pr7.java_room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    ArrayList<User> userArrayList;
    Context context;
    MainActivity mainActivity;

    public UserAdapter(ArrayList<User> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;
        mainActivity= (MainActivity) context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if (position%2==0){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#F4F0F0"));
        }else {
            holder.linearLayout.setBackgroundColor(Color.WHITE);

        }

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                UserDao  userDao= Room.databaseBuilder(context,AppDatabase.class,
                        "user.db").allowMainThreadQueries().build().userDao();

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete this user ?");
                builder.setMessage(userArrayList.get(holder.getAdapterPosition()).getFirstName()+"\n"+userArrayList.get(holder.getAdapterPosition()).getLastName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    userDao.delete(userArrayList.get(holder.getAdapterPosition()));
                    mainActivity.readallusers();
                    }
                });
                builder.create().show();


                return true;
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=LayoutInflater.from(context).inflate(R.layout.updatelayout,null);
                UserDao  userDao= Room.databaseBuilder(context,AppDatabase.class,
                        "user.db").allowMainThreadQueries().build().userDao();

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setView(view);
                builder.setTitle("Update this user ?");
               // builder.setMessage(userArrayList.get(holder.getAdapterPosition()).getFirstName()+"\n"+userArrayList.get(holder.getAdapterPosition()).getLastName());
                EditText editText1,editText2;
                editText1=view.findViewById(R.id.edittext3);
                editText2=view.findViewById(R.id.edittext4);
                editText1.setText(userArrayList.get(holder.getAdapterPosition()).getFirstName());
                editText2.setText(userArrayList.get(holder.getAdapterPosition()).getLastName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userArrayList.get(holder.getAdapterPosition()).setFirstName(editText1.getText().toString());
                        userArrayList.get(holder.getAdapterPosition()).setLastName(editText2.getText().toString());
                        userDao.update(userArrayList.get(holder.getAdapterPosition()));
                        mainActivity.readallusers();
                    }
                });
                builder.create().show();

            }
        });

        holder.textView1.setText(userArrayList.get(position).getFirstName());
        holder.textView2.setText(userArrayList.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textView1,textView2;
        LinearLayout linearLayout;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textview1);
            textView2=itemView.findViewById(R.id.textview2);
            linearLayout=itemView.findViewById(R.id.linearlay1);
        }
    }
}
