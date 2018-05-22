package ru.fantlab.android.ui.modules.work.overview

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import butterknife.BindView
import com.google.gson.GsonBuilder
import ru.fantlab.android.R
import ru.fantlab.android.data.dao.newmodel.Work
import ru.fantlab.android.helper.BundleConstant
import ru.fantlab.android.helper.Bundler
import ru.fantlab.android.ui.base.BaseFragment
import timber.log.Timber

class WorkOverviewFragment : BaseFragment<WorkOverviewMvp.View, WorkOverviewPresenter>(),
		WorkOverviewMvp.View {

	@BindView(R.id.progress) lateinit var progress: View
	private var work: Work? = null

	override fun fragmentLayout() = R.layout.work_overview_layout

	override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
		if (savedInstanceState == null) {
			presenter.onFragmentCreated(arguments)
		} else {
			work = savedInstanceState.getParcelable("work")
			if (work != null) {
				onInitViews(work!!)
			} else {
				presenter.onFragmentCreated(arguments)
			}
		}
	}

	override fun providePresenter() = WorkOverviewPresenter()

	override fun onInitViews(work: Work) {
		hideProgress()
		Timber.d("work: ${GsonBuilder().setPrettyPrinting().create().toJson(work)}")
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putParcelable("work", work)
	}

	override fun showProgress(@StringRes resId: Int, cancelable: Boolean) {
		progress.visibility = View.VISIBLE
	}

	override fun hideProgress() {
		progress.visibility = View.GONE
	}

	override fun showErrorMessage(msgRes: String) {
		hideProgress()
		super.showErrorMessage(msgRes)
	}

	override fun showMessage(titleRes: Int, msgRes: Int) {
		hideProgress()
		super.showMessage(titleRes, msgRes)
	}

	companion object {

		fun newInstance(workId: Int): WorkOverviewFragment {
			val view = WorkOverviewFragment()
			view.arguments = Bundler.start().put(BundleConstant.EXTRA, workId).end()
			return view
		}
	}
}