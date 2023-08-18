package com.example.jps;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private Context context;
    private List<ScrapModel> itemList;
    private ItemClick itemClick;

    public RVAdapter(Context context, List<ScrapModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrap_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (itemClick != null) {
            holder.itemView.setOnClickListener(v -> itemClick.onClick(v, position));
        }
        holder.bindItems(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface ItemClick {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindItems(ScrapModel item) {
            TextView company_name = itemView.findViewById(R.id.company_name);
            TextView job_position = itemView.findViewById(R.id.job_position);
            TextView address=itemView.findViewById(R.id.address);

            company_name.setText(item.getCompanyName());
            job_position.setText(item.getJobPosition());
            address.setText(item.getAddress());
        }
    }
}
