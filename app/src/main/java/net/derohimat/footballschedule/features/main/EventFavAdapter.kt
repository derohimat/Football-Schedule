package net.derohimat.footballschedule.features.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.EventMatchFav
import javax.inject.Inject

class EventFavAdapter @Inject
constructor() : RecyclerView.Adapter<EventFavAdapter.EventViewHolder>() {

    private var list: List<EventMatchFav>
    private var eventType: Int = 0
    private lateinit var mClickListener: ClickListener

    init {
        list = emptyList()
    }

    fun setData(data: List<EventMatchFav>, type: Int) {
        list = data
        eventType = type
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
                .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventMatch = list[position]
        holder.item = eventMatch
        holder.txtDate?.text = eventMatch.dateEvent
        holder.txtTeamHome?.text = eventMatch.homeTeam
        holder.txtTeamAway?.text = eventMatch.awayTeam

        when (eventType) {
            1 -> {
                holder.txtScoreHome?.text = "-"
                holder.txtScoreAway?.text = "-"
            }
            else -> {
                holder.txtScoreHome?.text = eventMatch.homeScore
                holder.txtScoreAway?.text = eventMatch.awayScore
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ClickListener {
        fun onTeamClick(eventId: String, eventName: String)
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var item: EventMatchFav

        @BindView(R.id.txt_date)
        @JvmField
        var txtDate: TextView? = null

        @BindView(R.id.txt_team_home)
        @JvmField
        var txtTeamHome: TextView? = null

        @BindView(R.id.txt_score_home)
        @JvmField
        var txtScoreHome: TextView? = null

        @BindView(R.id.txt_team_away)
        @JvmField
        var txtTeamAway: TextView? = null

        @BindView(R.id.txt_score_away)
        @JvmField
        var txtScoreAway: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { mClickListener.onTeamClick(item.idEvent, item.event as String) }
        }
    }
}
