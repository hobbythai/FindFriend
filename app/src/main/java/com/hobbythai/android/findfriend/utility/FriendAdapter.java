package com.hobbythai.android.findfriend.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hobbythai.android.findfriend.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> nameStringArrayList, pathURLStringArrayList;

    public FriendAdapter(Context context, ArrayList<String> nameStringArrayList,
                         ArrayList<String> pathURLStringArrayList) {
        this.context = context;
        this.nameStringArrayList = nameStringArrayList;
        this.pathURLStringArrayList = pathURLStringArrayList;
    }

    @Override
    public int getCount() {
        return nameStringArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.lisview_friend, viewGroup, false);

        TextView textView = view1.findViewById(R.id.txtDisplayName);
        textView.setText(nameStringArrayList.get(i));

        CircleImageView circleImageView = view1.findViewById(R.id.imvAvata);
        Picasso.get().load(pathURLStringArrayList.get(i)).resize(150,150).into(circleImageView);

        return view1;
    }
}
