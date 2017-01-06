package me.hachy.routineweek.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import me.hachy.routineweek.R;
import me.hachy.routineweek.util.Prefs;
import me.hachy.routineweek.util.TagColor;


public class TagColorDialogFragment extends DialogFragment {

    private ImageView tagIcon;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final Prefs prefs = new Prefs(getContext());

        tagIcon = (ImageView) getActivity().findViewById(R.id.tag_icon);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.tag_color)
                .setSingleChoiceItems(TagColor.names(), prefs.getTagColorIdx(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prefs.setTagColorIdx(i);

                        TagColor t = TagColor.values()[i];
                        int colorId = getResources().getIdentifier(t.getName(), "color", getActivity().getPackageName());
                        tagIcon.setColorFilter(ContextCompat.getColor(getContext(), colorId));

                        dialogInterface.dismiss();
                    }
                });

        return builder.create();
    }
}
