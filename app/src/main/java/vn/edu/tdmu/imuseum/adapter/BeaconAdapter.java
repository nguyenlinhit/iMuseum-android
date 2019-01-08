package vn.edu.tdmu.imuseum.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.recognition.packets.Beacon;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vn.edu.tdmu.imuseum.views.R;

/**
 * Created by nvulinh on 9/19/17.
 * Nguyễn Vũ Linh
 */

public class BeaconAdapter extends BaseAdapter{
    private List<Beacon> beaconIDs;
    private LayoutInflater inflater;

    public BeaconAdapter(Activity context){
        this.beaconIDs = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void replaceWith(Collection<Beacon> newBeacons){
        this.beaconIDs.clear();
        this.beaconIDs.addAll(newBeacons);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beaconIDs.size();
    }

    @Override
    public Object getItem(int i) {
        return beaconIDs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflaterIfRequired(view);
        bind((Beacon) getItem(i),view);
        return view;
    }

    private View inflaterIfRequired(View view){
        if (view == null){
            view = inflater.inflate(R.layout.view_beacon_item, null);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    private void bind(final Beacon beacon, View view){
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtMacAddress.setText(String.format("Mac Address: %s", beacon.getMacAddress().toStandardString()));
        holder.txtDistance.setText(String.format("Distance: (%.2fm)", RegionUtils.computeAccuracy(beacon)));
        holder.txtMajor.setText("Major: " + beacon.getMajor());
        holder.txtMinor.setText("Minor: " + beacon.getMinor());

    }

    static class ViewHolder{
        TextView txtMacAddress;
        TextView txtDistance;
        TextView txtMajor;
        TextView txtMinor;
        ViewHolder(View view){
            txtDistance = view.findViewById(R.id.tv_distance);
            txtMacAddress = view.findViewById(R.id.tv_mac_address);
            txtMajor = view.findViewById(R.id.tv_major);
            txtMinor = view.findViewById(R.id.tv_minor);
        }
    }
}
