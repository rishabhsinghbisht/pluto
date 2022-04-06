package com.pluto.plugins.network.internal.mock.ui.list

import android.view.ViewGroup
import com.pluto.plugin.utilities.list.BaseAdapter
import com.pluto.plugin.utilities.list.DiffAwareHolder
import com.pluto.plugin.utilities.list.ListItem
import com.pluto.plugins.network.internal.mock.logic.dao.MockSettingsEntity

internal class MockSettingsItemAdapter(private val listener: OnActionListener) : BaseAdapter() {
    override fun getItemViewType(item: ListItem): Int? {
        return when (item) {
            is MockSettingsEntity -> ITEM_TYPE_NETWORK_PROXY
            else -> null
        }
    }

    override fun onViewHolderCreated(parent: ViewGroup, viewType: Int): DiffAwareHolder? {
        return when (viewType) {
            ITEM_TYPE_NETWORK_PROXY -> MockSettingsItemHolder(parent, listener)
            else -> null
        }
    }

    companion object {
        const val ITEM_TYPE_NETWORK_PROXY = 1000
    }
}
