package com.finnkinoinfo.finnkinoinfo;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class recyclerView_adapter extends RecyclerView.Adapter<recyclerView_adapter.MyViewHolder> {
    Context context;
    ArrayList<recyclerView> recyclerView;
    private ItemClickListener mItemListener;
    /** makes adapter for recyclerView
     * @param recyclerView given ArrayList of recyclerView's
     * @param context Activitys Context
     * @param itemClickListener sets itemClickListener to recyclerview*/
    public recyclerView_adapter(Context context, ArrayList<recyclerView> recyclerView, ItemClickListener itemClickListener){
        this.context=context;
        this.recyclerView=recyclerView;
        this.mItemListener = itemClickListener;
    }



    @NonNull
    @Override
    public recyclerView_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movielist_row,parent, false);

        return new recyclerView_adapter.MyViewHolder(view);
    }
    /** Makes row's under another and not on pile and clicklistener get's position of the clicked*/
    @Override
    public void onBindViewHolder(@NonNull recyclerView_adapter.MyViewHolder holder, int position) {
        holder.movieName.setText(recyclerView.get(position).getName());
        holder.movieTime.setText(recyclerView.get(position).getTime());

        holder.itemView.setOnClickListener(view ->{
            mItemListener.onItemClick(recyclerView.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return recyclerView.size();
    }
    /**Sets function what happens when item is cLicked*/
    public interface ItemClickListener{
        void onItemClick(com.finnkinoinfo.finnkinoinfo.recyclerView details);
    }
    /**Sets given values(Strings) to recyclerView_row element */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movieName, movieTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_Title);
            movieTime = itemView.findViewById(R.id.movie_Time);
        }
    }
}
