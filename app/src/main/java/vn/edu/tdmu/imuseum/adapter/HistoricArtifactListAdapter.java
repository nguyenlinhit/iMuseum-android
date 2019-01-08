package vn.edu.tdmu.imuseum.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.estimote.coresdk.recognition.packets.Beacon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.ultils.Server;
import vn.edu.tdmu.imuseum.views.ArtifactDetailActivity;
import vn.edu.tdmu.imuseum.views.R;

/**
 * Created by nvulinh on 11/8/17.
 * Nguyen Vu Linh
 */

public class HistoricArtifactListAdapter extends RecyclerView.Adapter<HistoricArtifactListAdapter.ViewHolder> {

    private List<Beacon> baBeacons = new ArrayList<>();
    private Context context;
    private List<Artifact> artifactArrayList;

    public HistoricArtifactListAdapter(Context context, List<Artifact> artifactArrayList){
        this.context = context;
        this.artifactArrayList = artifactArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_list_view, parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Artifact objArtifact = artifactArrayList.get(position);
        if (objArtifact != null) {
            holder.tv_artifactname.setText(objArtifact.getNameArtifact());
            holder.tv_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ArtifactDetailActivity.class);
                    intent.putExtra("idArtifact", objArtifact.getIdArtifact());
                    intent.putExtra("major", objArtifact.getBeaconArtifact().getId());
                    context.startActivity(intent);
                }
            });
            Glide.with(this.context).load(Server.LOCALHOST + "/" + (objArtifact.getMedias().get(0).getMedia())).into(holder.iv_icon);
        }else {
            holder.tv_artifactname.setText(this.context.getString(R.string.no_name));
            Glide.with(this.context).load(R.drawable.no_image).into(holder.iv_icon);
        }

    }

    public void replaceWith(Collection<Beacon> newBeacons) {
        this.baBeacons.clear();
        this.baBeacons.addAll(newBeacons);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return this.artifactArrayList.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder{
         ImageView iv_icon;
         TextView tv_artifactname;
         TextView tv_description;

         ViewHolder(View itemView) {
            super(itemView);
            this.iv_icon = itemView.findViewById(R.id.iv_icon);
            this.tv_artifactname = itemView.findViewById(R.id.tv_artifactname);
            this.tv_description = itemView.findViewById(R.id.tv_description);

        }

    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    public List<Beacon> getBaBeacons() {
        return this.baBeacons;
    }



}

