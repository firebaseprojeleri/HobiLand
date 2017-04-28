package tr.firefighters.hobilandgelecegiyazanlar.Fragments;


import android.content.Context;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.Adapter.newChatCustomLVAdapter;
import tr.firefighters.hobilandgelecegiyazanlar.R;
import tr.firefighters.hobilandgelecegiyazanlar.User.UserClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class newChatFragment extends BaseFragment {
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private List<UserClass> listUser;
    ListView listView ;

    public newChatFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getFID() {
        return R.layout.fragment_new_chat;
    }

    @Override
    protected void init() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        getAllUser();
    }

    protected  void getAllUser()
    {
        dbRef = db.getReference("Profiles/");
        listUser = new ArrayList<>();
        listView =(ListView) getActivity().findViewById(R.id.newChatLV);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    UserClass user = ds.getValue(UserClass.class);
                    if(!user.getUserEmail().equalsIgnoreCase(mAuth.getCurrentUser().getEmail())){listUser.add(user);}
                }
                newChatCustomLVAdapter customAdapter =new newChatCustomLVAdapter(getActivity(), listUser,getContext());
                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void handlers() {
        lvListener();
    }

    public void lvListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserClass user =(UserClass) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("to",user.getUserId().toString());
                listener.onFragmentChange(FragmentDesignFactory.MESSAGING,bundle);
            }
        });
    }

}
