package net.derohimat.footballschedule

import io.reactivex.Single
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.data.remote.FootBallApi
import net.derohimat.footballschedule.util.RxSchedulersOverrideRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Rule
    @JvmField
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()
    @Mock
    private
    lateinit var mMockPokemonApi: FootBallApi

    private var mDataManager: DataManager? = null

    @Before
    fun setUp() {
        mDataManager = DataManager(mMockPokemonApi)
    }

    @Test
    fun getEventListCompletesAndEmitsEventList() {
        val eventMatchResponse = TestDataFactory.makeEventMatchResponse(5)
        val eventListResponse = EventMatchResponse(eventMatchResponse.events)

        `when`(mMockPokemonApi.getEventDetail(anyString()))
                .thenReturn(Single.just(eventListResponse))

        mDataManager?.getEventMatch("4328", 1)
                ?.test()
                ?.assertComplete()
                ?.assertValue(TestDataFactory.makeEventMatchResponse(10))
    }

    @Test
    fun getEventsCompletesAndEmitsEvents() {
        val id = anyInt()
        val eventMatch = TestDataFactory.makeEventMatchResponse(id)
        `when`(mMockPokemonApi.getEventDetail(anyString()))
                .thenReturn(Single.just(eventMatch))

        mDataManager?.getEventDetail(id.toString())
                ?.test()
                ?.assertComplete()
                ?.assertValue(TestDataFactory.makeEventDetail(id.toString()))
    }
}
