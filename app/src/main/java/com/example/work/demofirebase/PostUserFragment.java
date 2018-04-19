package com.example.work.demofirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PostUserFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.edt_id)
    EditText edtId;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.tv_chart_email)
    TextView tvChartEmail;
    @BindView(R.id.btn_postUser)
    Button btnPostUser;

    private DatabaseReference databaseReference;

    public static PostUserFragment newInstance() {
        return new PostUserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postuser, container, false);
        ButterKnife.bind(this, view);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnPostUser.setOnClickListener(this);
        edtId.setEnabled(false);

        getDataUser();

        return view;
    }

    private void getDataUser() {
        final List<User> userList = new ArrayList<>();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        userList.add(item.getValue(User.class));
                    }
                }
                Integer id = userList.size() + 1;
                edtId.setText(String.valueOf(id));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Post Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnPostUser) {
            if (edtName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Name is empty!", Toast.LENGTH_SHORT).show();
            } else if (edtEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Email is empty!", Toast.LENGTH_SHORT).show();
            } else if (edtAddress.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Address is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Integer id = Integer.parseInt(edtId.getText().toString());
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();
                User user = new User(id, name, address, email + tvChartEmail.getText().toString());
                databaseReference.child("Users").child(String.valueOf(id)).setValue(user);
            }
            Toast.makeText(getActivity(), "Post Successs!", Toast.LENGTH_SHORT).show();
        }
    }
}
