package net.derohimat.footballschedule

import io.reactivex.Observable
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.features.detail.DetailMvpView
import net.derohimat.footballschedule.features.detail.DetailPresenter
import net.derohimat.footballschedule.util.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
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
    fun getEventDetailReturnsEventDetail() {
        val eventMatch = TestDataFactory.makeEventDetail("441613")
        `when`(mMockDataManager.getEventDetail(anyString()))
                .thenReturn(Observable.just(eventMatch))

        mDetailPresenter?.getEventDetail(anyString())

        verify<DetailMvpView>(mMockDetailMvpView, times(2)).showProgress(anyBoolean())
        verify<DetailMvpView>(mMockDetailMvpView).showEvent(eventMatch)
        verify<DetailMvpView>(mMockDetailMvpView, never()).showError(RuntimeException())
    }

    @Test
    @Throws(Exception::class)
    fun getEventDetailReturnsError() {
        `when`(mMockDataManager.getEventDetail("441613"))
                .thenReturn(Observable.error<EventMatch>(RuntimeException()))

        mDetailPresenter?.getEventDetail("441613")

        verify<DetailMvpView>(mMockDetailMvpView, times(2)).showProgress(anyBoolean())
    }

}