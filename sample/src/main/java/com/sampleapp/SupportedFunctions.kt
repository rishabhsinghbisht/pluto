package com.sampleapp

import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.sampleapp.functions.datastore.DemoDatastorePrefFragment
import com.sampleapp.functions.exceptions.DemoExceptionFragment
import com.sampleapp.functions.logger.DemoLoggerFragment
import com.sampleapp.functions.network.DemoNetworkFragment
import com.sampleapp.functions.roomsdatabase.DemoRoomsDatabaseFragment
import com.sampleapp.functions.sharedpreferences.DemoSharedPrefFragment

class SupportedPlugins private constructor() {
    companion object {
        internal fun get(): List<FunctionsModel> {
            return listOf(
                FunctionsModel(NETWORK, "Network & API calls"),
                FunctionsModel(EXCEPTIONS, "Exceptions & Crashes"),
                FunctionsModel(LOGGER, "Logger"),
                FunctionsModel(SHARED_PREF, "Shared Preferences"),
                FunctionsModel(ROOMS_DATABASE, "Rooms Database"),
                FunctionsModel(DATASTORE_PREF, "Datastore Preferences")
            )
        }

        fun getDemoFragment(id: String): Fragment {
            return when (id) {
                NETWORK -> DemoNetworkFragment()
                EXCEPTIONS -> DemoExceptionFragment()
                SHARED_PREF -> DemoSharedPrefFragment()
                DATASTORE_PREF -> DemoDatastorePrefFragment()
                LOGGER -> DemoLoggerFragment()
                ROOMS_DATABASE -> DemoRoomsDatabaseFragment()
                else -> DemoNetworkFragment()
            }
        }

        const val DEMO: String = "demo"
        const val NETWORK: String = "network"
        const val EXCEPTIONS: String = "exceptions"
        const val SHARED_PREF: String = "shared-preferences"
        const val LOGGER: String = "logger"
        const val ROOMS_DATABASE: String = "rooms-database"
        const val DATASTORE_PREF: String = "datastore-preferences"
    }
}

@Keep
internal data class FunctionsModel(val id: String, val label: String)
