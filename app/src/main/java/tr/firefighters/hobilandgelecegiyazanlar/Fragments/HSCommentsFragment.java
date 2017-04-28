package tr.firefighters.hobilandgelecegiyazanlar.Fragments;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.Adapter.UserHobbiesRecyclerAdapter;
import tr.firefighters.hobilandgelecegiyazanlar.Hobbie.HobbieClass;
import tr.firefighters.hobilandgelecegiyazanlar.MainActivity;
import tr.firefighters.hobilandgelecegiyazanlar.R;
import tr.firefighters.hobilandgelecegiyazanlar.Adapter.CommentRecyclerAdapter;
import tr.firefighters.hobilandgelecegiyazanlar.Comment.CommentClass;

import static android.R.attr.data;
import static android.R.attr.key;

public class HSCommentsFragment extends BaseFragment implements UserHobbiesRecyclerAdapter.ICardViewClickListenerUser {

    private RecyclerView recyclerView;
    private List<HobbieClass> hobbieList;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private UserHobbiesRecyclerAdapter adapterItems;
    private FirebaseAuth mAuth;


    public HSCommentsFragment() {}

    private Bundle bundle ;

    @Override
    protected int getFID() {
        return R.layout.fragment_hscomments;
    }

    @Override
    protected void init() {
        db = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        getHobbies();
        bundle = new Bundle();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.commentRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        hobbieList = new ArrayList<HobbieClass>();
        hobbieList.add(new HobbieClass("Yükleniyor...",false,""));
        adapterItems = new UserHobbiesRecyclerAdapter(hobbieList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterItems);


       // listener.onFragmentChange(FragmentDesignFactory.UCOMMENTS,bundle);
    }

   private void getHobbies(){
       dbRef= db.getReference("UsersHobbies/"+mAuth.getCurrentUser().getUid()+"/");
       dbRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
               hobbieList.clear();
               for(DataSnapshot key: keys) {
                    if(!key.getValue().toString().isEmpty())
                        hobbieList.add(new HobbieClass(key.getValue().toString(),false,key.getKey()));
               }
               adapterItems.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

   }

    @Override
    protected void handlers() {}

    @Override
    public void OnCardClick(int position) {
        bundle.putString("hobbieid",hobbieList.get(position).getKey());
        listener.onFragmentChange(FragmentDesignFactory.UCOMMENTS,bundle);
    }
}
