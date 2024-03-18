package com.kronst.demo.ui

import com.vaadin.flow.component.UI
import com.vaadin.flow.server.ErrorEvent
import com.vaadin.flow.server.VaadinSession
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

object ViewUtils {
    /**
     * A [CoroutineDispatcher] which dispatches the coroutine to the UI thread.
     */
    private class UIDispatcher(val ui: UI) : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            ui.access { block.run() }
        }
    }

    /**
     * An exception handler which reports uncaught exceptions to the Vaadin error handler.
     */
    private class UIExceptionHandler(val ui: UI) : CoroutineExceptionHandler {
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

    /**
     * Provides a [CoroutineContext] which dispatches coroutines to the UI thread and reports uncaught exceptions to the Vaadin error handler.
     */
    fun uiContext(ui: UI = UI.getCurrent()): CoroutineContext {
        return UIDispatcher(ui) + UIExceptionHandler(ui)
    }
}
