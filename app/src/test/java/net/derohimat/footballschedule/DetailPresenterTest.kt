package net.derohimat.footballschedule

import io.reactivex.Single
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatchResponse
import net.derohimat.footballschedule.features.detail.DetailMvpView
import net.derohimat.footballschedule.features.detail.DetailPresenter
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
class DetailPresenterTest {

    @Mock
    lateinit var mMockDetailMvpView: DetailMvpView
    @Mock
    lateinit var mMockDataManager: DataManager
    private var mDetailPresenter: DetailPresenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mDetailPresenter = DetailPresenter(mMockDataManager)
        mDetailPresenter?.attachView(mMockDetailMvpView)
    }

    @After
    fun tearDown() {
        mDetailPresenter?.detachView()
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

        mDetailPresenter?.getEventDetail(id)

        verify<DetailMvpView>(mMockDetailMvpView, times(2)).showProgress(anyBoolean())
        when {
            eventMatchResponse.events.isNotEmpty() -> {
                verify<DetailMvpView>(mMockDetailMvpView).showEvent(event)
                verify<DetailMvpView>(mMockDetailMvpView, never()).showError("No Data")
            }
            else -> {
                verify<DetailMvpView>(mMockDetailMvpView).showError("No Data")
                verify<DetailMvpView>(mMockDetailMvpView, never()).showEvent(event)
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

        mDetailPresenter?.getEventDetail(id)

        verify<DetailMvpView>(mMockDetailMvpView, times(2)).showProgress(anyBoolean())
        verify<DetailMvpView>(mMockDetailMvpView).showError("No Data")
        verify<DetailMvpView>(mMockDetailMvpView, never()).showEvent(event)
    }

}