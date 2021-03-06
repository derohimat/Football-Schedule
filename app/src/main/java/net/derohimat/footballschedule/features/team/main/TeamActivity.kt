package net.derohimat.footballschedule.features.team.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import butterknife.BindView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.miguelcatalan.materialsearchview.MaterialSearchView
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.db.database
import net.derohimat.footballschedule.data.model.League
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.common.ErrorView
import net.derohimat.footballschedule.features.match.main.MatchActivity
import net.derohimat.footballschedule.features.team.detail.DetailTeamActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TeamActivity : BaseActivity(), TeamMvpView, TeamAdapter.ClickListener, ErrorView.ErrorListener, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var mAdapter: TeamAdapter
    @Inject
    lateinit var mTeamPresenter: TeamPresenter

    @BindView(R.id.spinner_league)
    lateinit var mSpinner: AppCompatSpinner

    @BindView(R.id.view_error)
    lateinit var mErrorView: ErrorView

    @BindView(R.id.progress)
    lateinit var mProgress: ProgressBar

    @BindView(R.id.recycler_view)
    lateinit var mRecycler: RecyclerView

    @BindView(R.id.swipe_to_refresh)
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.bottom_navigation)
    lateinit var mBottomNavigation: AHBottomNavigation

    @BindView(R.id.search_view)
    lateinit var mSearchView: MaterialSearchView

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    private var leagueList: List<League> = ArrayList()
    private var selectedLeague: String = "English Premier League"
    private var selectedType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mTeamPresenter.attachView(this)

        mToolbar.title = "Teams"
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white)
        mSwipeRefreshLayout.setOnRefreshListener { getEvent() }

        mAdapter.setClickListener(this)
        mRecycler.layoutManager = LinearLayoutManager(this)
        mRecycler.adapter = mAdapter

        mErrorView.setErrorListener(this)

        mSpinner.onItemSelectedListener = this

        setupSearchView()
        setupBottomNavigation()

        mTeamPresenter.getLeague()
    }

    private fun setupSearchView() {
        mSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Do some magic
                mTeamPresenter.searchTeams(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })

        mSearchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })
    }

    private fun setupBottomNavigation() {
        val item1 = AHBottomNavigationItem(R.string.team_tab_1, R.drawable.ic_team_24dp, R.color.primary)
        val item2 = AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_favorite_white_24dp, R.color.primary)

        mBottomNavigation.addItem(item1)
        mBottomNavigation.addItem(item2)

        mBottomNavigation.defaultBackgroundColor = Color.parseColor("#FEFEFE")
        mBottomNavigation.isBehaviorTranslationEnabled = true
        mBottomNavigation.accentColor = Color.parseColor("#F63D2B")
        mBottomNavigation.inactiveColor = Color.parseColor("#747474")
        mBottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        mBottomNavigation.isColored = true

        mBottomNavigation.setOnTabSelectedListener({ position, _ ->
            when (position) {
                0 -> {
                    mSpinner.visibility = View.VISIBLE
                    selectedType = 0
                    mTeamPresenter.getTeams(selectedLeague)
                }
                else -> {
                    mSpinner.visibility = View.GONE
                    selectedType = 1
                    showTeam(getFavorite())
                }
            }
            true
        })
    }

    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mTeamPresenter.detachView()
    }

    override fun setupAdapter(data: List<League>) {
        leagueList = data

        val dataAdapter = ArrayAdapter<String>(this,
                R.layout.item_league, leagueList.map { league -> league.leagueName })

        mSpinner.adapter = dataAdapter
    }

    override fun showTeam(data: List<Team>) {
        mRecycler.adapter = mAdapter
        mAdapter.setData(data)
        mAdapter.notifyDataSetChanged()

        mErrorView.visibility = View.GONE
        mRecycler.visibility = View.VISIBLE
        mSwipeRefreshLayout.visibility = View.VISIBLE
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (mRecycler.visibility == View.VISIBLE && mAdapter.itemCount > 0) {
                mSwipeRefreshLayout.isRefreshing = true
            } else {
                mProgress.visibility = View.VISIBLE

                mRecycler.visibility = View.GONE
                mSwipeRefreshLayout.visibility = View.GONE
            }

            mErrorView.visibility = View.GONE
        } else {
            mSwipeRefreshLayout.isRefreshing = false
            mProgress.visibility = View.GONE
        }
    }

    override fun showError(message: String) {
        mRecycler.visibility = View.GONE
        mSwipeRefreshLayout.visibility = View.GONE
        mErrorView.visibility = View.VISIBLE
        Timber.e(message)
    }

    override fun onTeamClick(teamId: String, teamName: String) {
        startActivity(DetailTeamActivity.getStartIntent(this, teamId, teamName))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun getEvent() {
        when (selectedType) {
            0 -> mTeamPresenter.getTeams(selectedLeague)
            else -> {
                showTeam(getFavorite())
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
            leagueList.isNotEmpty() -> {
                selectedLeague = leagueList[position].leagueName
                getEvent()
            }
        }
    }

    private fun getFavorite(): List<Team> {
        mSwipeRefreshLayout.isRefreshing = false
        return database.use {
            select("favorite_team").exec {
                parseList(classParser())
            }
        }
    }

    override fun showNoTeam() {
        mAdapter.clearData()
        mErrorView.visibility = View.VISIBLE
    }

    override fun onReloadData() {
        mTeamPresenter.getTeams(selectedLeague)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, false)
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (mSearchView.isSearchOpen) {
            mSearchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.team_menu, menu)

        val item = menu.findItem(R.id.action_search)
        mSearchView.setMenuItem(item)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_match -> {
                startActivity<MatchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}