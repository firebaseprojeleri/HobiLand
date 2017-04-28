package tr.firefighters.hobilandgelecegiyazanlar.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.Comment.CommentClass;
import tr.firefighters.hobilandgelecegiyazanlar.Hobbie.HobbieClass;
import tr.firefighters.hobilandgelecegiyazanlar.R;

/**
 * Created by eniserkaya on 20.04.2017.
 */

public class HobbiesRecyclerAdapter extends RecyclerView.Adapter<HobbiesRecyclerAdapter.ViewHolder> {

    public interface IFollowButtonClickListener{
        void FollowButtonClick(int position);
    }

    public IFollowButtonClickListener followButtonListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hobbieName;
        public ImageView followImage;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardView);
            hobbieName = (TextView)view.findViewById(R.id.hobbieid);
            followImage = (ImageView)view.findViewById(R.id.followimageid);
        }
    }

    List<HobbieClass> hobbieList;
    public HobbiesRecyclerAdapter(List<HobbieClass> hobbieList, IFollowButtonClickListener listener) {
        this.followButtonListener = listener;
        this.hobbieList = hobbieList;

    }


    @Override
    public HobbiesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobbies_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.hobbieName.setText(hobbieList.get(position).getHobbieName());
        if(hobbieList.get(position).isSelected())
            holder.followImage.setBackgroundResource(R.drawable.ic_unfollow);
        else
            holder.followImage.setBackgroundResource(R.drawable.ic_follow);

        holder.followImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followButtonListener.FollowButtonClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return hobbieList.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}