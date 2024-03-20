package com.kronst.demo.ui

import com.vaadin.flow.component.UI
import com.vaadin.flow.server.ErrorEvent
import com.vaadin.flow.server.VaadinSession
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * An exception handler which reports uncaught exceptions to the Vaadin error handler.
 */
class UIExceptionHandler(private val ui: UI) : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        ui.access {
            val handler = VaadinSession.getCurrent().errorHandler
            val event = ErrorEvent(exception)
            handler.error(event)
        }
    }
}
