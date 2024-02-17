package com.app.flexfusion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.flexfusion.R;
import com.app.flexfusion.models.DietItems;

import java.util.List;

public class DietsAdapter extends RecyclerView.Adapter<DietsAdapter.ViewHolder> {

    Context context;
    List<DietItems>list;

    public DietsAdapter(Context context, List<DietItems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.diets_add_row,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietItems items=list.get(position);
        holder.tvName.setText(items.getName());
        holder.tvSize.setText(items.getSize());
        holder.tvCalories.setText(items.getCalories());
        holder.ivAdd.setImageResource(R.drawable.icon_add_green);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvSize;
        TextView tvCalories;
        ImageView ivAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvSize=itemView.findViewById(R.id.tv_size);
            tvCalories=itemView.findViewById(R.id.tvCalories);
            ivAdd=itemView.findViewById(R.id.ic_add);
        }
    }
}
