package net.derohimat.footballschedule.features.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import butterknife.BindView
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.db.database
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.detail.widget.DetailView
import timber.log.Timber
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailMvpView, ErrorView.ErrorListener {

    @Inject
    lateinit var mDetailPresenter: DetailPresenter

    @BindView(R.id.view_error)
    @JvmField
    var mErrorView: ErrorView? = null

    @BindView(R.id.progress)
    @JvmField
    var mProgress: ProgressBar? = null

    @BindView(R.id.toolbar)
    @JvmField
    var mToolbar: Toolbar? = null

    @BindView(R.id.layout_team)
    @JvmField
    var mTeamLayout: LinearLayout? = null

    lateinit var detailView: DetailView

    private var mEventId: String? = null
    private var mEventName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mDetailPresenter.attachView(this)

        mEventId = intent.getStringExtra(EXTRA_ID)
        mEventName = intent.getStringExtra(EXTRA_NAME)
        if (mEventId == null) {
            throw IllegalArgumentException("Detail Activity requires a event id")
        }

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = mEventName?.substring(0, 1)?.toUpperCase() + mEventName?.substring(1)

        mErrorView?.setErrorListener(this)

        mDetailPresenter.getEventDetail(mEventId as String)
    }

    override val layout: Int
        get() = R.layout.activity_detail

    override fun showEvent(eventMatch: EventMatch) {
        mTeamLayout?.visibility = View.VISIBLE
        detailView = DetailView(this)
        detailView.setEvent(eventMatch, database)
        mTeamLayout?.addView(detailView)

        eventMatch.idHomeTeam?.let { mDetailPresenter.getTeamDetail(it, 0) }
        eventMatch.idAwayTeam?.let { mDetailPresenter.getTeamDetail(it, 1) }
    }

    override fun showTeam(team: Team, type: Int) {
        detailView.setImage(team.teamBadge, type)
    }

    override fun showProgress(show: Boolean) {
        mErrorView?.visibility = View.GONE
        mProgress?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(error: Throwable) {
        mTeamLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        Timber.e(error, "There was a problem retrieving data...")
    }

    override fun onReloadData() {
        mDetailPresenter.getEventDetail(mEventId as String)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDetailPresenter.detachView()
    }

    companion object {

        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_NAME = "EXTRA_NAME"

        fun getStartIntent(context: Context, teamId: String, teamName: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, teamId)
            intent.putExtra(EXTRA_NAME, teamName)
            return intent
        }
    }
}