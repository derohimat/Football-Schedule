package net.derohimat.footballschedule.features.player.detail.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Player
import net.derohimat.footballschedule.util.loadImageFromUrl

class PlayerDetailView : LinearLayout {

    @BindView(R.id.txt_team_name)
    @JvmField
    var txtName: TextView? = null
    @BindView(R.id.txt_description)
    @JvmField
    var txtDescription: TextView? = null
    @BindView(R.id.txt_join_date)
    @JvmField
    var txtJoinDate: TextView? = null
    @BindView(R.id.txt_position)
    @JvmField
    var txtPosition: TextView? = null
    @BindView(R.id.txt_nationality)
    @JvmField
    var txtNationality: TextView? = null
    @BindView(R.id.txt_birth_info)
    @JvmField
    var txtBirthInfo: TextView? = null
    @BindView(R.id.img_thumb)
    @JvmField
    var imgThumb: ImageView? = null

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
        LayoutInflater.from(context).inflate(R.layout.view_detail_player, this)
        ButterKnife.bind(this)
    }

    @SuppressLint("SetTextI18n")
    fun setPlayer(player: Player) {
        txtName?.text = player.player.substring(0, 1).toUpperCase() + player.player.substring(1)
        txtDescription?.text = player.descriptionEN
        txtJoinDate?.text = player.dateSigned
        txtPosition?.text = player.position
        txtBirthInfo?.text = "${player.birthLocation} / ${player.dateBorn}"
        txtNationality?.text = player.nationality

        imgThumb?.loadImageFromUrl(player.thumb)

    }

}
