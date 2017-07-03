package me.hachy.routineweek.activity

import android.content.Context
import android.content.Intent
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import java.util.Date

import io.realm.Realm
import me.hachy.routineweek.R
import me.hachy.routineweek.fragment.TagColorDialogFragment
import me.hachy.routineweek.model.Todo
import me.hachy.routineweek.util.Prefs
import me.hachy.routineweek.util.TagColor

class AddTaskActivity : AppCompatActivity() {
    private var day: Int = 0
    private var editText: AppCompatEditText? = null
    private var textInputLayout: TextInputLayout? = null
    private var prefs: Prefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        setContentView(R.layout.activity_add_task)

        day = intent.getIntExtra(DAY, 0)
        prefs = Prefs(applicationContext)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        textInputLayout = findViewById(R.id.title_text_inpnut_layout) as TextInputLayout
        editText = findViewById(R.id.edit_text) as AppCompatEditText
        val tagColor = findViewById(R.id.tag_color) as TextView
        val tagIcon = findViewById(R.id.tag_icon) as ImageView

        val strId = resources.getIdentifier("day_of_week_" + day.toString(), "string", packageName)
        toolbar.title = resources.getString(strId)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val t = TagColor.values()[prefs!!.tagColorIdx]
        val colorId = resources.getIdentifier(t.name, "color", packageName)
        tagIcon.setColorFilter(ContextCompat.getColor(applicationContext, colorId))

        tagColor.setOnClickListener {
            val newFragment = TagColorDialogFragment()
            newFragment.show(supportFragmentManager, "tagColor")
        }

        val adView = findViewById(R.id.adView) as AdView
        //        AdRequest adRequest = new AdRequest.Builder().build(); // release用
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
            if (TextUtils.isEmpty(editText!!.text.toString())) {
                textInputLayout!!.isErrorEnabled = true
                textInputLayout!!.error = resources.getString(R.string.til_error_msg)
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
                todo.content = editText!!.text.toString()
                todo.done = false
                todo.tagColor = prefs!!.tagColorIdx
                todo.createdTime = Date()
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(todo)
                realm.commitTransaction()
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
