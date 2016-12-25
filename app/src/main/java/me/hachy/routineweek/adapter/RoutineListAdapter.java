package me.hachy.routineweek.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.hachy.routineweek.R;
import me.hachy.routineweek.model.Todo;

public class RoutineListAdapter extends RealmRecyclerViewAdapter<Todo, RoutineListAdapter.MyViewHolder> {

    public RoutineListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Todo> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Todo todo = getData().get(position);
        holder.content.setText(todo.getContent());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView content;

        private MyViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.textview);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }
}
