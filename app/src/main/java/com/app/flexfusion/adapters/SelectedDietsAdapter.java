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
import com.app.flexfusion.models.CurentDietsItems;
import com.app.flexfusion.repositories.DatabaseHelper;

import java.util.List;

public class SelectedDietsAdapter extends RecyclerView.Adapter<SelectedDietsAdapter.ViewHolder> {
    Context context;
    List<CurentDietsItems> list;
    DatabaseHelper databaseHelper;
    OnDeleteClickedListener onDeleteClickedListener;
    String subCategory;

    public SelectedDietsAdapter(Context context, List<CurentDietsItems> list, String subCategory, OnDeleteClickedListener onDeleteClickedListener) {
        this.context = context;
        this.list = list;
        databaseHelper = new DatabaseHelper();
        this.onDeleteClickedListener = onDeleteClickedListener;
        this.subCategory = subCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.selected_diets_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurentDietsItems model = list.get(position);
        holder.tv_diet_name.setText(model.getName());
        holder.tv_selected_size.setText(model.getSize());
        holder.tvCal.setText("" + model.getCalories());
        holder.iv_cancel.setImageResource(R.drawable.icon_delete);

        holder.iv_cancel.setOnClickListener(v -> onDeleteClickedListener.onDeleteClicked(subCategory, model.getFirebaseKey(), model.getCalories()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnDeleteClickedListener {
        void onDeleteClicked(String subCategory, String firebaseKey, int calories);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_diet_name;
        TextView tv_selected_size;
        TextView tvCal;
        ImageView iv_cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_diet_name = itemView.findViewById(R.id.tv_diet_name);
            tv_selected_size = itemView.findViewById(R.id.tv_selected_size);
            tvCal = itemView.findViewById(R.id.tvCal);
            iv_cancel = itemView.findViewById(R.id.ic_cancel);
        }
    }
}
