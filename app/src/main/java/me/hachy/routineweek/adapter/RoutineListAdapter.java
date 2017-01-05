package me.hachy.routineweek.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.hachy.routineweek.R;
import me.hachy.routineweek.databinding.RoutineRowBinding;
import me.hachy.routineweek.model.Todo;
import me.hachy.routineweek.util.TagColor;

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

        int i = todo.getTagColor();
        TagColor t = TagColor.values()[i];
        int colorId = context.getResources().getIdentifier(t.getName(), "color", context.getPackageName());
        holder.binding.tagColor.setBackgroundColor(ContextCompat.getColor(context.getApplicationContext(), colorId));

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

        holder.binding.moreVert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.list_popup, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.remove) {
                            realm.beginTransaction();
                            int pos = holder.getAdapterPosition();
                            Todo t = getItem(pos);
                            if (t != null) {
                                t.deleteFromRealm();
                            }
                            notifyItemRemoved(pos);
                            realm.commitTransaction();
                        }
                        return true;
                    }
                });
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
