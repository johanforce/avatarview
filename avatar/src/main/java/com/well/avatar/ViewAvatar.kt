package com.well.avatar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.well.avatar.databinding.ViewAvatarBinding
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern

class ViewAvatar : ConstraintLayout {
    private var binding: ViewAvatarBinding? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ViewAvatarBinding.inflate(layoutInflater, this, true)
    }

    fun setDataAvatar(isVip: Boolean, name: String, url: String?, byteArray: ByteArray?) {
        if (byteArray?.isNotEmpty() == true) {
            Glide.with(context!!).asBitmap()
                .load(byteArray)
                .placeholder(R.drawable.ic_avatar_default)
                .error(R.drawable.ic_avatar_default)
                .transform(CircleCrop())
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        Handler(Looper.getMainLooper()).post {
                            binding!!.ivAvatar.setImageBitmap(resource)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        Handler(Looper.getMainLooper()).post {
                            binding!!.ivAvatar.setImageDrawable(placeholder)
                        }
                    }
                })
        } else {
            setDataAvatar(isVip, name, url)
        }
    }

    fun setDataAvatar(isVip: Boolean, name: String, url: String?) {
        setVip(isVip)
        //Url not empty
        if (!TextUtils.isEmpty(url)) {
            val avatarUrl = AvatarUtils.convertAvatarLink(url!!)
            Glide.with(context!!).asBitmap()
                .load(avatarUrl)
                .placeholder(R.drawable.ic_avatar_default)
                .error(R.drawable.ic_avatar_default)
                .transform(CircleCrop())
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        Handler(Looper.getMainLooper()).post {
                            binding!!.ivAvatar.setImageBitmap(resource)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        Handler(Looper.getMainLooper()).post{
                            binding!!.ivAvatar.setImageDrawable(placeholder)
                        }
                    }
                })
        } else {
            //Url empty
            if (!TextUtils.isEmpty(name)) {
                val userName = name.trim().replace(Regex("\\s+"), " ")
                val listString: LinkedList<String> = LinkedList()
                userName.split(" ").forEach {
                    listString.add(it.trim())
                }

                if (listString.size >= 2) {
                    if (isAlphabet(listString[0]) && isAlphabet(listString[1])) {
                        binding?.ivAvatar?.setText(
                            listString[0].plus(" ").plus(listString[1])
                        )
                        return
                    }
                } else {
                    if (isAlphabet(listString[0])) {
                        binding?.ivAvatar?.setText(userName)
                    }
                    return
                }
            }
            binding?.ivAvatar!!.setImageResource(R.drawable.ic_avatar_default)
        }
    }


    fun setCircleImage() {
        binding?.ivAvatar?.setBackgroundResource(R.drawable.bg_ring)
        val padding = context.resources.getDimension(R.dimen.px2)
        binding?.ivAvatar?.setPadding(
            padding.toInt(), padding.toInt(), padding.toInt(),
            padding.toInt()
        )
    }


    fun setDataAvatar(name: String, url: String?) {
        setDataAvatar(false, name, url)
    }

    private fun isAlphabet(text: String): Boolean {
        if (TextUtils.isEmpty(text)) {
            return false
        }
        val pattern = Pattern.compile("([a-zA-Z])")
        val matcher = pattern.matcher(text.substring(0, 1))
        return matcher.matches()
    }

    fun setVip(isVip: Boolean) {
        setVip(isVip, false)
    }

    fun setVip(isVip: Boolean, isMe: Boolean) {
        if (isVip) {
            binding?.ivPremiumIcon?.visibility = VISIBLE
            binding?.ivPremiumBorder?.visibility = if (isMe) VISIBLE else GONE
        } else {
            binding?.ivPremiumIcon?.visibility = GONE
            binding?.ivPremiumBorder?.visibility = GONE
        }
    }

    fun setAvatarDefault() {
        binding?.ivAvatar!!.setImageResource(R.drawable.ic_avatar_default)
    }

    fun setImageAvatar(drawable: Int) {
        try {
            binding?.ivAvatar!!.setImageResource(drawable)
        } catch (e: Exception) {
            binding?.ivAvatar!!.setImageResource(R.drawable.ic_avatar_default)
        }
    }

    fun setImageAvatar(drawable: Drawable) {
        try {
            binding?.ivAvatar!!.setImageDrawable(drawable)
        } catch (e: Exception) {
            binding?.ivAvatar!!.setImageResource(R.drawable.ic_avatar_default)
        }
    }
}