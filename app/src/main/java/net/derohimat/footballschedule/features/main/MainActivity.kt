package net.derohimat.footballschedule.features.main

import android.graphics.Color
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
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.detail.DetailActivity
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView, EventAdapter.ClickListener, ErrorView.ErrorListener, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var mEventAdapter: EventAdapter
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

    @BindView(R.id.bottom_navigation)
    @JvmField
    var mBottomNavigation: AHBottomNavigation? = null

    @BindView(R.id.toolbar)
    @JvmField
    var mToolbar: Toolbar? = null

    private var leagueList: List<League> = ArrayList()
    private var selectedLeague: String = "4328"
    private var selectedType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setSupportActionBar(mToolbar)

        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(R.color.primary)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.white)
        mSwipeRefreshLayout?.setOnRefreshListener { mMainPresenter.getEvent(selectedLeague, selectedType) }

        mEventAdapter.setClickListener(this)
        mRecycler?.layoutManager = LinearLayoutManager(this)
        mRecycler?.adapter = mEventAdapter

        mErrorView?.setErrorListener(this)

        mSpinner?.onItemSelectedListener = this

        setupBottomNavigation()

        mMainPresenter.getLeague()
    }

    private fun setupBottomNavigation() {
        val item1 = AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_skip_previous_white_24dp, R.color.primary)
        val item2 = AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_skip_next_white_24dp, R.color.primary)

        mBottomNavigation?.addItem(item1)
        mBottomNavigation?.addItem(item2)


        mBottomNavigation?.defaultBackgroundColor = Color.parseColor("#FEFEFE")
        mBottomNavigation?.isBehaviorTranslationEnabled = true
        mBottomNavigation?.accentColor = Color.parseColor("#F63D2B")
        mBottomNavigation?.inactiveColor = Color.parseColor("#747474")
        mBottomNavigation?.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        mBottomNavigation?.isColored = true

        mBottomNavigation?.setOnTabSelectedListener({ position, wasSelected ->
            // Do something cool here...
            when (position) {
                0 -> {
                    selectedType = 0
                    mMainPresenter.getEvent(selectedLeague, selectedType)
                }
                else -> {
                    selectedType = 1
                    mMainPresenter.getEvent(selectedLeague, selectedType)
                }
            }
            true
        })
    }

    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    override fun setupAdapter(data: List<League>) {
        leagueList = data

        val dataAdapter = ArrayAdapter<String>(this,
                R.layout.item_league, leagueList.map { league -> league.leagueName })

        mSpinner?.adapter = dataAdapter
    }

    override fun showEventMatch(data: List<EventMatch>) {
        mEventAdapter.setData(data, selectedType)
        mEventAdapter.notifyDataSetChanged()

        mRecycler?.visibility = View.VISIBLE
        mSwipeRefreshLayout?.visibility = View.VISIBLE
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (mRecycler?.visibility == View.VISIBLE && mEventAdapter.itemCount > 0) {
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
        when {
            leagueList.isNotEmpty() -> {
                selectedLeague = leagueList[position].leagueId
                mMainPresenter.getEvent(selectedLeague, selectedType)
            }
        }
    }

    override fun onReloadData() {
        mMainPresenter.getEvent(selectedLeague, selectedType)
    }
}