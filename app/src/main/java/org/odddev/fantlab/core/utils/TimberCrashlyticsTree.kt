package org.odddev.fantlab.core.utils

import android.util.Log

import com.crashlytics.android.Crashlytics

import timber.log.Timber

/**
 * @author kenrube
 * *
 * @since 23.08.16
 */

class TimberCrashlyticsTree : Timber.Tree() {

	override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
		if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
			return
		}

		Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
		Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
		Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)

		Crashlytics.logException(t ?: Exception(message))
	}

	companion object {

		private val CRASHLYTICS_KEY_PRIORITY = "priority"
		private val CRASHLYTICS_KEY_TAG = "tag"
		private val CRASHLYTICS_KEY_MESSAGE = "message"
	}
}
