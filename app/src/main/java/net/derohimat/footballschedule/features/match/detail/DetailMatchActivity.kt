package net.derohimat.footballschedule.features.match.detail

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
import net.derohimat.footballschedule.data.model.TeamDetail
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.match.detail.widget.MatchDetailView
import timber.log.Timber
import javax.inject.Inject

class DetailMatchActivity : BaseActivity(), DetailMatchMvpView, ErrorView.ErrorListener {

    @Inject
    lateinit var mDetailMatchPresenter: DetailMatchPresenter

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

    lateinit var matchDetailView: MatchDetailView

    private var mEventId: String? = null
    private var mEventName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mDetailMatchPresenter.attachView(this)

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

        mDetailMatchPresenter.getEventDetail(mEventId as String)
    }

    override val layout: Int
        get() = R.layout.activity_detail

    override fun showEvent(eventMatch: EventMatch) {
        mTeamLayout?.visibility = View.VISIBLE
        matchDetailView = MatchDetailView(this)
        matchDetailView.setEvent(eventMatch, database)
        mTeamLayout?.addView(matchDetailView)

        eventMatch.idHomeTeam?.let { mDetailMatchPresenter.getTeamDetail(it, 0) }
        eventMatch.idAwayTeam?.let { mDetailMatchPresenter.getTeamDetail(it, 1) }
    }

    override fun showTeam(team: TeamDetail, type: Int) {
        matchDetailView.setImage(team.teamBadge, type)
    }

    override fun showProgress(show: Boolean) {
        mErrorView?.visibility = View.GONE
        mProgress?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(message: String) {
        mTeamLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        Timber.e(message)
    }

    override fun onReloadData() {
        mDetailMatchPresenter.getEventDetail(mEventId as String)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDetailMatchPresenter.detachView()
    }

    companion object {

        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_NAME = "EXTRA_NAME"

        fun getStartIntent(context: Context, eventId: String, teamName: String): Intent {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(EXTRA_ID, eventId)
            intent.putExtra(EXTRA_NAME, teamName)
            return intent
        }
    }
}