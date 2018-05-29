package net.derohimat.footballschedule.match

import io.reactivex.Single
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.features.match.main.MatchMvpView
import net.derohimat.footballschedule.features.match.main.MatchPresenter
import net.derohimat.footballschedule.util.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MatchPresenterTest {

    @Mock
    lateinit var mMockMatchMvpView: MatchMvpView
    @Mock
    lateinit var mMockDataManager: DataManager
    private var mMatchPresenter: MatchPresenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        mMatchPresenter = MatchPresenter(mMockDataManager)
        mMatchPresenter?.attachView(mMockMatchMvpView)
    }

    @After
    fun tearDown() {
        mMatchPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getEventTest() {
        val eventMatchResponse = TestDataFactory.makeEventMatchResponse()
        val events = eventMatchResponse.events
        val leagueId = "4328"
        `when`(mMockDataManager.getEventMatch(leagueId, 0))
                .thenReturn(Single.just(eventMatchResponse))

        mMatchPresenter?.getEvent(leagueId, 0)

        verify<MatchMvpView>(mMockMatchMvpView, times(2)).showProgress(anyBoolean())
        verify<MatchMvpView>(mMockMatchMvpView).showEventMatch(events)
        verify<MatchMvpView>(mMockMatchMvpView, never()).showError("No Data")

    }

    @Test
    @Throws(Exception::class)
    fun getEventReturnsError() {
        val eventList = TestDataFactory.makeEventList()
        val leagueId = "123123"

        `when`(mMockDataManager.getEventMatch(leagueId, 0))
                .thenReturn(Single.error<EventMatchResponse>(Throwable("No Data")))

        mMatchPresenter?.getEvent(leagueId, 0)

        verify<MatchMvpView>(mMockMatchMvpView, times(2)).showProgress(anyBoolean())
        verify<MatchMvpView>(mMockMatchMvpView).showError("No Data")
        verify<MatchMvpView>(mMockMatchMvpView, never()).showEventMatch(eventList)
    }

}