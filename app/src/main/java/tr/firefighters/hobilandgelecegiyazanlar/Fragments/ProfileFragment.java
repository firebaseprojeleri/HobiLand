package tr.firefighters.hobilandgelecegiyazanlar.Fragments;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.R;

import static android.R.attr.data;
import static android.R.attr.privateImeOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment{
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private TextView userCardViewEmail;
    private ImageView userImageView;
    private List<String> dataList;
    private ArrayAdapter adapter;

    public ProfileFragment() {}

    @Override
    protected int getFID() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void init() {
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userCardViewEmail = (TextView) getActivity().findViewById(R.id.userCardViewEmail);
        userImageView =(ImageView) getActivity().findViewById(R.id.userCardViewIV);
        dataList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dataList);
        ListView listView = (ListView) getActivity().findViewById(R.id.userHobbies);
        listView.setAdapter(adapter);
        getProfile();
    }

    private void getProfile() {
        dbRef= db.getReference("Profiles/"+mAuth.getCurrentUser().getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userCardViewEmail.setText(dataSnapshot.child("userEmail").getValue().toString());
                Glide.with(getActivity()).load(dataSnapshot.child("userPhoto").getValue().toString()).into(userImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRef=db.getReference("UsersHobbies/"+mAuth.getCurrentUser().getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> data = dataSnapshot.getChildren();
                if(dataSnapshot.getChildrenCount()>0) {
                    dataList.clear();
                    for (DataSnapshot ds : data) {
                        dataList.add(ds.getValue().toString());
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    dataList.add("Takip edilen hobi yok.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void handlers() {}

}
