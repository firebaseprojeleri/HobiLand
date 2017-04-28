package tr.firefighters.hobilandgelecegiyazanlar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.Comment.CommentClass;
import tr.firefighters.hobilandgelecegiyazanlar.R;

/**
 * Created by eniserkaya on 19.04.2017.
 */

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {


    public Context context;

    public interface ICardViewClickListener{
        void CardViewClick(int position);
    }

    public ICardViewClickListener cardListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView userComment;
        public ImageView userPhoto;
        public CardView cardView;
        public TextView likeCount;
        public ImageView heartView;
        public RelativeLayout rLayout;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardView);
            userName = (TextView)view.findViewById(R.id.userName);
            userComment = (TextView)view.findViewById(R.id.userComment);
            userPhoto = (ImageView)view.findViewById(R.id.profilePhoto);
            likeCount = (TextView)view.findViewById(R.id.countId);
            heartView = (android.widget.ImageView)view.findViewById(R.id.heartId);
            rLayout = (RelativeLayout)view.findViewById(R.id.heartContainer);
        }
    }

    List<CommentClass> listComment;
    public CommentRecyclerAdapter(List<CommentClass> listComment, ICardViewClickListener listener, Context context) {
        this.cardListener = listener;
        this.listComment = listComment;
        this.context=context;
    }


    @Override
    public CommentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.userName.setText(listComment.get(position).getUserName());
        holder.userComment.setText(listComment.get(position).getUserComment());

        if(listComment.get(position).getLikes().size()==1){
            holder.likeCount.setText("0");
            holder.heartView.setImageResource(R.drawable.ic_notliked);
        }
        else{
            holder.likeCount.setText(String.valueOf(listComment.get(position).getLikes().size()-1));
            holder.heartView.setImageResource(R.drawable.ic_liked);

        }
        Glide.with(context).load(listComment.get(position).getPhotoUrl()).into(holder.userPhoto);
        holder.heartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardListener.CardViewClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}