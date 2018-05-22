package ru.fantlab.android.ui.modules.author.editions

import android.os.Bundle
import io.reactivex.functions.Consumer
import ru.fantlab.android.helper.BundleConstant
import ru.fantlab.android.provider.rest.DataManager
import ru.fantlab.android.ui.base.mvp.presenter.BasePresenter

class AuthorEditionsPresenter : BasePresenter<AuthorEditionsMvp.View>(),
		AuthorEditionsMvp.Presenter {

	@com.evernote.android.state.State
	var authorId: Int? = null

	override fun onFragmentCreated(bundle: Bundle?) {
		if (bundle?.getInt(BundleConstant.EXTRA) == null) {
			throw NullPointerException("Either bundle or AuthorId is null")
		}
		authorId = bundle.getInt(BundleConstant.EXTRA)
		authorId?.let {
			makeRestCall(
					DataManager.getAuthorEditions(it, true)
							.map { it.get() }
							.toObservable(),
					Consumer { authorEditionsResponse ->
						sendToView { it.onInitViews(authorEditionsResponse) }
					}
			)
		}
	}

	override fun onError(throwable: Throwable) {
		authorId?.let { onWorkOffline(it) }
		super.onError(throwable)
	}

	override fun onWorkOffline(id: Int) {
		sendToView { it.showErrorMessage("Не удалось загрузить данные") }
	}
}