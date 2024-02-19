package com.app.flexfusion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.flexfusion.R;
import com.app.flexfusion.fragments.DietPlansFragment;
import com.app.flexfusion.models.CurentDietsItems;
import com.app.flexfusion.models.DietItems;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DietsAdapter extends RecyclerView.Adapter<DietsAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<DietItems> originalList;
    private List<DietItems> filteredList;
    private String subcategory;
    private DatabaseHelper databaseHelper;
    private FirebaseAuth auth;
    private String uid;
    private String dbName;

    public DietsAdapter(Context context, List<DietItems> list, String subcategory) {
        this.context = context;
        this.originalList = list;
        this.filteredList = list;
        this.subcategory = subcategory;
        databaseHelper = new DatabaseHelper();
        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.diets_add_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietItems items = filteredList.get(position);
        holder.tvName.setText(items.getName());
        holder.tvSize.setText(items.getSize());
        holder.tvCalories.setText(String.valueOf(items.getCalories()));
        holder.ivAdd.setImageResource(R.drawable.icon_add_green);

        holder.ivAdd.setOnClickListener(view -> {
            DietItems dietItems = filteredList.get(position);
            dbName = "Selected " + DietPlansFragment.activityName + " Diets";
            CurentDietsItems curentDietsItems = new CurentDietsItems();
            curentDietsItems.setName(dietItems.getName());
            curentDietsItems.setSize(dietItems.getSize());
            curentDietsItems.setCalories(dietItems.getCalories());
            Date currentDate = new Date();
            curentDietsItems.setTimestemp((int) currentDate.getTime());


            databaseHelper.addEatenCaloriesToCurrentUser(dietItems.getCalories());
            databaseHelper.addCurrentItems(subcategory, curentDietsItems, uid).addOnSuccessListener(suc -> {
                Toast.makeText(context, "Data Added to", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er -> {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvSize;
        TextView tvCalories;
        ImageView ivAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvSize = itemView.findViewById(R.id.tv_size);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            ivAdd = itemView.findViewById(R.id.ic_add);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase().trim();
                filteredList = new ArrayList<>();

                for (DietItems item : originalList) {
                    if (item.getName().toLowerCase().contains(query) ||
                            item.getSize().toLowerCase().contains(query)) {
                        filteredList.add(item);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (List<DietItems>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

