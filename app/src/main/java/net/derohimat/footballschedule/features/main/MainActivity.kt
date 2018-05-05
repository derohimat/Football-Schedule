package net.derohimat.footballschedule.features.main

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import butterknife.BindView
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.detail.DetailActivity
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView, TeamAdapter.ClickListener, ErrorView.ErrorListener, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var mTeamAdapter: TeamAdapter
    @Inject
    lateinit var mMainPresenter: MainPresenter

    @BindView(R.id.spinner_league)
    @JvmField
    var mSpinner: AppCompatSpinner? = null

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

    private var leagueList: List<String> = ArrayList()
    private var selectedLeague: String = "English Premier League"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setSupportActionBar(mToolbar)

        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(R.color.primary)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.white)
        mSwipeRefreshLayout?.setOnRefreshListener { mMainPresenter.getTeams(selectedLeague) }

        mTeamAdapter.setClickListener(this)
        mRecycler?.layoutManager = LinearLayoutManager(this)
        mRecycler?.adapter = mTeamAdapter

        mErrorView?.setErrorListener(this)

        mSpinner?.onItemSelectedListener = this

        setupAdapter(resources.getStringArray(R.array.top_league).toList())

        mMainPresenter.getLeague()
    }

    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    override fun setupAdapter(data: List<String>) {
        leagueList = data

        val dataAdapter = ArrayAdapter<String>(this,
                R.layout.item_league, leagueList)

        mSpinner?.adapter = dataAdapter
    }

    override fun showTeams(data: List<Team>) {
        mTeamAdapter.setData(data)
        mTeamAdapter.notifyDataSetChanged()

        mRecycler?.visibility = View.VISIBLE
        mSwipeRefreshLayout?.visibility = View.VISIBLE
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (mRecycler?.visibility == View.VISIBLE && mTeamAdapter.itemCount > 0) {
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

    override fun showError(error: Throwable) {
        mRecycler?.visibility = View.GONE
        mSwipeRefreshLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        Timber.e(error, "There was an error retrieving data")
    }

    override fun onTeamClick(teamId: String, teamName: String) {
        startActivity(DetailActivity.getStartIntent(this, teamId, teamName))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedLeague = leagueList[position]
        mMainPresenter.getTeams(selectedLeague)
    }

    override fun onReloadData() {
        mMainPresenter.getTeams(selectedLeague)
    }
}