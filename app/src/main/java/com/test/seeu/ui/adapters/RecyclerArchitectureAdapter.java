package com.test.seeu.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.seeu.R;
import com.test.seeu.data.FirebaseHelper;
import com.test.seeu.data.models.ArchitectureModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerArchitectureAdapter extends RecyclerView.Adapter<RecyclerArchitectureAdapter.ArchitectureViewHolder> {

    private List<ArchitectureModel> architectureList = new ArrayList();

    public void setArchitectureList(List<ArchitectureModel> architectureList) {
        this.architectureList = architectureList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerArchitectureAdapter.ArchitectureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container, parent, false);
        RecyclerArchitectureAdapter.ArchitectureViewHolder holder = new RecyclerArchitectureAdapter.ArchitectureViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerArchitectureAdapter.ArchitectureViewHolder holder, int position) {
        holder.onBind(architectureList.get(position));
    }

    @Override
    public int getItemCount() {
        return architectureList.size();
    }

    public class ArchitectureViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtAuthor;
        private TextView txtDetails;
        private ImageView imgPainting;

        public ArchitectureViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDetails = itemView.findViewById(R.id.txtDetails);
            imgPainting = itemView.findViewById(R.id.imgPainting);
        }

        public void onBind(ArchitectureModel architectureModel) {
            txtName.setText(architectureModel.getName());
            txtAuthor.setText(architectureModel.getAuthor());
            txtDetails.setText(architectureModel.getPreviewInfo());
            FirebaseHelper.getInstance()
                    .getReference(architectureModel.getPhoto())
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide.with(imgPainting.getContext())
                            .asBitmap()
                            .load(uri)
                            .into(imgPainting))
                    .addOnFailureListener(e -> Log.e("Firebase storage:",e.getLocalizedMessage()));
        }
    }
}
