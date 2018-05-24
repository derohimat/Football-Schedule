package net.derohimat.footballschedule.common

import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.EventMatchResponse
import java.util.*

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
object TestDataFactory {

    fun makeEventDetail(id: String): EventMatch {
        return EventMatch("11",
                "Soccer",
                " Mamadou Sakho; Emre Can; Martin Skrtel;",
                "",
                "4328",
                " Philippe Coutinho; Raheem Sterling; Adam Lallana;",
                "",
                "69':Own Jonjo Shelvey;61': Adam Lallana;51': Adam Lallana;33': Alberto Moreno;",
                " Lukasz Fabianski",
                " Jonjo Shelvey; Wayne Routledge; Gylfi Sigurdsson; Nathan Dyer; Leon Britton;",
                id,
                "19",
                "4",
                "133602",
                "4",
                "2014-12-29",
                "",
                "Manchester City",
                " Javier Manquillo; Jordan Henderson; Lucas Leiva; Alberto Moreno;",
                "29/12/14",
                "3-4-3",
                "",
                "133614",
                "5",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                " Wilfried Bony;",
                "",
                "",
                "",
                "",
                " Simon Mignolet",
                "",
                "",
                "Manchester United vs City",
                "5",
                " Neil Taylor; Ashley Williams; Federico Fernandez; Ashley Richards;",
                "Manchester United",
                "",
                "English Premier League",
                "1",
                "Manchester",
                ""
        )
    }

    fun makeEventMatchResponse(count: Int): EventMatchResponse {
        val eventList = ArrayList<EventMatch>()
        for (i in 0 until count) {
            eventList.add(makeEventDetail("441613"))
        }
        return EventMatchResponse(eventList)
    }

    fun makeEventListList(list: List<EventMatch>): List<EventMatch> {
        val eventList = ArrayList<EventMatch>()
        for (item in list) {
            eventList.add(item)
        }
        return eventList
    }

    fun makeEventList(count: Int): List<EventMatch> {
        return (0 until count).map { makeEventDetail("441613") }
    }
}