package com.example.secnoteapp.view_holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.secnoteapp.R;

public class NotesViewHolder extends RecyclerView.ViewHolder
{
    public TextView vTitle,vContent;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        vTitle = itemView.findViewById(R.id.view_title);
        vContent = itemView.findViewById(R.id.view_content);
    }
}
