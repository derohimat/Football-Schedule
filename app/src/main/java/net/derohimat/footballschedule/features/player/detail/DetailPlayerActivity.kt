package net.derohimat.footballschedule.features.player.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import butterknife.BindView
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Player
import net.derohimat.footballschedule.features.base.BaseActivity
import net.derohimat.footballschedule.features.player.detail.widget.PlayerDetailView

class DetailPlayerActivity : BaseActivity() {

    @BindView(R.id.progress)
    @JvmField
    var mProgress: ProgressBar? = null

    @BindView(R.id.toolbar)
    @JvmField
    var mToolbar: Toolbar? = null

    @BindView(R.id.layout_team)
    @JvmField
    var mTeamLayout: LinearLayout? = null

    private lateinit var teamDetailView: PlayerDetailView

    private var mPlayer: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)

        mPlayer = intent.getParcelableExtra(EXTRA_DATA)
        if (mPlayer == null) {
            throw IllegalArgumentException("Detail Player Activity requires a player model")
        }

        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        mPlayer?.let {
            title = it.player.substring(0, 1).toUpperCase() + it.player.substring(1)
            showPlayer(it)
        }
    }

    override val layout: Int
        get() = R.layout.activity_detail

    private fun showPlayer(player: Player) {
        mTeamLayout?.visibility = View.VISIBLE
        teamDetailView = PlayerDetailView(this)
        teamDetailView.setPlayer(player)
        mTeamLayout?.addView(teamDetailView)
    }

    companion object {

        const val EXTRA_DATA = "EXTRA_DATA"

        fun getStartIntent(context: Context, player: Player): Intent {
            val intent = Intent(context, DetailPlayerActivity::class.java)
            intent.putExtra(EXTRA_DATA, player)
            return intent
        }
    }
}