package tr.firefighters.hobilandgelecegiyazanlar.Fragments;


import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.Adapter.CommentRecyclerAdapter;
import tr.firefighters.hobilandgelecegiyazanlar.Adapter.UserHobbiesRecyclerAdapter;
import tr.firefighters.hobilandgelecegiyazanlar.Comment.CommentClass;
import tr.firefighters.hobilandgelecegiyazanlar.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class UCommentFragment extends BaseFragment implements CommentRecyclerAdapter.ICardViewClickListener {

    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private List<CommentClass> commentList;
    private CommentRecyclerAdapter adapterItems;
    private RecyclerView recyclerView;
    private ArrayList<String> likesArray;

    private  boolean commentRefresh;

    private EditText commentEt;
    private ImageButton commentImgBtn;

    private ArrayList<String> keys;

    private String keyHobbie;


    public static synchronized UCommentFragment newInstance(Bundle bundle)
    {
        UCommentFragment uCommentFragment = new UCommentFragment();
        uCommentFragment.setArguments(bundle);
        return uCommentFragment;
    }


    @Override
    protected int getFID() {
        return R.layout.fragment_ucomment;
    }

    @Override
    protected void init() {

        if(getArguments()!=null)
        {
            mAuth=FirebaseAuth.getInstance();
            db=FirebaseDatabase.getInstance();

            keys = new ArrayList<String>();

            commentEt = (EditText)getActivity().findViewById(R.id.mesajEtId);
            commentImgBtn=(ImageButton)getActivity().findViewById(R.id.imageButton);


            likesArray = new ArrayList<String>();
            likesArray.add("0");
            keyHobbie =getArguments().getString("hobbieid");
            commentList=new ArrayList<CommentClass>();
            recyclerView = (RecyclerView) getActivity().findViewById(R.id.commentRecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            recyclerView.setLayoutManager(layoutManager);
            commentList.add(new CommentClass("Yükleniyor...","","",likesArray,""));
            adapterItems = new CommentRecyclerAdapter(commentList,this,getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapterItems);

            getComments();
        }
        else
        {
            Toast.makeText(getContext(), "Key boş geliyor...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void handlers() {

        commentImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp=commentEt.getText().toString().trim();
                if(!temp.isEmpty()) {
                    DatabaseReference dbRefComment;
                    dbRefComment = db.getReference("Comments/" + keyHobbie);
                    Uri photoUri = mAuth.getCurrentUser().getPhotoUrl();
                    if(photoUri!=null){
                    dbRefComment.push().
                            setValue(new CommentClass(temp,mAuth.getCurrentUser().getUid(),
                            mAuth.getCurrentUser().getEmail(),
                            likesArray, photoUri.toString()));
                    }
                    else
                    {
                        dbRefComment.push().
                            setValue(new CommentClass(temp,mAuth.getCurrentUser().getUid(),
                                    mAuth.getCurrentUser().getEmail(),
                                    likesArray,
                                    "http://simpleicon.com/wp-content/uploads/user-2.png"));

                    }
                    commentRefresh=true;
                    commentEt.setText("");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ucomment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getComments() {
        dbRef= db.getReference("Comments/"+keyHobbie);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                keys.clear();
                for (DataSnapshot comments: dataSnapshot.getChildren()) {
                    commentList.add(comments.getValue(CommentClass.class));
                    keys.add(comments.getKey());

                }
                adapterItems.notifyDataSetChanged();
                if(commentRefresh)
                    recyclerView.smoothScrollToPosition(adapterItems.getItemCount()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void CardViewClick(int position) {
        DatabaseReference dbRefAddLike = db.getReference("Comments/"+keyHobbie+"/"+keys.get(position).toString());

        if(!commentList.get(position).getLikes().contains(mAuth.getCurrentUser().getUid())){
            commentList.get(position).getLikes().add(mAuth.getCurrentUser().getUid());
            dbRefAddLike.setValue(commentList.get(position));
            adapterItems.notifyDataSetChanged();
        }
        else{

            commentList.get(position).getLikes().remove(getIndexLikeUser(mAuth.getCurrentUser().getUid(),commentList.get(position).getLikes()));
            dbRefAddLike.setValue(commentList.get(position));
            adapterItems.notifyDataSetChanged();
        }

    }
    private int getIndexLikeUser(String category,ArrayList<String> arrayLst) {
        return arrayLst.indexOf(category);
    }

}
