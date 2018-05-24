package net.derohimat.footballschedule.runner

import android.app.Application
import android.content.Context
import io.appflate.restmock.android.RESTMockTestRunner
import net.derohimat.footballschedule.FootballApplication

class TestRunner : RESTMockTestRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, FootballApplication::class.java.name, context)
    }

}
