package com.example.asus.go_paradmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.go_paradmin.R;
import com.example.asus.go_paradmin.data.model.Reminder;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.RoomChatViewHolder> {
    private Context context;
    private List<Reminder> reminderList;
    private OnDetailListener onDetailListener;

    public ReminderAdapter(Context context) {
        this.context = context;
    }

    public ReminderAdapter() {

    }
    public void setData(List<Reminder> reminderList){
        this.reminderList = reminderList;
        notifyDataSetChanged();
    }

    @Override
    public ReminderAdapter.RoomChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_reminder, parent, false);
        return new ReminderAdapter.RoomChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomChatViewHolder holder, int position) {
        holder.tvUserTimeUpload.setText("Dipost oleh "+reminderList.get(position).getUser().getName() + "   "+ reminderList.get(position).getCreatedAt());
        holder.tvReminderTitle.setText(reminderList.get(position).getReminderTitle());
        holder.tvReminderDescription.setText(reminderList.get(position).getReminderDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailListener.onItemDetailClicked(reminderList.get(position).getId());
            }
        });
        holder.btnReminderYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailListener.onButtonReminderYesClicked(reminderList.get(position).getReminderYes());
            }
        });
        
        holder.btnReminderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailListener.onButtonReminderNoClicked(reminderList.get(position).getReminderNo());
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
        if(reminderList == null) return 0;
        return reminderList.size();
    }

    public class RoomChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvReminderTitle, tvReminderDescription, tvUserTimeUpload;
        Button btnReminderYes, btnReminderNo;
        
        public RoomChatViewHolder(final View itemView) {
            super(itemView);
            tvUserTimeUpload = itemView.findViewById(R.id.tv_user_time_upload);
            tvReminderTitle = itemView.findViewById(R.id.tv_reminder_title);
            tvReminderDescription = itemView.findViewById(R.id.tv_reminder_description);
            btnReminderYes = itemView.findViewById(R.id.btn_reminder_yes);
            btnReminderNo = itemView.findViewById(R.id.btn_reminder_no);
        }
    }

    public interface OnDetailListener{
        void onItemDetailClicked(int tipsId);



        void onButtonReminderYesClicked(String reminderYes);

        void onButtonReminderNoClicked(String reminderNo);
    }
}
