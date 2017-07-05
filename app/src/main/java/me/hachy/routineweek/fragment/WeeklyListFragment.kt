package me.hachy.routineweek.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import io.realm.Realm
import io.realm.RealmConfiguration
import me.hachy.routineweek.R
import me.hachy.routineweek.activity.AddTaskActivity
import me.hachy.routineweek.adapter.RoutineListAdapter
import me.hachy.routineweek.model.Todo

import android.support.v7.widget.helper.ItemTouchHelper.DOWN
import kotlinx.android.synthetic.main.fragment_weekly.*
import kotlinx.android.synthetic.main.fragment_weekly_list.*
import kotlin.properties.Delegates

class WeeklyListFragment : Fragment() {

    private var day: Int = 0
    private var realm: Realm by Delegates.notNull()
    private var tempRealm: Realm by Delegates.notNull()
    private var adapter: RoutineListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        day = arguments.getInt(POSITION)

        realm = Realm.getDefaultInstance()
        val tempConfig = RealmConfiguration.Builder()
                .name("temp.realm")
                .inMemory()
                .build()
        tempRealm = Realm.getInstance(tempConfig)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_weekly_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                LinearLayoutManager(activity).orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity, R.drawable.divider))
        recyclerView.addItemDecoration(dividerItemDecoration)

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_IDLE -> fab?.show()
                    ViewPager.SCROLL_STATE_DRAGGING, ViewPager.SCROLL_STATE_SETTLING -> fab?.hide()
                }
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                if (dy > 0) {
                    if (fab.isShown) {
                        fab.hide()
                    }
                } else if (dy < 0) {
                    if (!fab.isShown) {
                        fab.show()
                    }
                }
            }
        })

        fab.setOnClickListener { startActivity(AddTaskActivity.createIntent(activity, day)) }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.ACTION_STATE_DRAG or ItemTouchHelper.UP or DOWN, 0) {
            override fun onMove(rv: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                adapter?.notifyItemMoved(fromPos, toPos)

                val from = adapter?.getItem(fromPos)
                val to = adapter?.getItem(toPos)
                var temp: Todo? = null

                tempRealm.executeTransaction {
                    val results = tempRealm.where(Todo::class.java).findAll()
                    results.deleteAllFromRealm()
                    temp = tempRealm.createObject(Todo::class.java, 1)
                    temp?.createdTime = from?.createdTime
                }

                realm.executeTransaction {
                    from?.createdTime = to?.createdTime
                    to?.createdTime = temp?.createdTime
                }

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

            override fun clearView(rv: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(rv, viewHolder)
                setUpRecyclerView()
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        tempRealm.close()
    }

    private fun setUpRecyclerView() {
        val todo = realm.where(Todo::class.java).equalTo("day", day).findAllSorted("createdTime")
        adapter = RoutineListAdapter(context, todo, false)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    companion object {

        private val POSITION = "position"

        fun newInstance(position: Int): WeeklyListFragment {
            val fragment = WeeklyListFragment()
            val args = Bundle()
            args.putInt(POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
