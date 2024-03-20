package com.kronst.demo

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@PWA(name = "Spring Vaadin Kotlin", shortName = "SVK", iconPath = "icons/icon.png")
@Theme("demo")
@Push
@SpringBootApplication
class VaadinDemoApplication : AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<VaadinDemoApplication>(*args)
}
