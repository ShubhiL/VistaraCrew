package com.example.sl.vistaracrew;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Activity obj;
    int res;
    ArrayList<ContentFormat> Al= new ArrayList<>();
    ContentFormat s;

    MyAdapter(Activity obj, int res, ArrayList<ContentFormat> Al)
    {
        this.obj=obj;
        this.Al=Al;
        this.res=res;

    }


    @Override
    public int getCount() {
        return Al.size();
    }

    @Override
    public Object getItem(int i) {
        return Al.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vv= LayoutInflater.from(obj).inflate(res,viewGroup,false);

        TextView tv2=(TextView)vv.findViewById(R.id.seat_no);
        TextView tv3= (TextView)vv.findViewById(R.id.request_made);


        s=Al.get(i);
        tv2.setText(s.getSeatNo());
        tv3.setText(s.getRequestMade());

        return vv;

    }
}
