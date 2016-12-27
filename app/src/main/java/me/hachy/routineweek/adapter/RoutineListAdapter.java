package me.hachy.routineweek.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.hachy.routineweek.R;
import me.hachy.routineweek.databinding.RoutineRowBinding;
import me.hachy.routineweek.model.Todo;

public class RoutineListAdapter extends RealmRecyclerViewAdapter<Todo, RoutineListAdapter.MyViewHolder> {

    private Realm realm;

    public RoutineListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Todo> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        realm = Realm.getDefaultInstance();

        RoutineRowBinding routineRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.routine_row, parent, false);

        return new MyViewHolder(routineRowBinding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Todo todo = getData().get(position);
        holder.binding.setTodo(todo);
        holder.binding.executePendingBindings();

        holder.binding.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                Todo t = getItem(holder.getAdapterPosition());
                if (t != null) {
                    if (holder.binding.checkBox.isChecked()) {
                        t.setDone(true);
                    } else if (!holder.binding.checkBox.isChecked()) {
                        t.setDone(false);
                    }
                }
                realm.commitTransaction();
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RoutineRowBinding binding;

        private MyViewHolder(RoutineRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
