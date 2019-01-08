package vn.edu.tdmu.imuseum.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import vn.edu.tdmu.imuseum.model.ArtifactSave;
import vn.edu.tdmu.imuseum.views.ArtifactDetailSaveActivity;
import vn.edu.tdmu.imuseum.views.R;

/**
 * Created by nvulinh on 11/20/17.
 *
 */

public class ArtifactSaveAdapter extends RecyclerView.Adapter<ArtifactSaveAdapter.ViewHolder>{

    Context context;
    List<ArtifactSave> artifactSaves;

    public ArtifactSaveAdapter(Context context, List<ArtifactSave> artifactSaves) {
        this.context = context;
        this.artifactSaves = artifactSaves;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ArtifactSave objArtifactSave = artifactSaves.get(position);
        if (objArtifactSave != null){
            holder.tv_artifactname.setText(objArtifactSave.getName());
            holder.tv_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ArtifactDetailSaveActivity.class);
                    i.putExtra("idArtifact", objArtifactSave.getId());
                    context.startActivity(i);
                }
            });
            //Bitmap bitmap  = BitmapFactory.decodeByteArray(objArtifactSave.getImage(), 0, objArtifactSave.getImage().length);
            //holder.iv_icon.setImageBitmap(bitmap);
            Log.e("IMAGE_PATH_PNG", objArtifactSave.getImage());
            try {
                File f=new File(objArtifactSave.getImage());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.iv_icon.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }
    }



    @Override
    public int getItemCount() {
        return this.artifactSaves.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

         ImageView iv_icon;
         TextView tv_artifactname;
         TextView tv_description;

        public ViewHolder(View itemView) {
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
}
