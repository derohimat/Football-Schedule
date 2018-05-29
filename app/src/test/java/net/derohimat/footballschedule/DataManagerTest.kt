package net.derohimat.footballschedule

import io.reactivex.Single
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.DataManager
import net.derohimat.footballschedule.data.remote.FootBallApi
import net.derohimat.footballschedule.util.RxSchedulersOverrideRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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
    lateinit var mMockFootBallApi: FootBallApi

    private var mDataManager: DataManager? = null

    @Before
    fun setUp() {
        mDataManager = DataManager(mMockFootBallApi)
    }

    @Test
    fun getEventsTest() {
        val id = "4328"
        val eventMatchResponse = TestDataFactory.makeEventMatchResponse()

        `when`(mMockFootBallApi.getEventDetail(id))
                .thenReturn(Single.just(eventMatchResponse))

        mDataManager?.getEventDetail(id)
                ?.test()
                ?.assertComplete()
                ?.assertValue(TestDataFactory.makeEventMatchResponse())
    }

    @Test
    fun getEventDetailTest() {
        val eventMatchResponse = TestDataFactory.makeEventMatchResponse()
        val id = "441613"

        `when`(mMockFootBallApi.getEventDetail(id))
                .thenReturn(Single.just(eventMatchResponse))

        mDataManager?.getEventDetail(id)
                ?.test()
                ?.assertComplete()
                ?.assertValue(TestDataFactory.makeEventMatchResponse())
    }
}
