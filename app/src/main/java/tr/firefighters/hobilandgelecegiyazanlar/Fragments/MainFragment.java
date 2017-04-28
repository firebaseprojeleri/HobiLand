package tr.firefighters.hobilandgelecegiyazanlar.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tr.firefighters.hobilandgelecegiyazanlar.R;


public class MainFragment extends BaseFragment {

    private Button signOut;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;


    public MainFragment() {}


    @Override
    protected int getFID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void handlers() {
        userControl();
        listener.onFragmentChange(FragmentDesignFactory.HOBBIES);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    protected void userControl()
    {
        final tempUser tUser = new tempUser();
        tUser.setUserId(mAuth.getCurrentUser().getUid());
        tUser.setUserEmail(mAuth.getCurrentUser().getEmail());
        if(mAuth.getCurrentUser().getPhotoUrl()!=null)
        {
            tUser.setUserPhoto(mAuth.getCurrentUser().getPhotoUrl().toString());
        }
        else
        {
            tUser.setUserPhoto("http://pngimages.net/sites/default/files/user-png-image-65995.png");
        }

        dbRef= db.getReference("Profiles/").child(mAuth.getCurrentUser().getUid().toString());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()==0)
                {
                    dbRef.setValue(tUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
class tempUser
{
    private String UserId;
    private String userEmail;
    private String userPhoto;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
