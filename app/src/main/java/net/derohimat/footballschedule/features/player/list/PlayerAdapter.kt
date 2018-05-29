package net.derohimat.footballschedule.features.player.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Player
import net.derohimat.footballschedule.util.loadImageFromUrl
import javax.inject.Inject

class PlayerAdapter @Inject
constructor() : RecyclerView.Adapter<PlayerAdapter.EventViewHolder>() {

    private var list: List<Player>
    private lateinit var mClickListener: ClickListener

    init {
        list = emptyList()
    }

    fun setData(data: List<Player>) {
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
                .inflate(R.layout.item_player, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val team = list[position]
        holder.item = team
        holder.imgThumb?.loadImageFromUrl(team.thumb)
        holder.txtPlayerName?.text = team.player
        holder.txtPosition?.text = team.position
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ClickListener {
        fun onPlayerClick(teamId: String, teamName: String)
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var item: Player

        @BindView(R.id.img_thumb)
        @JvmField
        var imgThumb: ImageView? = null

        @BindView(R.id.txt_player_name)
        @JvmField
        var txtPlayerName: TextView? = null

        @BindView(R.id.txt_position)
        @JvmField
        var txtPosition: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { mClickListener.onPlayerClick(item.idPlayer, item.player) }
        }
    }
}
