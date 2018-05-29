package net.derohimat.footballschedule.features.match.detail.widget

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.db.DatabaseHelper
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.EventMatchFav
import net.derohimat.footballschedule.util.loadImageFromUrl
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.sdk25.coroutines.onClick
import timber.log.Timber

class DetailView : LinearLayout {

    @BindView(R.id.txt_event_name)
    @JvmField
    var txtName: TextView? = null
    @BindView(R.id.txt_date)
    @JvmField
    var txtDate: TextView? = null
    @BindView(R.id.txt_favorite)
    @JvmField
    var txtFavorite: TextView? = null
    @BindView(R.id.img_home)
    @JvmField
    var imgHome: ImageView? = null
    @BindView(R.id.img_away)
    @JvmField
    var imgAway: ImageView? = null
    @BindView(R.id.txt_home)
    @JvmField
    var txtHome: TextView? = null
    @BindView(R.id.txt_away)
    @JvmField
    var txtAway: TextView? = null
    @BindView(R.id.txt_home_formation)
    @JvmField
    var txtHomeFormation: TextView? = null
    @BindView(R.id.txt_away_formation)
    @JvmField
    var txtAwayFormation: TextView? = null
    @BindView(R.id.txt_score_home)
    @JvmField
    var txtHomeScore: TextView? = null
    @BindView(R.id.txt_score_away)
    @JvmField
    var txtAwayScore: TextView? = null
    @BindView(R.id.txt_home_goal)
    @JvmField
    var txtHomeGoal: TextView? = null
    @BindView(R.id.txt_away_goal)
    @JvmField
    var txtAwayGoal: TextView? = null
    @BindView(R.id.txt_home_shots)
    @JvmField
    var txtHomeShots: TextView? = null
    @BindView(R.id.txt_away_shots)
    @JvmField
    var txtAwayShots: TextView? = null
    @BindView(R.id.txt_home_goalkeeper)
    @JvmField
    var txtHomeGoalKeeper: TextView? = null
    @BindView(R.id.txt_away_goalkeeper)
    @JvmField
    var txtAwayGoalKeeper: TextView? = null
    @BindView(R.id.txt_home_defense)
    @JvmField
    var txtHomeDefender: TextView? = null
    @BindView(R.id.txt_away_defense)
    @JvmField
    var txtAwayDefender: TextView? = null
    @BindView(R.id.txt_home_midfield)
    @JvmField
    var txtHomeMidfielder: TextView? = null
    @BindView(R.id.txt_away_midfield)
    @JvmField
    var txtAwayMidfielder: TextView? = null
    @BindView(R.id.txt_home_forward)
    @JvmField
    var txtHomeForward: TextView? = null
    @BindView(R.id.txt_away_forward)
    @JvmField
    var txtAwayForward: TextView? = null

    private var isFavorite: Boolean = false
    private lateinit var database: DatabaseHelper

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.view_detail, this)
        ButterKnife.bind(this)
    }

    @SuppressLint("SetTextI18n")
    fun setEvent(eventMatch: EventMatch, dbHelper: DatabaseHelper) {
        this.database = dbHelper
        txtName?.text = eventMatch.league?.substring(0, 1)?.toUpperCase() + eventMatch.league?.substring(1)
        txtDate?.text = eventMatch.dateEvent

        isFavorite = isFavoriteEvent(dbHelper, eventMatch.idEvent)

        checkFavorite()

        txtFavorite?.onClick {
            when {
                isFavorite -> {
                    removeFromFavorite(database, eventMatch.idEvent)
                    isFavorite = false
                    checkFavorite()
                }
                else -> {
                    addToFavorite(database, eventMatch)
                    isFavorite = true
                    checkFavorite()
                }
            }
        }

        txtHome?.text = eventMatch.homeTeam
        txtAway?.text = eventMatch.awayTeam
        txtHomeScore?.text = eventMatch.homeScore
        txtAwayScore?.text = eventMatch.awayScore
        txtHomeFormation?.text = eventMatch.homeFormation
        txtAwayFormation?.text = eventMatch.awayFormation
        txtHomeGoal?.text = eventMatch.homeGoalDetails?.replace(";", "\n")
        txtAwayGoal?.text = eventMatch.awayGoalDetails?.replace(";", "\n")
        txtHomeShots?.text = eventMatch.homeShots?.replace("; ", "\n")
        txtAwayShots?.text = eventMatch.awayShots?.replace("; ", "\n")
        txtHomeGoalKeeper?.text = eventMatch.homeLineupGoalkeeper?.replace(";", "\n")
        txtAwayGoalKeeper?.text = eventMatch.awayLineupGoalkeeper?.replace(";", "\n")
        txtHomeDefender?.text = eventMatch.homeLineupDefense?.replace("; ", "\n")
        txtAwayDefender?.text = eventMatch.awayLineupDefense?.replace("; ", "\n")
        txtHomeMidfielder?.text = eventMatch.homeLineupMidfield?.replace("; ", "\n")
        txtAwayMidfielder?.text = eventMatch.awayLineupMidfield?.replace("; ", "\n")
        txtHomeForward?.text = eventMatch.homeLineupForward?.replace("; ", "\n")
        txtAwayForward?.text = eventMatch.awayLineupForward?.replace("; ", "\n")
    }

    fun setImage(imgUrl: String, type: Int) {
        when (type) {
            0 -> imgHome?.loadImageFromUrl(imgUrl)
            else -> {
                imgAway?.loadImageFromUrl(imgUrl)
            }
        }
    }

    private fun addToFavorite(database: DatabaseHelper, eventMatch: EventMatch) {
        try {
            database.use {
                insert("favorite",
                        "idEvent" to eventMatch.idEvent,
                        "event" to eventMatch.event,
                        "dateEvent" to eventMatch.dateEvent,
                        "homeTeam" to eventMatch.homeTeam,
                        "homeScore" to eventMatch.homeScore,
                        "awayTeam" to eventMatch.awayTeam,
                        "awayScore" to eventMatch.awayScore
                )
            }
        } catch (e: SQLiteConstraintException) {
            Timber.e(e.message.toString())
        }
    }

    private fun removeFromFavorite(database: DatabaseHelper, eventId: String) {
        try {
            database.use {
                delete("favorite",
                        "(idEvent = {idEvent})",
                        "idEvent" to eventId)
            }
        } catch (e: SQLiteConstraintException) {
            Timber.e(e.message.toString())
        }
    }

    private fun checkFavorite() {
        when {
            isFavorite -> {
                txtFavorite?.text = context.getString(R.string.remove_from_favorite)
            }
            else -> {
                txtFavorite?.text = context.getString(R.string.add_to_favorite)
            }
        }
    }

    private fun isFavoriteEvent(database: DatabaseHelper, eventId: String): Boolean {
        var isFavorite = false
        database.use {
            val result = select("favorite")
                    .whereArgs("(idEvent = {idEvent})",
                            "idEvent" to eventId)
            val favorite = result.parseList(classParser<EventMatchFav>())
            isFavorite = !favorite.isEmpty()
        }
        return isFavorite
    }

}
