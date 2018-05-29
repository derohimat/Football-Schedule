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
import net.derohimat.footballschedule.data.db.database
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.EventMatchFav
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.detail.DetailActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView, EventAdapter.ClickListener, EventFavAdapter.ClickListener, ErrorView.ErrorListener, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var mEventAdapter: EventAdapter
    @Inject
    lateinit var mEventFavAdapter: EventFavAdapter
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
        mSwipeRefreshLayout?.setOnRefreshListener { getEvent() }

        mEventAdapter.setClickListener(this)
        mEventFavAdapter.setClickListener(this)
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
        val item3 = AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_favorite_white_24dp, R.color.primary)

        mBottomNavigation?.addItem(item1)
        mBottomNavigation?.addItem(item2)
        mBottomNavigation?.addItem(item3)

        mBottomNavigation?.defaultBackgroundColor = Color.parseColor("#FEFEFE")
        mBottomNavigation?.isBehaviorTranslationEnabled = true
        mBottomNavigation?.accentColor = Color.parseColor("#F63D2B")
        mBottomNavigation?.inactiveColor = Color.parseColor("#747474")
        mBottomNavigation?.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        mBottomNavigation?.isColored = true

        mBottomNavigation?.setOnTabSelectedListener({ position, _ ->
            when (position) {
                0 -> {
                    selectedType = 0
                    mMainPresenter.getEvent(selectedLeague, selectedType)
                }
                1 -> {
                    selectedType = 1
                    mMainPresenter.getEvent(selectedLeague, selectedType)
                }
                else -> {
                    selectedType = 2
                    showEventMatchFav(getFavorite())
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
        mEventFavAdapter.clearData()
        mRecycler?.adapter = mEventAdapter
        mEventAdapter.setData(data, selectedType)
        mEventAdapter.notifyDataSetChanged()

        mErrorView?.visibility = View.GONE
        mRecycler?.visibility = View.VISIBLE
        mSwipeRefreshLayout?.visibility = View.VISIBLE
    }

    override fun showEventMatchFav(data: List<EventMatchFav>) {
        mEventAdapter.clearData()
        mRecycler?.adapter = mEventFavAdapter
        mEventFavAdapter.setData(data, selectedType)
        mEventAdapter.notifyDataSetChanged()

        mErrorView?.visibility = View.GONE
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

    override fun showError(message: String) {
        mRecycler?.visibility = View.GONE
        mSwipeRefreshLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        Timber.e(message)
    }

    override fun onTeamClick(eventId: String, eventName: String) {
        startActivity(DetailActivity.getStartIntent(this, eventId, eventName))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun getEvent() {
        when (selectedType) {
            0 -> {
                mMainPresenter.getEvent(selectedLeague, selectedType)
            }
            1 -> {
                mMainPresenter.getEvent(selectedLeague, selectedType)
            }
            else -> {
                showEventMatchFav(getFavorite())
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
            leagueList.isNotEmpty() -> {
                selectedLeague = leagueList[position].leagueId
                getEvent()
            }
        }
    }

    private fun getFavorite(): List<EventMatchFav> {
        return database.use {
            select("favorite").exec {
                parseList(classParser())
            }
        }
    }

    override fun showNoMatch() {
        mEventAdapter.clearData()
        mEventFavAdapter.clearData()
        mErrorView?.visibility = View.VISIBLE
    }

    override fun onReloadData() {
        mMainPresenter.getEvent(selectedLeague, selectedType)
    }
}