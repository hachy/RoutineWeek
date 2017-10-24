package me.hachy.routineweek.fragment


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.ImageView

import me.hachy.routineweek.R
import me.hachy.routineweek.util.Prefs
import me.hachy.routineweek.util.TagColor


class TagColorDialogFragment : DialogFragment() {

    private lateinit var tagIcon: ImageView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val prefs = Prefs(context)

        tagIcon = activity.findViewById<ImageView>(R.id.tagIcon) as ImageView

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.tag_color)
                .setSingleChoiceItems(TagColor.names(), prefs.tagColorIdx) { dialogInterface, i ->
                    prefs.tagColorIdx = i

                    val t = TagColor.values()[i]
                    val colorId = resources.getIdentifier(t.name, "color", activity.packageName)
                    tagIcon.setColorFilter(ContextCompat.getColor(context, colorId))

                    dialogInterface.dismiss()
                }

        return builder.create()
    }
}
