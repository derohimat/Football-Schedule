package net.derohimat.footballschedule

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.Single
import net.derohimat.footballschedule.common.TestComponentRule
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.features.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val mComponent = TestComponentRule(InstrumentationRegistry.getTargetContext())
    private val mMain = ActivityTestRule(MainActivity::class.java, false, false)

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    @JvmField
    var chain: TestRule = RuleChain.outerRule(mComponent).around(mMain)

    @Test
    fun checkEventsDisplay() {
        val eventResourceList = TestDataFactory.makeEventList(5)
        val eventList = TestDataFactory.makeEventListList(eventResourceList)
        stubDataManagerGetEventMatchList(Single.just(eventList))
        mMain.launchActivity(null)

        for (eventName in eventResourceList) {
            onView(withText(eventName.homeTeam))
                    .check(matches(isDisplayed()))
        }
    }

    @Test
    fun clickingEventLaunchesDetailActivity() {
        val namedResourceList = TestDataFactory.makeEventList(5)
        val eventList = TestDataFactory.makeEventListList(namedResourceList)
        stubDataManagerGetEventMatchList(Single.just(eventList))
        stubDataManagerGetEventDetail(Observable.just(TestDataFactory.makeEventDetail("441613")))
        mMain.launchActivity(null)

        onView(withText(eventList[0].homeTeam))
                .perform(click())

        onView(withId(R.id.img_home)).check(matches(isDisplayed()))
    }

    @Test
    fun checkErrorViewDisplays() {
        stubDataManagerGetEventMatchList(Single.error<List<EventMatch>>(RuntimeException()))
        mMain.launchActivity(null)
        net.derohimat.footballschedule.util.ErrorTestUtil.checkErrorViewsDisplay()
    }

    private fun stubDataManagerGetEventMatchList(single: Single<List<EventMatch>>) {
        `when`(mComponent.mockDataManager.getEventMatch("4328", 1).toObservable()
                .flatMapIterable { eventMatchResponse -> eventMatchResponse.events }
                .toList())
                .thenReturn(single)
    }

    private fun stubDataManagerGetEventDetail(single: Observable<EventMatch>) {
        `when`(mComponent.mockDataManager.getEventDetail(ArgumentMatchers.anyString()))
                .thenReturn(single)
    }

}