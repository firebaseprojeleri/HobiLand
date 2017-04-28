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
import tr.firefighters.hobilandgelecegiyazanlar.Message.MessageClass;
import tr.firefighters.hobilandgelecegiyazanlar.R;

/**
 * Created by bthnorhan on 26.04.2017.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView userMessage;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardView);
            userName = (TextView)view.findViewById(R.id.userName);
            userMessage = (TextView)view.findViewById(R.id.userMessage);
        }
    }

    List<MessageClass> messageList;
    public MessageRecyclerAdapter(List<MessageClass> messageList) {
        this.messageList = messageList;
    }

    @Override
    public MessageRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageRecyclerAdapter.ViewHolder holder, int position) {
        holder.userName.setText(messageList.get(position).getUser());
        holder.userMessage.setText(messageList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
