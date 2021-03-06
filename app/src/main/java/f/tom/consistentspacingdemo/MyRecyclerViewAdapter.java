package f.tom.consistentspacingdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tom on 7-1-2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<RecyclerViewData> data;
    private boolean useConsistentSpacing;


    public MyRecyclerViewAdapter(ArrayList<RecyclerViewData> data, boolean useConsistentSpacing) {
        this.data = data;
        this.useConsistentSpacing =useConsistentSpacing;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (useConsistentSpacing){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null);

        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_bad, null);
        }
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String text= data.get(position).getItemText();
        holder.dataTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView dataTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.dataTextView = itemView.findViewById(R.id.dataTextView);
        }
    }
}
