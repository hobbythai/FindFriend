package com.hobbythai.android.findfriend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hobbythai.android.findfriend.MainActivity;
import com.hobbythai.android.findfriend.R;
import com.hobbythai.android.findfriend.utility.FriendAdapter;
import com.hobbythai.android.findfriend.utility.UserModel;

import java.util.ArrayList;

public class ServiceFragment extends Fragment {

    private String displayNameString, uidUserLoggedinString;
    private ArrayList<String> uidFriendStringArrayList, nameStringArrayList, pathStringArrayList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create toolbar
        createToolbar();

//        find member of friend
        findMemberOfFriend();


    } //main method

    private void findMemberOfFriend() {

        uidFriendStringArrayList = new ArrayList<>();
        nameStringArrayList = new ArrayList<>();
        pathStringArrayList = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Log.d("6MayV1", "data = " + dataSnapshot1.toString());

                    if (!uidUserLoggedinString.equals(dataSnapshot1.getKey())) {
                        uidFriendStringArrayList.add(dataSnapshot1.getKey());
                    }

                } //for

                Log.d("6MayV1", "uid = "+uidFriendStringArrayList.toString());
                //uid ok (no my)

                CreateListView();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void CreateListView() {

        final int[] ints = new int[]{0};

        for (int i=0;i<uidFriendStringArrayList.size();i++) {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference()
                    .child(uidFriendStringArrayList.get(i));

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.d("6MayV2", "data snap = " + dataSnapshot.toString());
                    //ok data in child got
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                    nameStringArrayList.add(userModel.getNameString());
                    pathStringArrayList.add(userModel.getPathAvataString());

                    if (ints[0]==(uidFriendStringArrayList.size()-1)) {
                        Log.d("6MayV2", "Friend = " + nameStringArrayList.toString());
                        Log.d("6MayV2", "path = " + pathStringArrayList.toString());

                        FriendAdapter friendAdapter = new FriendAdapter(getActivity(),
                                nameStringArrayList, pathStringArrayList);

                        ListView listView = getView().findViewById(R.id.listViewFriend);
                        listView.setAdapter(friendAdapter);

                    }

                    ints[0]++;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } //for



    } // create list view

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSignOut) {
            signOutFirebase();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOutFirebase() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        // go back main fragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentMainFragment, new MainFragment())
                .commit();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_service,menu);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarService);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("My All Friend");

//        show display name on subtitel toolbar
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        displayNameString = firebaseUser.getDisplayName();
        uidUserLoggedinString = firebaseUser.getUid(); //my id

        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(displayNameString);

        setHasOptionsMenu(true); //set menu

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service, container, false);

        return view;
    }
}
