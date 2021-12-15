package org.mk.playlist.adapters

import android.content.Context
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.selection.SelectionTracker


class ActionModeController(context: Context, selectionTracker: SelectionTracker<*>) :
    ActionMode.Callback {
    private val context: Context
    private val selectionTracker: SelectionTracker<*>
    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menuItem: MenuItem?): Boolean {
        return false
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        selectionTracker.clearSelection()
    }

    init {
        this.context = context
        this.selectionTracker = selectionTracker
    }
}