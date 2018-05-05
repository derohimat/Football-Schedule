package net.derohimat.footballschedule.features.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Team
import net.derohimat.footballschedule.util.loadImageFromUrl
import javax.inject.Inject

class TeamAdapter @Inject
constructor() : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    private var mTeam: List<Team>
    private lateinit var mClickListener: ClickListener

    init {
        mTeam = emptyList()
    }

    fun setData(data: List<Team>) {
        mTeam = data
    }

    fun setClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = mTeam[position]
        holder.mTeam = team
        holder.nameText?.text = team.teamName
        holder.imageBadge?.loadImageFromUrl(team.teamBadge)
    }

    override fun getItemCount(): Int {
        return mTeam.size
    }

    interface ClickListener {
        fun onTeamClick(teamId: String, teamName: String)
    }

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var mTeam: Team
        @BindView(R.id.text_name)
        @JvmField
        var nameText: TextView? = null
        @BindView(R.id.image_badge)
        @JvmField
        var imageBadge: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { mClickListener.onTeamClick(mTeam.teamId, mTeam.teamName) }
        }
    }
}
