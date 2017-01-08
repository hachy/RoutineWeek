package me.hachy.routineweek.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import me.hachy.routineweek.R;
import me.hachy.routineweek.activity.AddTaskActivity;
import me.hachy.routineweek.adapter.RoutineListAdapter;
import me.hachy.routineweek.model.Todo;

import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;

public class WeeklyListFragment extends Fragment {

    private static final String POSITION = "position";
    private int day;
    private Realm realm;
    private Realm tempRealm;
    private RecyclerView recyclerView;
    private RoutineListAdapter adapter;
    private FloatingActionButton fab;

    public WeeklyListFragment() {
        // Required empty public constructor
    }

    public static WeeklyListFragment newInstance(int position) {
        WeeklyListFragment fragment = new WeeklyListFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        day = getArguments().getInt(POSITION);

        realm = Realm.getDefaultInstance();
        RealmConfiguration tempConfig = new RealmConfiguration.Builder()
                .name("temp.realm")
                .inMemory()
                .build();
        tempRealm = Realm.getInstance(tempConfig);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) view.findViewById(R.id.main_fab);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(getActivity()).getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        fab.show();
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                    case ViewPager.SCROLL_STATE_SETTLING:
                        fab.hide();
                        break;
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (fab.isShown()) {
                        fab.hide();
                    }
                } else if (dy < 0) {
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddTaskActivity.createIntent(getActivity(), day));
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.ACTION_STATE_DRAG | ItemTouchHelper.UP | DOWN, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                adapter.notifyItemMoved(fromPos, toPos);

                Todo from = adapter.getItem(fromPos);
                Todo to = adapter.getItem(toPos);

                tempRealm.beginTransaction();
                RealmResults<Todo> results = tempRealm.where(Todo.class).findAll();
                results.deleteAllFromRealm();
                Todo temp = tempRealm.createObject(Todo.class, 1);
                if (from != null) {
                    temp.setCreatedTime(from.getCreatedTime());
                }
                tempRealm.commitTransaction();

                realm.beginTransaction();
                if (to != null && from != null) {
                    from.setCreatedTime(to.getCreatedTime());
                    to.setCreatedTime(temp.getCreatedTime());
                }
                realm.commitTransaction();

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                setUpRecyclerView();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpRecyclerView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
        tempRealm.close();
    }

    private void setUpRecyclerView() {
        RealmResults<Todo> todo = realm.where(Todo.class).equalTo("day", day).findAllSorted("createdTime");
        adapter = new RoutineListAdapter(getContext(), todo, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
