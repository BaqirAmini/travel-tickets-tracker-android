package com.example.a303bus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketsBookAdapter extends RecyclerView.Adapter<TicketsBookAdapter.ViewHolder> {
    private List<TicketsDataModel> ticketsList;
    private Context context;

    public TicketsBookAdapter(Context context, List<TicketsDataModel> ticketsList) {
        this.context = context;
        this.ticketsList = ticketsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tickets_recyclerview_for_users, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TicketsDataModel modal = ticketsList.get(position);
        holder.depPlaceName.setText(modal.getFromWhere());
        holder.depTime.setText(modal.getDepDate() + " At " + modal.getDepTime());
        holder.depToPlaceName.setText(modal.getToWhere());
        holder.depTctPrice.setText(modal.getTicketPrice() + " AFN");
        //Book a ticket
        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), passenger_detail.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra("TICKET_ID", modal.getTicketID()); // Ticket ID is the primary key with tickets table
               intent.putExtra("TICKET_NO", modal.getTicketNO()); // Ticket NO is generated randomly which is printed on layouts
               intent.putExtra("USER_ID", modal.getUserID());
               intent.putExtra("BUS_COMPANY", modal.getTicketCompany());
               intent.putExtra("SOURCE", modal.getFromWhere());
               intent.putExtra("DEST", modal.getToWhere());
               intent.putExtra("DATE", modal.getDepDate());
               intent.putExtra("TIME", modal.getDepTime());
               view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onUpdateList(List<TicketsDataModel> newList) {
        ticketsList = new ArrayList<>();
        ticketsList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView imageCustomer;
        TextView depFromLbl;
        TextView depPlaceName;
        TextView depTimeLbl;
        TextView depTime;
        TextView depToLbl;
        TextView depToPlaceName;
        TextView depTctPriceLbl;
        TextView depTctPrice;
        TextView btnBook;

        public ViewHolder(View itemView) {
            super(itemView);
            depFromLbl = itemView.findViewById(R.id.txt_from_lbl);
            depPlaceName = itemView.findViewById(R.id.txt_from_val);
            depTimeLbl = itemView.findViewById(R.id.txt_from_dep_lbl);
            depTime = itemView.findViewById(R.id.txt_from_ddatetime);
            depToLbl = itemView.findViewById(R.id.txt_to_lbl);
            depToPlaceName = itemView.findViewById(R.id.txt_to_val);
            depTctPriceLbl = itemView.findViewById(R.id.txt_price_lbl);
            depTctPrice = itemView.findViewById(R.id.txt_price_amount);
            btnBook = itemView.findViewById(R.id.btn_book_by_user);
        }
    }


}
