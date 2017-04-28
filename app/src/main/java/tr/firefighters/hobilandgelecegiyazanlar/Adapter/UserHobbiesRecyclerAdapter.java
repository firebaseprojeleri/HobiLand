package tr.firefighters.hobilandgelecegiyazanlar.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.Hobbie.HobbieClass;
import tr.firefighters.hobilandgelecegiyazanlar.R;

/**
 * Created by eniserkaya on 20.04.2017.
 */

public class UserHobbiesRecyclerAdapter extends RecyclerView.Adapter<UserHobbiesRecyclerAdapter.ViewHolder> {

    public interface ICardViewClickListenerUser{
        void OnCardClick(int position);
    }

    public ICardViewClickListenerUser cardClickListenerUser;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hobbieName;
        public ImageView ImageView;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardView);
            hobbieName = (TextView)view.findViewById(R.id.hobbieid);
            ImageView = (ImageView)view.findViewById(R.id.followimageid);
        }
    }

    List<HobbieClass> hobbieList;
    public UserHobbiesRecyclerAdapter(List<HobbieClass> hobbieList, ICardViewClickListenerUser listener) {
        this.cardClickListenerUser = listener;
        this.hobbieList = hobbieList;
    }


    @Override
    public UserHobbiesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobbies_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.hobbieName.setText(hobbieList.get(position).getHobbieName());
        holder.ImageView.setImageResource(R.drawable.ic_arrow);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardClickListenerUser.OnCardClick(holder.getAdapterPosition());
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