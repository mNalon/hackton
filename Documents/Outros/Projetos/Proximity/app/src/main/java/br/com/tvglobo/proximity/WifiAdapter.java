package br.com.tvglobo.proximity;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.tvglobo.proximity.Classes.Wifi;

/**
 * Created by danielventura on 29/12/15.
 */
public class WifiAdapter extends BaseAdapter {
    Wifi [] connections;
    Context context;
    int [] imageId;

    public WifiAdapter(Context _context, ArrayList<Wifi> _connections) {
        connections = new Wifi[_connections.size()];
        connections = _connections.toArray(connections);

        context=_context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    private static LayoutInflater inflater=null;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return connections.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView _ssid;
        TextView _level;
        ImageView _strenght;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.wifi_item, null);
        holder._ssid=(TextView) rowView.findViewById(R.id.ssid);
        holder._ssid.setText(connections[position].SSID);
        holder._level=(TextView) rowView.findViewById(R.id.level);
        holder._level.setText(String.valueOf(connections[position].level));
        holder._strenght = (ImageView) rowView.findViewById(R.id.strength);
        if(connections[position].level > -80 && connections[position].level <= -70)
            holder._strenght.setImageResource(R.drawable.battery82);
        else if(connections[position].level > -70 && connections[position].level <= -60)
            holder._strenght.setImageResource(R.drawable.battery82);
        else if(connections[position].level > -60 && connections[position].level <= -50)
            holder._strenght.setImageResource(R.drawable.battery82);
        else if(connections[position].level > -50 && connections[position].level <= -40)
            holder._strenght.setImageResource(R.drawable.battery82);
        else if(connections[position].level > -40)
            holder._strenght.setImageResource(R.drawable.battery84);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + connections[position].SSID, Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
