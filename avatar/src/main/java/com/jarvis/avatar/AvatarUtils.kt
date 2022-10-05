package com.jarvis.avatar

import android.text.TextUtils
import java.lang.Exception

class AvatarUtils {
    companion object {
        @JvmStatic
        fun convertAvatarLink(avatarLink: String): String? {
            if (TextUtils.isEmpty(avatarLink)) {
                return ""
            }
            if (avatarLink.contains("graph.facebook.com") && avatarLink.contains("&migration_overrides")) {
                try {
                    return avatarLink.substring(0, avatarLink.indexOf("&migration_overrides"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return avatarLink
        }
    }
}