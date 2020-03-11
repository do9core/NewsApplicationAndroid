package xyz.do9core.crashreporter

import android.app.Application
import splitties.initprovider.InitProvider

class Installer : InitProvider() {

    override fun onCreate(): Boolean = true.also {
        CrashReportUtil.install(context!!.applicationContext as Application)
    }
}