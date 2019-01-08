package vn.edu.tdmu.imuseum.adapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.views.R;

/**
 * Created by nvulinh on 8/25/17.
 * Artifact Adapter
 */

public class ArtifactAdapter extends RecyclerView.Adapter<ArtifactAdapter.Viewholder>{

    Context context;
    Artifact artifact;

    public ArtifactAdapter(Context context, Artifact artifact){
        this.context = context;
        this.artifact = artifact;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        public Viewholder(View itemView) {
            super(itemView);
        }
    }
}
