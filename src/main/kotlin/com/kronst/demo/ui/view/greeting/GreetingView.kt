package com.kronst.demo.ui.view.greeting

import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.kronst.demo.backend.service.GreetingService
import com.kronst.demo.ui.MainLayout
import com.kronst.demo.ui.view.ScopeView
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.progressbar.ProgressBar
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import kotlinx.coroutines.launch

@PageTitle("Greeting | SVK")
@Route(value = "greet", layout = MainLayout::class)
class GreetingView(
    private val greetingService: GreetingService
) : ScopeView() {

    private val root = ui {
        verticalLayout {
            setSizeFull()
        }
    }

    private val nameField = TextField("Name", "Enter you name")
    private val greetButton = Button("Greet Me")
    private val checkUIButton = Button("Check UI")
    private val progress = ProgressBar().apply {
        isIndeterminate = true
        isVisible = false
    }

    init {
        checkUIButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        checkUIButton.addClickShortcut(Key.SPACE)
        checkUIButton.onLeftClick { checkUI() }

        greetButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)
        greetButton.addClickShortcut(Key.ENTER)
        greetButton.onLeftClick { greet() }

        val hl = HorizontalLayout(nameField, greetButton).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.BASELINE
        }

        root.add(
            hl,
            checkUIButton,
            progress
        )

        nameField.focus()
    }

    private fun checkUI() {
        Notification.show("UI is responsive", 1000, Notification.Position.TOP_END)
    }

    private fun greet() {
        greetButton.isEnabled = false
        nameField.isEnabled = false
        progress.isVisible = true

        launch {
            try {
                val greetings = greetingService.greet(name = nameField.value)
                Notification.show(greetings, 3000, Notification.Position.MIDDLE)
                nameField.clear()
            } finally {
                progress.isVisible = false
                greetButton.isEnabled = true
                nameField.isEnabled = true
            }
        }
    }
}
