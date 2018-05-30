package net.derohimat.footballschedule.features.team.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import butterknife.BindView
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.db.database
import net.derohimat.footballschedule.data.model.TeamDetail
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.player.list.PlayerActivity
import net.derohimat.footballschedule.features.team.detail.widget.TeamDetailView
import timber.log.Timber
import javax.inject.Inject

class DetailTeamActivity : BaseActivity(), DetailTeamMvpView, ErrorView.ErrorListener {

    @Inject
    lateinit var mPresenter: DetailTeamPresenter

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

    private lateinit var teamDetailView: TeamDetailView

    private var mTeamId: String? = null
    private var mTeamName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mPresenter.attachView(this)

        mTeamId = intent.getStringExtra(EXTRA_ID)
        mTeamName = intent.getStringExtra(EXTRA_NAME)
        if (mTeamId == null) {
            throw IllegalArgumentException("Detail Team Activity requires a team id")
        }

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = mTeamName?.substring(0, 1)?.toUpperCase() + mTeamName?.substring(1)

        mErrorView?.setErrorListener(this)

        mPresenter.getTeamDetail(mTeamId as String)
    }

    override val layout: Int
        get() = R.layout.activity_detail

    override fun showTeam(team: TeamDetail) {
        mTeamLayout?.visibility = View.VISIBLE
        teamDetailView = TeamDetailView(this)
        teamDetailView.setTeam(team, database)
        mTeamLayout?.addView(teamDetailView)
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
        mPresenter.getTeamDetail(mTeamId as String)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    companion object {

        const val EXTRA_ID = "EXTRA_DATA"
        const val EXTRA_NAME = "EXTRA_NAME"

        fun getStartIntent(context: Context, teamId: String, teamName: String): Intent {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra(EXTRA_ID, teamId)
            intent.putExtra(EXTRA_NAME, teamName)
            return intent
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_team_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_player -> {
                startActivity(PlayerActivity.getStartIntent(this, mTeamName as String))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}