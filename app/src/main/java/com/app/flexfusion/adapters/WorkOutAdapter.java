package com.app.flexfusion.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.flexfusion.R;
import com.app.flexfusion.activities.DetailActivity;
import com.app.flexfusion.models.WorkOutModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WorkOutAdapter extends RecyclerView.Adapter<WorkOutAdapter.ViewHolder> {
    List<WorkOutModel> workOutModelList;
    Context context;
    String title;

    public WorkOutAdapter(List<WorkOutModel> workOutModelList, Context context, String title) {
        this.workOutModelList = workOutModelList;
        this.context = context;
        this.title = title;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_view_gym, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkOutModel workOutModel = workOutModelList.get(position);
        holder.tvTitle.setText(workOutModel.getWorkOutName());
        Picasso.get().load(workOutModel.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, DetailActivity.class).putExtra("data", workOutModel).putExtra("title", title));
        });
    }

    @Override
    public int getItemCount() {
        return workOutModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
