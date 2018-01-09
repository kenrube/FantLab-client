package org.odddev.fantlab.edition

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.odddev.fantlab.author.IAuthorProvider
import org.odddev.fantlab.author.models.Author
import org.odddev.fantlab.core.db.MainDatabase
import org.odddev.fantlab.core.di.Injector
import org.odddev.fantlab.core.network.IServerApi
import javax.inject.Inject

class EditionProvider : IEditionProvider {

	@Inject
	lateinit var serverApi: IServerApi

	@Inject
	lateinit var database: MainDatabase

	init {
		Injector.getAppComponent().inject(this)
	}

	override fun getEdition(id: Int): Flowable<Edition> {
		serverApi.getAuthor(id)
				.subscribeOn(Schedulers.io())
				.subscribe {
					response -> run {
						database.authorDao().saveAuthorFromResponse(response)
						database.authorPseudonymDao().saveAuthorPseudonymsFromResponse(response)
						database.authorStatDao().saveAuthorStatFromResponse(response)
						database.authorDao().saveWorksAuthorsFromResponse(response)
					// todo аналогично сохранить художников
					// todo сохранить ворки
					// todo сохранить связи ворков друг с другом. вроде все, можно отрисовывать
					}
				}
		return database.authorDao()
				.getAsFlowable(id)
				.distinctUntilChanged()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
	}
}
