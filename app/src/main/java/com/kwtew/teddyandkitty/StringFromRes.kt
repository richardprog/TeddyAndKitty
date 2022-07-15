package com.kwtew.teddyandkitty

import android.content.Context
import androidx.annotation.StringRes

data class StringFromRes (
    @StringRes val resId: Int? = null,
    @StringRes val resIdArg: Int? = null
) {
    fun toString(context: Context? = null): String {
        return context?.let { context2 ->
            resId?.let { resId2 ->
                val resIdArgString = resIdArg?.let {
                    context.getString(it)
                }
                context2.getString(resId2, resIdArgString)
            } ?: ""
        } ?: ""
    }
}
