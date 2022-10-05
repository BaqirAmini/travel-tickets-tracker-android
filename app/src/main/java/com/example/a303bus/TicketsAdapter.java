package com.example.a303bus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Double;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {
    private List<TicketsDataModel> ticketsList;
    private Context context;

    public TicketsAdapter(Context context, List<TicketsDataModel> ticketsList) {
        this.context = context;
        this.ticketsList = ticketsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tickets_recyclerview_for_admins, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TicketsDataModel modal = ticketsList.get(position);
        holder.depPlaceName.setText(modal.getFromWhere());
        holder.depTime.setText(modal.getDepDate().concat(" At ").concat(modal.getDepTime()));
        holder.depToPlaceName.setText(modal.getToWhere());
        holder.depTctPrice.setText(modal.getTicketPrice() + "".concat(" AFN"));
        holder.tctCompName.setText(modal.getTicketCompany());

        //Update contents of any card
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), UpdateTicketActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra("TICKET_ID", modal.getTicketID());
               intent.putExtra("FROM", modal.getFromWhere());
               intent.putExtra("TO", modal.getToWhere());
               intent.putExtra("PRICE", modal.getTicketPrice()+"");
               intent.putExtra("COMPANY", modal.getTicketCompany());
               view.getContext().startActivity(intent);
            }
        });

//        Delete a ticket by admin.
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                int ticketId = modal.getTicketID();
                try {
                    File inStorage = context.getExternalFilesDir(null);
                    String desPath = inStorage.getAbsolutePath() + "/bus303/database/";
                    SQLiteDatabase db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
                    int deleted = db.delete("tickets", "ticket_ID = ?", new String[]{String.valueOf(ticketId)});
                    if (deleted != -1) {
                        ticketsList.get(holder.getAdapterPosition());
                        ticketsList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        Toast.makeText(context, "The ticket deleted.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("DELETE_ERROR", "Cannot delete from tickets: " + e.getMessage());
                }
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
        TextView tctCompLbl;
        TextView tctCompName;
        TextView btnUpdate;
        TextView btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            depFromLbl = itemView.findViewById(R.id.txtfrom);
            depPlaceName = itemView.findViewById(R.id.txtfrom2);
            depTimeLbl = itemView.findViewById(R.id.txtfrom6);
            depTime = itemView.findViewById(R.id.txtdep);
            depToLbl = itemView.findViewById(R.id.txtto);
            depToPlaceName = itemView.findViewById(R.id.txtto1);
            depTctPriceLbl = itemView.findViewById(R.id.txtfrom4);
            depTctPrice = itemView.findViewById(R.id.txt_price_value);
            tctCompLbl = itemView.findViewById(R.id.txttc);
            tctCompName = itemView.findViewById(R.id.txttc2);
            btnUpdate = itemView.findViewById(R.id.btnupdete);
            btnDelete = itemView.findViewById(R.id.btndelete);


        }
    }


}
