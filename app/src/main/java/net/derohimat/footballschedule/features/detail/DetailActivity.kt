package net.derohimat.footballschedule.features.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import butterknife.BindView
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.detail.widget.DetailView
import net.derohimat.footballschedule.util.loadImageFromUrl
import timber.log.Timber
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailMvpView, ErrorView.ErrorListener {

    @Inject
    lateinit var mDetailPresenter: DetailPresenter

    @BindView(R.id.view_error)
    @JvmField
    var mErrorView: ErrorView? = null

    @BindView(R.id.image_badge)
    @JvmField
    var mTeamBadge: ImageView? = null

    @BindView(R.id.progress)
    @JvmField
    var mProgress: ProgressBar? = null

    @BindView(R.id.toolbar)
    @JvmField
    var mToolbar: Toolbar? = null

    @BindView(R.id.layout_detail)
    @JvmField
    var mStatLayout: LinearLayout? = null

    @BindView(R.id.layout_team)
    @JvmField
    var mTeamLayout: View? = null

    private var mTeamId: String? = null
    private var mTeamName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mDetailPresenter.attachView(this)

        mTeamId = intent.getStringExtra(EXTRA_TEAM_ID)
        mTeamName = intent.getStringExtra(EXTRA_TEAM_NAME)
        if (mTeamId == null) {
            throw IllegalArgumentException("Detail Activity requires a team id")
        }

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = mTeamName?.substring(0, 1)?.toUpperCase() + mTeamName?.substring(1)

        mErrorView?.setErrorListener(this)

        mDetailPresenter.getTeamDetail(mTeamId as String)
    }

    override val layout: Int
        get() = R.layout.activity_detail

    override fun showTeam(team: Team) {
        mTeamBadge?.loadImageFromUrl(team.teamBadge)
        mTeamLayout?.visibility = View.VISIBLE
        val detailView = DetailView(this)
        detailView.setTeam(team)
        mStatLayout?.addView(detailView)
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
        mDetailPresenter.getTeamDetail(mTeamId as String)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDetailPresenter.detachView()
    }

    companion object {

        const val EXTRA_TEAM_ID = "EXTRA_TEAM_ID"
        const val EXTRA_TEAM_NAME = "EXTRA_TEAM_NAME"

        fun getStartIntent(context: Context, teamId: String, teamName: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_TEAM_ID, teamId)
            intent.putExtra(EXTRA_TEAM_NAME, teamName)
            return intent
        }
    }
}