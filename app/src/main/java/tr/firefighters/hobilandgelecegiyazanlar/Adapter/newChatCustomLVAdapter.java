package tr.firefighters.hobilandgelecegiyazanlar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tr.firefighters.hobilandgelecegiyazanlar.R;
import tr.firefighters.hobilandgelecegiyazanlar.User.UserClass;

/**
 * Created by bthnorhan on 21.04.2017.
 */

public class newChatCustomLVAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<UserClass> mUserList;
    private Context context;

    public newChatCustomLVAdapter(Activity activity, List<UserClass> users, Context context) {
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mUserList = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public UserClass getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView;

        listView = mInflater.inflate(R.layout.custom_listview, null);
        TextView textView = (TextView) listView.findViewById(R.id.custom_lv_email);
        ImageView imageView = (ImageView) listView.findViewById(R.id.custom_lv_imView);

        UserClass user = mUserList.get(position);

        textView.setText(user.getUserEmail());
        Glide.with(context).load(user.getUserPhoto()).into(imageView);

        return listView;
    }
}
