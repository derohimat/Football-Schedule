package net.derohimat.footballschedule.match

import io.reactivex.Single
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.features.match.detail.DetailMatchMvpView
import net.derohimat.footballschedule.features.match.detail.DetailMatchPresenter
import net.derohimat.footballschedule.util.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMatchPresenterTest {

    @Mock
    lateinit var mMockDetailMatchMvpView: DetailMatchMvpView
    @Mock
    lateinit var mMockDataManager: DataManager
    private var mDetailMatchPresenter: DetailMatchPresenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mDetailMatchPresenter = DetailMatchPresenter(mMockDataManager)
        mDetailMatchPresenter?.attachView(mMockDetailMatchMvpView)
    }

    @After
    fun tearDown() {
        mDetailMatchPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getEventDetail() {
        val eventMatchResponse = TestDataFactory.makeEventMatchResponse()
        val event = TestDataFactory.makeEventDetail()
        val id = "441613"
        when {
            eventMatchResponse.events.isNotEmpty() ->
                `when`(mMockDataManager.getEventDetail(id))
                        .thenReturn(Single.just(eventMatchResponse))
            else ->
                `when`(mMockDataManager.getEventDetail(id))
                        .thenReturn(Single.error<EventMatchResponse>(Throwable("No Data")))
        }

        mDetailMatchPresenter?.getEventDetail(id)

        verify<DetailMatchMvpView>(mMockDetailMatchMvpView, times(2)).showProgress(anyBoolean())
        when {
            eventMatchResponse.events.isNotEmpty() -> {
                verify<DetailMatchMvpView>(mMockDetailMatchMvpView).showEvent(event)
                verify<DetailMatchMvpView>(mMockDetailMatchMvpView, never()).showError("No Data")
            }
            else -> {
                verify<DetailMatchMvpView>(mMockDetailMatchMvpView).showError("No Data")
                verify<DetailMatchMvpView>(mMockDetailMatchMvpView, never()).showEvent(event)
            }

        }
    }

    @Test
    @Throws(Exception::class)
    fun getEventDetailReturnsError() {
        val event = TestDataFactory.makeEventDetail()
        val id = "123213"

        `when`(mMockDataManager.getEventDetail(id))
                .thenReturn(Single.error<EventMatchResponse>(Throwable("No Data")))

        mDetailMatchPresenter?.getEventDetail(id)

        verify<DetailMatchMvpView>(mMockDetailMatchMvpView, times(2)).showProgress(anyBoolean())
        verify<DetailMatchMvpView>(mMockDetailMatchMvpView).showError("No Data")
        verify<DetailMatchMvpView>(mMockDetailMatchMvpView, never()).showEvent(event)
    }

}