package com.example.secnoteapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.secnoteapp.create_new_notes.NewNote;
import com.example.secnoteapp.create_new_notes.NewNoteActivity;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyHolder> {
    List<NewNote> notesList;
    private Context context;
    public  NotesAdapter(List<NewNote> noteslist,Context context)
    {
        this.context=context;
        this.notesList=noteslist;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_view,viewGroup,false);

        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        NewNote data=notesList.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.desc.setText(data.getContent());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,desc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.view_title);
            desc=itemView.findViewById(R.id.view_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewNote newNote=notesList.get(getAdapterPosition());
                    Intent i=new Intent(context, UpdateActivity.class);
                    i.putExtra("id",newNote.getId());
                    i.putExtra("title",newNote.getTitle());
                    i.putExtra("desc",newNote.getContent());
                    context.startActivity(i);
                }
            });

        }


    }

}
