package com.saitejajanjirala.digitaludhaarkhata;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class customercontentsadpater extends RecyclerView.Adapter<customercontentsadpater.customercontentsviewholder> {

    ArrayList<Customertotal> mlisttotal;
    public oncustomernameclicked mlistener;
    public interface oncustomernameclicked{
         void onclicked(int position);
    }
    public void oncustomernameclicked(oncustomernameclicked listener){
        mlistener=listener;
    }
    public static class customercontentsviewholder extends RecyclerView.ViewHolder{
        TextView mcustomername,mgetamount,mgiveamount;
        public customercontentsviewholder(@NonNull View itemView, final oncustomernameclicked listener) {
            super(itemView);
            //mrelativelayout=itemView.findViewById(R.id.mtoprelative);
            mcustomername=itemView.findViewById(R.id.mcontentscustomername);
            mgetamount=itemView.findViewById(R.id.mgetamount);
            mgiveamount=itemView.findViewById(R.id.mgiveamount);
            mcustomername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onclicked(position);
                        }
                    }
                }
            });

        }
    }
    public customercontentsadpater(ArrayList<Customertotal> list){
        mlisttotal=list;
    }
    @NonNull
    @Override
    public customercontentsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.exampleitemforhome,parent,false);
        return new customercontentsviewholder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull customercontentsviewholder holder, int position) {
    //    Log.i("adaptername",mlisttotal.get(position).getMid());
      //  Log.i("adapternumber",mlisttotal.get(position).getMphonenumber());
            holder.mcustomername.setText(mlisttotal.get(position).getMid() + "\n" + mlisttotal.get(position).getMphonenumber());
            holder.mgiveamount.setText(mlisttotal.get(position).getMtotalgot() + "");
            holder.mgetamount.setText(mlisttotal.get(position).getMtotalgave() + "");

    }

    @Override
    public int getItemCount() {
        return mlisttotal.size();
    }

}
