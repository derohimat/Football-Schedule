package net.derohimat.footballschedule.features.detail.widget

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import net.derohimat.footballschedule.R
import net.derohimat.footballschedule.data.model.Team

class DetailView : RelativeLayout {

    @BindView(R.id.text_name)
    @JvmField
    var mNameText: TextView? = null

    @BindView(R.id.text_id)
    @JvmField
    var mIdText: TextView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_detail, this)
        ButterKnife.bind(this)
    }

    @SuppressLint("SetTextI18n")
    fun setTeam(team: Team) {
        mNameText?.text = team.teamName.substring(0, 1).toUpperCase() + team.teamName.substring(1)
        mIdText?.text = team.teamId
    }
}
