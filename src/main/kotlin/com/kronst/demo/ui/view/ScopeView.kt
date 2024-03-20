package com.kronst.demo.ui.view

import com.github.mvysny.karibudsl.v10.KComposite
import com.kronst.demo.ui.UIExceptionHandler
import com.vaadin.flow.component.DetachEvent
import com.vaadin.flow.component.UI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.coroutines.CoroutineContext

abstract class ScopeView : KComposite(), CoroutineScope {

    /**
     * The [SupervisorJob] to which all coroutines started in this view are attached.
     * This allows us to cancel all coroutines when the view is detached.
     */
    private val uiScope = SupervisorJob()

    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

    override val coroutineContext: CoroutineContext
        get() = uiScope + UIExceptionHandler(ui = UI.getCurrent())

    /**
     * Cancels all coroutines started in this view when the view is detached.
     */
    override fun onDetach(detachEvent: DetachEvent) {
        uiScope.cancel()
        super.onDetach(detachEvent)
    }
}
