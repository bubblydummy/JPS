package com.example.jps;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            holder.itemView.setOnClickListener(v -> itemClick.onClick(v, holder.getAdapterPosition()));
        }
        holder.bindItems(itemList.get(position));

        //삭제 버튼에 클릭 리스너 추가
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(holder.getAdapterPosition()); // 아이템 삭제 함수 호출
            }
        });
    }

    // 아이템 삭제 기능
    private void deleteItem(int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("JPS");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String itemId = itemList.get(position).getJobPosition(); // 해당 아이템의 식별자를 가져와야 함



        // Firebase에서 아이템 삭제
        databaseReference.child(userId).child(itemId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            itemList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, itemList.size());
                        } else {
                            Log.e("firebase", "Error deleting item", task.getException());
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface ItemClick {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_delete;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindItems(ScrapModel item) {
            TextView company_name = itemView.findViewById(R.id.company_name);
            TextView job_position = itemView.findViewById(R.id.job_position);
            TextView address=itemView.findViewById(R.id.address);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            company_name.setText(item.getCompanyName());
            job_position.setText(item.getJobPosition());
            //address.setText(item.getAddress());
        }
    }
}
