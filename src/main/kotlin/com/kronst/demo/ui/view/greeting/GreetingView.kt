package com.kronst.demo.ui.view.greeting

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.progressBar
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.kronst.demo.backend.service.GreetingService
import com.kronst.demo.ui.MainLayout
import com.kronst.demo.ui.ScopeComponent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.progressbar.ProgressBar
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@PageTitle("Greeting | SVK")
@Route(value = "greet", layout = MainLayout::class)
class GreetingView(
    private val greetingService: GreetingService
) : ScopeComponent() {

    private lateinit var nameField: TextField
    private lateinit var greetButton: Button
    private lateinit var progress: ProgressBar

    init {
        ui {
            verticalLayout {
                setSizeFull()

                horizontalLayout {
                    verticalLayout(padding = false) {
                        horizontalLayout {
                            nameField = textField("Name") {
                                placeholder = "Enter your name"
                                focus()
                            }

                            greetButton = button("Greet Me") {
                                addThemeVariants(ButtonVariant.LUMO_PRIMARY)
                                addClickShortcut(Key.ENTER)
                                onLeftClick { greet() }
                            }

                            button("Check UI") {
                                addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS)
                                addClickShortcut(Key.SPACE)
                                onLeftClick { checkUI() }
                            }
                        }

                        progress = progressBar {
                            isIndeterminate = true
                            isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun greet() {
        val ui = UI.getCurrent()

        greetButton.isEnabled = false
        nameField.isEnabled = false
        progress.isVisible = true

        launch {
            try {
                val greet = async { greetingService.greet(name = nameField.value) }
                logger.info("awaiting for greetings...")
                val greetings = greet.await()

                ui.access {
                    Notification.show(greetings, 3000, Notification.Position.MIDDLE)
                    nameField.clear()
                }
            } finally {
                ui.access {
                    progress.isVisible = false
                    greetButton.isEnabled = true
                    nameField.isEnabled = true
                }
            }
        }
    }

    private fun checkUI() {
        Notification.show("UI is responsive", 1000, Notification.Position.TOP_END)
            .addThemeVariants(NotificationVariant.LUMO_SUCCESS)
    }
}
