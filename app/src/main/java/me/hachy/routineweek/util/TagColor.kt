package me.hachy.routineweek.util


import java.util.Arrays

enum class TagColor {
    None, Blue, Red, Green, Yellow;

    companion object {

        fun names(): Array<String> {
            return Arrays.toString(TagColor.values()).replace("^.|.$".toRegex(), "").split(", ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
    }
}
