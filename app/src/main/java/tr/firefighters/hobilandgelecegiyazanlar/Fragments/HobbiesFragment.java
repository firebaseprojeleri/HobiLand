package tr.firefighters.hobilandgelecegiyazanlar.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.Adapter.HobbiesRecyclerAdapter;
import tr.firefighters.hobilandgelecegiyazanlar.Hobbie.HobbieClass;
import tr.firefighters.hobilandgelecegiyazanlar.R;




public class HobbiesFragment extends BaseFragment implements HobbiesRecyclerAdapter.IFollowButtonClickListener {

    private RecyclerView recyclerView;
    private List<HobbieClass> hobbieList;
    private List<HobbieClass> hobbieListUsers;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private HobbiesRecyclerAdapter adapterItems;
    private Button btnSee;

    private FirebaseAuth mAuth;

    @Override
    protected int getFID() {
        return R.layout.fragment_hobbies;
    }

    @Override
    protected void init() {
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.hobbiesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        btnSee =(Button)getActivity().findViewById(R.id.button_see);
        hobbieList = new ArrayList<HobbieClass>();
        hobbieListUsers=new ArrayList<HobbieClass>();

        hobbieList.add(new HobbieClass("Yükleniyor...",false,""));
        adapterItems = new HobbiesRecyclerAdapter(hobbieList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterItems);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getHobbies();
    }
    private void getHobbies() {
        dbRef = db.getReference("Hobbies/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hobbieList.clear();
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for(DataSnapshot key: keys) {
                    hobbieList.add(new HobbieClass(key.getValue().toString(),false,key.getKey()));
                }
                getUserHobbies();
                adapterItems.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getUserHobbies() {
        dbRef = db.getReference("UsersHobbies/"+mAuth.getCurrentUser().getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hobbieListUsers.clear();
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for (DataSnapshot key : keys) {
                    hobbieListUsers.add(new HobbieClass(key.getValue().toString(), true, key.getKey()));

                }
                compareToHobbieLists();
                adapterItems.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void compareToHobbieLists() {
        for(int i=0;i<hobbieList.size();i++)
            for (int j = 0; j < hobbieListUsers.size(); j++)
                if (Integer.valueOf(hobbieList.get(i).getKey()) == Integer.valueOf(hobbieListUsers.get(j).getKey()))
                    if (hobbieListUsers.get(j).isSelected())
                        hobbieList.get(i).setSelected(true);
        adapterItems.notifyDataSetChanged();
    }

    @Override
    protected void handlers() {
        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFragmentChange(FragmentDesignFactory.HSCOMMENTS);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void FollowButtonClick(int position) {

        if(!hobbieList.get(position).isSelected()) {
            hobbieList.get(position).setSelected(true);
            sendHobbieToFirebase(hobbieList.get(position).getHobbieName(),position);
        }
        else{

            hobbieList.get(position).setSelected(false);
            deleteFirebase(position);
        }
        adapterItems.notifyDataSetChanged();
    }

    private void deleteFirebase(int position) {
        DatabaseReference sendHobbieRef = db.getReference("UsersHobbies/"+mAuth.getCurrentUser().getUid()+"/"+(position+1));
        sendHobbieRef.removeValue();
    }

    private void sendHobbieToFirebase(String hobbieName, int position) {
        DatabaseReference sendHobbieRef = db.getReference("UsersHobbies/"+mAuth.getCurrentUser().getUid()+"/"+(position+1));
        sendHobbieRef.setValue(hobbieName);
    }

}
