package net.derohimat.footballschedule

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.reactivex.Observable
import net.derohimat.footballschedule.common.TestComponentRule
import net.derohimat.footballschedule.common.TestDataFactory
import net.derohimat.footballschedule.data.model.EventMatch
import net.derohimat.footballschedule.features.detail.DetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    private val component = TestComponentRule(InstrumentationRegistry.getTargetContext())
    private val main = ActivityTestRule(DetailActivity::class.java, false, false)

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    @JvmField
    var chain: TestRule = RuleChain.outerRule(component).around(main)

    @Test
    fun checkEventDetailDisplays() {
        val event = TestDataFactory.makeEventDetail("441613")
        stubDataManagerGetMatch(Observable.just(event))
        main.launchActivity(
                DetailActivity.getStartIntent(InstrumentationRegistry.getContext(), event.idEvent, event.event as String))

        onView(withText(event.event))
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkErrorViewDisplays() {
        stubDataManagerGetMatch(Observable.error<EventMatch>(RuntimeException()))
        val event = TestDataFactory.makeEventDetail("441613")
        main.launchActivity(
                DetailActivity.getStartIntent(InstrumentationRegistry.getContext(), event.idEvent, event.event as String))
        net.derohimat.footballschedule.util.ErrorTestUtil.checkErrorViewsDisplay()
    }

    private fun stubDataManagerGetMatch(single: Observable<EventMatch>) {
        `when`(component.mockDataManager.getEventDetail(ArgumentMatchers.anyString()))
                .thenReturn(single)
    }

}