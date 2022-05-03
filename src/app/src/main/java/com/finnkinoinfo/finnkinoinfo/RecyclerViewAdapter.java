package com.finnkinoinfo.finnkinoinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<RecyclerView> recyclerView;
    private final ItemClickListener mItemListener;
    /** makes adapter for RecyclerView
     * @param recyclerView given ArrayList of RecyclerView's
     * @param context Activitys Context
     * @param itemClickListener sets itemClickListener to recyclerview*/
    public RecyclerViewAdapter(Context context, ArrayList<RecyclerView> recyclerView, ItemClickListener itemClickListener){
        this.context=context;
        this.recyclerView=recyclerView;
        this.mItemListener = itemClickListener;
    }



    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movielist_row,parent, false);

        return new RecyclerViewAdapter.MyViewHolder(view);
    }
    /** Makes row's under another and not on pile and clicklistener get's position of the clicked*/
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
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
        void onItemClick(RecyclerView details);
    }
    /**Sets given values(Strings) to recyclerView_row element */
    public static class MyViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {

        TextView movieName, movieTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_Title);
            movieTime = itemView.findViewById(R.id.movie_Time);
        }
    }
}
