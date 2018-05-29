package net.derohimat.footballschedule

import io.reactivex.Single
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.features.main.MainMvpView
import net.derohimat.footballschedule.features.main.MainPresenter
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
class MainPresenterTest {

    @Mock
    lateinit var mMockMainMvpView: MainMvpView
    @Mock
    lateinit var mMockDataManager: DataManager
    private var mMainPresenter: MainPresenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        mMainPresenter = MainPresenter(mMockDataManager)
        mMainPresenter?.attachView(mMockMainMvpView)
    }

    @After
    fun tearDown() {
        mMainPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getEventTest() {
        val eventMatchResponse = TestDataFactory.makeEventMatchResponse()
        val events = eventMatchResponse.events
        val leagueId = "4328"
        `when`(mMockDataManager.getEventMatch(leagueId, 0))
                .thenReturn(Single.just(eventMatchResponse))

        mMainPresenter?.getEvent(leagueId, 0)

        verify<MainMvpView>(mMockMainMvpView, times(2)).showProgress(anyBoolean())
        verify<MainMvpView>(mMockMainMvpView).showEventMatch(events)
        verify<MainMvpView>(mMockMainMvpView, never()).showError("No Data")

    }

    @Test
    @Throws(Exception::class)
    fun getEventReturnsError() {
        val eventList = TestDataFactory.makeEventList()
        val leagueId = "123123"

        `when`(mMockDataManager.getEventMatch(leagueId, 0))
                .thenReturn(Single.error<EventMatchResponse>(Throwable("No Data")))

        mMainPresenter?.getEvent(leagueId, 0)

        verify<MainMvpView>(mMockMainMvpView, times(2)).showProgress(anyBoolean())
        verify<MainMvpView>(mMockMainMvpView).showError("No Data")
        verify<MainMvpView>(mMockMainMvpView, never()).showEventMatch(eventList)
    }

}