package net.derohimat.footballschedule.common

import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.data.model.EventMatchResponse
import org.mockito.Mockito.mock

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
object TestDataFactory {

    fun makeEventMatchResponse(): EventMatchResponse {
        return EventMatchResponse(makeEventList())
    }

    fun makeEventList(): List<EventMatch> {
        return mutableListOf()
    }

    fun makeEventDetail(): EventMatch {
        return mock(EventMatch::class.java)
    }
}