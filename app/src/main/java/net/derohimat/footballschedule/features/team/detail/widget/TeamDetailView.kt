package net.derohimat.footballschedule.features.team.detail.widget

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
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.util.loadImageFromUrl
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.sdk25.coroutines.onClick
import timber.log.Timber

class TeamDetailView : LinearLayout {

    @BindView(R.id.txt_event_name)
    @JvmField
    var txtName: TextView? = null
    @BindView(R.id.txt_description)
    @JvmField
    var txtDescription: TextView? = null
    @BindView(R.id.txt_favorite)
    @JvmField
    var txtFavorite: TextView? = null
    @BindView(R.id.img_home)
    @JvmField
    var imgHome: ImageView? = null

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
        LayoutInflater.from(context).inflate(R.layout.view_detail_team, this)
        ButterKnife.bind(this)
    }

    @SuppressLint("SetTextI18n")
    fun setEvent(team: Team, dbHelper: DatabaseHelper) {
        this.database = dbHelper
        txtName?.text = team.teamName

        isFavorite = isFavoriteEvent(dbHelper, team.teamId)

        checkFavorite()

        imgHome?.loadImageFromUrl(team.teamBadge)

        txtFavorite?.onClick {
            when {
                isFavorite -> {
                    removeFromFavorite(database, team.teamId)
                    isFavorite = false
                    checkFavorite()
                }
                else -> {
                    addToFavorite(database, team)
                    isFavorite = true
                    checkFavorite()
                }
            }
        }
    }

    private fun addToFavorite(database: DatabaseHelper, team: Team) {
        try {
            database.use {
                insert("favorite_team",
                        "idTeam" to team.teamId,
                        "strTeam" to team.teamName,
                        "strTeamBadge" to team.teamBadge
                )
            }
        } catch (e: SQLiteConstraintException) {
            Timber.e(e.message.toString())
        }
    }

    private fun removeFromFavorite(database: DatabaseHelper, teamId: String) {
        try {
            database.use {
                delete("favorite_team",
                        "(idTeam = {idTeam})",
                        "idTeam" to teamId)
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

    private fun isFavoriteEvent(database: DatabaseHelper, teamId: String): Boolean {
        var isFavorite = false
        database.use {
            val result = select("favorite_team")
                    .whereArgs("(idTeam = {idTeam})",
                            "idTeam" to teamId)
            val favorite = result.parseList(classParser<Team>())
            isFavorite = !favorite.isEmpty()
        }
        return isFavorite
    }

}
