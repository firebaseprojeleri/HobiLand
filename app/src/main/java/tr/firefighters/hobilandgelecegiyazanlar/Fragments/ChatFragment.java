package tr.firefighters.hobilandgelecegiyazanlar.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {

    private Button newChat;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private List<String> chatList;
    private ListView chatListView;
    private ArrayAdapter adapter ;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getFID() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void init() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("Messages/"+mAuth.getCurrentUser().getUid().toString());
        newChat = (Button) getActivity().findViewById(R.id.button_newChat);

        chatList = new ArrayList<>();
        chatListView = (ListView) getActivity().findViewById(R.id.listView_chats);
    }

    @Override
    protected void handlers() {
        newChatListener();
        chatListener();
        chatListViewListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    protected  void newChatListener()
    {
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFragmentChange(FragmentDesignFactory.NEWCHAT);
            }
        });
    }

    protected void chatListener()
    {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("id").getChildrenCount()>0) {
                    try
                    {
                        for (DataSnapshot ds:dataSnapshot.child("id").getChildren() ) {
                            chatList.add(ds.getValue().toString());
                        }
                        if (!chatList.isEmpty())
                        {
                            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,chatList);
                            chatListView.setAdapter(adapter);
                        }
                    }
                    catch (Exception e)
                    {
                        throw e;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void chatListViewListener()
    {
        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("to",(String) parent.getAdapter().getItem(position));
                listener.onFragmentChange(FragmentDesignFactory.MESSAGING,bundle);
            }
        });
    }
}
