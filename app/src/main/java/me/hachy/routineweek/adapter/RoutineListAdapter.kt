package me.hachy.routineweek.adapter


import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import me.hachy.routineweek.R
import me.hachy.routineweek.databinding.RoutineRowBinding
import me.hachy.routineweek.model.Todo
import me.hachy.routineweek.util.TagColor

class RoutineListAdapter(private val context: Context, data: OrderedRealmCollection<Todo>?, autoUpdate: Boolean) : RealmRecyclerViewAdapter<Todo, RoutineListAdapter.MyViewHolder>(data, autoUpdate) {

    private var realm: Realm? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        realm = Realm.getDefaultInstance()

        val routineRowBinding = DataBindingUtil.inflate<RoutineRowBinding>(LayoutInflater.from(parent.context),
                R.layout.routine_row, parent, false)

        return MyViewHolder(routineRowBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todo = data!![position]
        holder.binding.todo = todo
        holder.binding.executePendingBindings()

        val i = todo.tagColor
        val t = TagColor.values()[i]
        val colorId = context.resources.getIdentifier(t.getName(), "color", context.packageName)
        holder.binding.tagColor.setBackgroundColor(ContextCompat.getColor(context.applicationContext, colorId))

        holder.binding.checkBox.setOnClickListener {
            realm!!.beginTransaction()
            val t = getItem(holder.adapterPosition)
            if (t != null) {
                if (holder.binding.checkBox.isChecked) {
                    t.done = true
                } else if (!holder.binding.checkBox.isChecked) {
                    t.done = false
                }
            }
            realm!!.commitTransaction()
        }

        holder.binding.moreVert.setOnClickListener { view ->
            val popup = PopupMenu(context, view)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.list_popup, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.remove) {
                    realm!!.beginTransaction()
                    val pos = holder.adapterPosition
                    val t = getItem(pos)
                    t?.deleteFromRealm()
                    notifyItemRemoved(pos)
                    realm!!.commitTransaction()
                }
                true
            }
        }
    }

    inner class MyViewHolder constructor(val binding: RoutineRowBinding) : RecyclerView.ViewHolder(binding.root)
}
