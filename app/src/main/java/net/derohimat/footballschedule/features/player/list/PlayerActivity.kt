package net.derohimat.footballschedule.features.player.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Player
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import timber.log.Timber
import javax.inject.Inject

class PlayerActivity : BaseActivity(), PlayerMvpView, PlayerAdapter.ClickListener, ErrorView.ErrorListener {

    @Inject
    lateinit var mAdapter: PlayerAdapter
    @Inject
    lateinit var mPlayerPresenter: PlayerPresenter

    @BindView(R.id.view_error)
    @JvmField
    var mErrorView: ErrorView? = null

    @BindView(R.id.progress)
    @JvmField
    var mProgress: ProgressBar? = null

    @BindView(R.id.recycler_view)
    @JvmField
    var mRecycler: RecyclerView? = null

    @BindView(R.id.swipe_to_refresh)
    @JvmField
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    @BindView(R.id.toolbar)
    @JvmField
    var mToolbar: Toolbar? = null

    private var mTeamName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mPlayerPresenter.attachView(this)

        mTeamName = intent.getStringExtra(EXTRA_NAME)
        if (mTeamName == null) {
            throw IllegalArgumentException("Players Activity requires a team name")
        }

        mToolbar?.title = "$mTeamName Players"
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(R.color.primary)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.white)
        mSwipeRefreshLayout?.setOnRefreshListener {
            getPlayers()
        }

        mAdapter.setClickListener(this)
        mRecycler?.layoutManager = LinearLayoutManager(this)
        mRecycler?.adapter = mAdapter

        mErrorView?.setErrorListener(this)

        getPlayers()
    }

    private fun getPlayers() {
        mTeamName?.let { mPlayerPresenter.getPlayers(it) }
    }

    override val layout: Int
        get() = R.layout.activity_list_player

    override fun onDestroy() {
        super.onDestroy()
        mPlayerPresenter.detachView()
    }

    override fun showPlayer(data: List<Player>) {
        mRecycler?.adapter = mAdapter
        mAdapter.setData(data)
        mAdapter.notifyDataSetChanged()

        mErrorView?.visibility = View.GONE
        mRecycler?.visibility = View.VISIBLE
        mSwipeRefreshLayout?.visibility = View.VISIBLE
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (mRecycler?.visibility == View.VISIBLE && mAdapter.itemCount > 0) {
                mSwipeRefreshLayout?.isRefreshing = true
            } else {
                mProgress?.visibility = View.VISIBLE

                mRecycler?.visibility = View.GONE
                mSwipeRefreshLayout?.visibility = View.GONE
            }

            mErrorView?.visibility = View.GONE
        } else {
            mSwipeRefreshLayout?.isRefreshing = false
            mProgress?.visibility = View.GONE
        }
    }

    override fun showError(message: String) {
        mRecycler?.visibility = View.GONE
        mSwipeRefreshLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        Timber.e(message)
    }

    override fun onPlayerClick(teamId: String, teamName: String) {
//        startActivity(DetailTeamActivity.getStartIntent(this, teamId, mTeamName))
    }

    override fun showNoPlayer() {
        mAdapter.clearData()
        mErrorView?.visibility = View.VISIBLE
    }

    override fun onReloadData() {
        getPlayers()
    }

    companion object {

        const val EXTRA_NAME = "EXTRA_NAME"

        fun getStartIntent(context: Context, teamName: String): Intent {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(EXTRA_NAME, teamName)
            return intent
        }
    }

}