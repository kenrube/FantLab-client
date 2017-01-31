package org.odddev.fantlab.core.utils

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide

import org.odddev.fantlab.R

/**
 * @author kenrube
 * *
 * @since 17.09.16
 */

object BindingUtils {

	@BindingAdapter("onClick")
	@JvmStatic
	fun bindOnClick(view: View, runnable: Runnable) {
		view.setOnClickListener { v -> runnable.run() }
	}

	@BindingAdapter("error")
	@JvmStatic
	fun bindError(textInputLayout: TextInputLayout, error: String?) {
		textInputLayout.isErrorEnabled = error != null
		textInputLayout.error = error
	}

	@BindingAdapter("srcUri", "isCircle", "placeholder")
	@JvmStatic
	fun bindSrcUri(imageView: ImageView, uri: String, isCircle: Boolean,
				   placeholder: Drawable) {
		val builder = Glide
				.with(imageView.context)
				.load(uri)
				.placeholder(placeholder)
				.error(placeholder)
		if (isCircle) {
			builder.transform(CircleTransform(imageView.context))
		}
		builder.into(imageView)
	}

	@BindingAdapter("font")
	@JvmStatic
	fun bindFont(textView: TextView, fontName: String) {
		val robotoMedium = textView.context.getString(R.string.roboto_medium)
		if (fontName == robotoMedium && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			textView.typeface = Typeface.createFromAsset(
					textView.context.assets, "fonts/$robotoMedium.ttf")
		} else {
			textView.typeface = Typeface.create(fontName, Typeface.NORMAL)
		}
	}

	@BindingConversion
	@JvmStatic
	fun convertConditionToVisibility(condition: Boolean): Int = if (condition) View.VISIBLE else View.GONE
}