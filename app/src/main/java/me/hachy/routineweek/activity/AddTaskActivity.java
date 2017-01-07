package me.hachy.routineweek.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Date;

import io.realm.Realm;
import me.hachy.routineweek.R;
import me.hachy.routineweek.fragment.TagColorDialogFragment;
import me.hachy.routineweek.model.Todo;
import me.hachy.routineweek.util.Prefs;
import me.hachy.routineweek.util.TagColor;

public class AddTaskActivity extends AppCompatActivity {

    private final static String DAY = "day";
    private int day;
    private AppCompatEditText editText;
    private TextInputLayout textInputLayout;
    private Prefs prefs;

    public static Intent createIntent(Context context, int day) {
        Intent intent = new Intent(context, AddTaskActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(DAY, day);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setContentView(R.layout.activity_add_task);

        day = getIntent().getIntExtra(DAY, 0);
        prefs = new Prefs(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textInputLayout = (TextInputLayout) findViewById(R.id.title_text_inpnut_layout);
        editText = (AppCompatEditText) findViewById(R.id.edit_text);
        TextView tagColor = (TextView) findViewById(R.id.tag_color);
        ImageView tagIcon = (ImageView) findViewById(R.id.tag_icon);

        int strId = getResources().getIdentifier("day_of_week_" + String.valueOf(day), "string", getPackageName());
        toolbar.setTitle(getResources().getString(strId));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TagColor t = TagColor.values()[prefs.getTagColorIdx()];
        int colorId = getResources().getIdentifier(t.getName(), "color", getPackageName());
        tagIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), colorId));

        tagColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TagColorDialogFragment();
                newFragment.show(getSupportFragmentManager(), "tagColor");
            }
        });

        AdView adView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build(); // release用
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // すべてのエミュレータ
                .addTestDevice(getResources().getString(R.string.test_device_id))  // テスト用携帯電話
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_task, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.save) {
            if (TextUtils.isEmpty(String.valueOf(editText.getText()))) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(getResources().getString(R.string.til_error_msg));
            } else {
                Realm realm = Realm.getDefaultInstance();
                long nextId;
                try {
                    Number currentId = realm.where(Todo.class).max("id");
                    if (currentId != null) {
                        nextId = currentId.longValue() + 1;
                    } else {
                        nextId = 0;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    nextId = 0;
                }
                Todo todo = new Todo();
                todo.setId(nextId);
                todo.setDay(day);
                todo.setContent(editText.getText().toString());
                todo.setDone(false);
                todo.setTagColor(prefs.getTagColorIdx());
                todo.setCreatedTime(new Date());
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(todo);
                realm.commitTransaction();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
