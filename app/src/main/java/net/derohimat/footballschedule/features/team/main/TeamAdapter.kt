package net.derohimat.footballschedule.features.team.main

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
constructor() : RecyclerView.Adapter<TeamAdapter.EventViewHolder>() {

    private var list: List<Team>
    private lateinit var mClickListener: ClickListener

    init {
        list = emptyList()
    }

    fun setData(data: List<Team>) {
        list = data
    }

    fun clearData() {
        list = emptyList()
        notifyDataSetChanged()
    }

    fun setClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_team, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val team = list[position]
        holder.item = team
        holder.imgBadge?.loadImageFromUrl(team.teamBadge)
        holder.txtTeamHome?.text = team.teamName
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ClickListener {
        fun onTeamClick(teamId: String, teamName: String)
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var item: Team

        @BindView(R.id.img_badge)
        @JvmField
        var imgBadge: ImageView? = null

        @BindView(R.id.txt_team_name)
        @JvmField
        var txtTeamHome: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { mClickListener.onTeamClick(item.teamId, item.teamName) }
        }
    }
}
