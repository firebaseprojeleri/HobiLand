package tr.firefighters.hobilandgelecegiyazanlar.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.firefighters.hobilandgelecegiyazanlar.Adapter.MessageRecyclerAdapter;
import tr.firefighters.hobilandgelecegiyazanlar.Message.MessageClass;
import tr.firefighters.hobilandgelecegiyazanlar.R;


public class MessagingFragment extends BaseFragment {
    public static String bKey = "to";
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef,dbRef2,dbmes;
    private ImageView imageViewSendIcon;
    private EditText editTextMessage;
    private ScrollView scrollView;
    private String otherUUID;
    private MessageRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<MessageClass> messageList;


    public MessagingFragment() {}

    public static MessagingFragment newInstance(Bundle bundle) {
        MessagingFragment myFragment = new MessagingFragment();
        Bundle args = new Bundle();
        args.putBundle(bKey, bundle);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    protected int getFID() {
        return R.layout.fragment_messaging;
    }

    @Override
    protected void init() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        Bundle bundle = getArguments().getBundle(bKey);
        otherUUID = bundle.getString(bKey);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.messagesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        messageList = new ArrayList<MessageClass>();
        adapter = new MessageRecyclerAdapter(messageList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        imageViewSendIcon = (ImageView) getActivity().findViewById(R.id.imageViewSendIcon);
        editTextMessage = (EditText) getActivity().findViewById(R.id.editTextMessage);
        scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
    }

    @Override
    protected void handlers() {
        sendButtonListener();
        messageListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messaging, container, false);
    }

    protected void sendButtonListener()
    {
        imageViewSendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = db.getReference("Messages/"+mAuth.getCurrentUser().getUid()+"/");
                dbRef2 = db.getReference("Messages/"+otherUUID+"/");
                String messageText = editTextMessage.getText().toString();
                editTextMessage.setText("");

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<>();
                    map.put("message", messageText);
                    map.put("user", mAuth.getCurrentUser().getUid());
                    dbRef.child(otherUUID).push().setValue(map);
                    dbRef2.child(mAuth.getCurrentUser().getUid()).push().setValue(map);
                    dbRef.child("id").child(otherUUID).setValue(otherUUID);
                    dbRef2.child("id").child(mAuth.getCurrentUser().getUid().toString()).setValue(mAuth.getCurrentUser().getUid().toString());
                }
            }
        });
    }

    protected void messageListener()
    {
        dbmes = db.getReference("Messages/"+ mAuth.getCurrentUser().getUid()+"/"+otherUUID+"/");
        dbmes.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(mAuth.getCurrentUser().getUid().toString())){
                    messageList.add(new MessageClass(message,"Sen :"));
                }
                else{
                    messageList.add(new MessageClass(message,userName + " :"));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

