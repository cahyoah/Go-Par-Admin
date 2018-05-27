package com.example.asus.go_paradmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.data.model.Tips;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.RoomChatViewHolder>{
    private Context context;
    private List<Tips> tipsList;
    private OnDetailListener onDetailListener;

    public TipsAdapter(Context context) {
        this.context = context;
    }

    public TipsAdapter() {

    }
    public void setData(List<Tips> items){
        tipsList = items;
        notifyDataSetChanged();
    }

    @Override
    public TipsAdapter.RoomChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_tips, parent, false);
        return new RoomChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomChatViewHolder holder, final int position) {
        holder.tvUserTimeUpload.setText("Dipost oleh "+tipsList.get(position).getUser().getName());
        holder.tvTipsTitle.setText(tipsList.get(position).getTipsTitle());
        holder.tvTipsDescription.setText(tipsList.get(position).getTipsDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailListener.onItemDetailClicked(tipsList.get(position).getId());
            }
        });
    }
    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }


    @Override
    public boolean onFailedToRecycleView(RoomChatViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if(tipsList == null) return 0;
        return tipsList.size();
    }

    public class RoomChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvTipsTitle, tvTipsDescription, tvUserTimeUpload;

        public RoomChatViewHolder(final View itemView) {
            super(itemView);
            tvUserTimeUpload = itemView.findViewById(R.id.tv_user_time_upload);
            tvTipsTitle = itemView.findViewById(R.id.tv_tips_title);
            tvTipsDescription = itemView.findViewById(R.id.tv_tips_description);

        }
    }

    public interface OnDetailListener{
        void onItemDetailClicked(int tipsId);

        void showMessageFailure();
    }
}
