package com.saitejajanjirala.digitaludhaarkhata;

import android.widget.Toast;

public class Total {
    private int myouwillgive;
   private int myouwillget;
   private String mid;

   public Total(int myouwillget,int myouwillgive,String id){
       this.myouwillgive=myouwillgive;
       this.myouwillget=myouwillget;
       this.mid=id;
   }
    public Total(){

    }

    public void setMyouwillgive(int myouwillgive) {
        this.myouwillgive = myouwillgive;
    }

    public void setMyouwillget(int myouwillget) {
        this.myouwillget = myouwillget;
    }

    public int getMyouwillget() {
        return myouwillget;
    }

    public int getMyouwillgive() {
        return myouwillgive;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMid() {
        return mid;
    }
}
