package com.example.work.demofirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class UserListFragment extends Fragment {

    @BindView(R.id.pb_waitShowData)
    ProgressBar pbWaitShowData;
    @BindView(R.id.rcv_userlist)
    RecyclerView rcvUserlist;

    private DatabaseReference databaseReference;
    private Adapter adapter;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userlist, container, false);
        ButterKnife.bind(this, view);

        pbWaitShowData.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        rcvUserlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

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
                pbWaitShowData.setVisibility(View.GONE);
                adapter = new Adapter(getActivity(), userList);
                rcvUserlist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Show Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
