package me.hachy.routineweek.activity

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager

import com.google.android.gms.ads.AdRequest

import java.util.Date

import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_task.*
import me.hachy.routineweek.R
import me.hachy.routineweek.fragment.TagColorDialogFragment
import me.hachy.routineweek.model.Todo
import me.hachy.routineweek.util.Prefs
import me.hachy.routineweek.util.TagColor

class AddTaskActivity : AppCompatActivity() {

    private var day: Int = 0
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        setContentView(R.layout.activity_add_task)

        day = intent.getIntExtra(DAY, 0)
        prefs = Prefs(applicationContext)

        val strId = resources.getIdentifier("day_of_week_$day", "string", packageName)
        toolbar.title = resources.getString(strId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val t = TagColor.values()[prefs.tagColorIdx]
        val colorId = resources.getIdentifier(t.name, "color", packageName)
        tagIcon.setColorFilter(ContextCompat.getColor(applicationContext, colorId))

        tagColor.setOnClickListener {
            val newFragment = TagColorDialogFragment()
            newFragment.show(supportFragmentManager, "tagColor")
        }

//        val adRequest = AdRequest.Builder().build(); // release用
        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // すべてのエミュレータ
                .addTestDevice(resources.getString(R.string.test_device_id))  // テスト用携帯電話
                .build()
        adView.loadAd(adRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_task, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        } else if (id == R.id.save) {
            if (TextUtils.isEmpty("${editText.text}")) {
                titleTextInputLayout.isErrorEnabled = true
                titleTextInputLayout.error = resources.getString(R.string.til_error_msg)
            } else {
                val realm = Realm.getDefaultInstance()
                var nextId: Long
                try {
                    val currentId = realm.where(Todo::class.java).max("id")
                    if (currentId != null) {
                        nextId = currentId.toLong() + 1
                    } else {
                        nextId = 0
                    }
                } catch (ex: Throwable) {
                    nextId = 0
                }

                val todo = Todo()
                todo.id = nextId
                todo.day = day
                todo.content = "${editText.text}"
                todo.done = false
                todo.tagColor = prefs.tagColorIdx
                todo.createdTime = Date()
                realm.executeTransaction {
                    realm.copyToRealmOrUpdate(todo)
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val DAY = "day"

        fun createIntent(context: Context, day: Int): Intent {
            val intent = Intent(context, AddTaskActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra(DAY, day)
            return intent
        }
    }
}
