package com.e.roomdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<Note> notes;
    Context context;

    NoteListAdapter(ArrayList<Note> notes, Context context) {
        Log.v("adapter", "---");
        this.context = context;
        this.notes = notes;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.v("create","--=");
        if (i == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_note_list, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_loading, viewGroup, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
Log.v("holder-","-");
        if (viewHolder instanceof ViewHolder) {
            populateItemRows((ViewHolder) viewHolder, i);
        } else {
            showLoadingView((LoadingViewHolder) viewHolder, i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return notes.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {

        Log.v("SIZE", "-" + notes.size());
        return notes == null ? 0 : notes.size();
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.tv_id);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ViewHolder viewHolder, int position) {

        Note note = notes.get(position);
        viewHolder.tvName.setText(note.getNote());
        viewHolder.id.setText(note.getId());

    }

}
