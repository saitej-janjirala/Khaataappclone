package com.saitejajanjirala.digitaludhaarkhata;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class customerchatadapter extends RecyclerView.Adapter<customerchatadapter.customerhatviewolder> {
    Customertotal mcustomertotal;
    ArrayList<Chatdata> xchatdatalist;

    @Override
    public int getItemCount() {
        return xchatdatalist.size();
    }

    public static class customerhatviewolder extends RecyclerView.ViewHolder{
        TextView mgaveamounttext,mgotamounttext,mdatetext;
        public customerhatviewolder(@NonNull View itemView) {
            super(itemView);
            mgaveamounttext=itemView.findViewById(R.id.gaveamounttext);
            mgotamounttext=itemView.findViewById(R.id.gotamounttext);
            mdatetext=itemView.findViewById(R.id.datetext);
        }
    }
    public customerchatadapter(ArrayList<Chatdata> customerchatArrayList1){
        xchatdatalist=new ArrayList<>();
        xchatdatalist=customerchatArrayList1;
    }

    @NonNull
    @Override
    public customerhatviewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.exampleitemforcustomerchats,parent,false);
        customerhatviewolder evh= new customerhatviewolder(view);
        return evh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull customerhatviewolder holder, int position) {
            if(xchatdatalist.get(position).getMyougot()==0 && xchatdatalist.get(position).getMyougave()==0){

            }
            else {
                if (xchatdatalist.get(position).getMyougave() == 0) {
                    holder.mgaveamounttext.setText("");
                    holder.mgotamounttext.setText(xchatdatalist.get(position).getMyougot() + "");
                    holder.mdatetext.setText(xchatdatalist.get(position).getMdate());
                } else if (xchatdatalist.get(position).getMyougot() == 0) {
                    holder.mgaveamounttext.setText(xchatdatalist.get(position).getMyougave() + "");
                    holder.mgotamounttext.setText("");
                    holder.mdatetext.setText(xchatdatalist.get(position).getMdate());
                }
            }


    }


}
