package com.kronst.demo.ui

import com.kronst.demo.ui.view.greeting.GreetingView
import com.kronst.demo.ui.view.home.HomeView
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.sidenav.SideNav
import com.vaadin.flow.component.sidenav.SideNavItem
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value

@SpringComponent
@UIScope
class MainLayout : AppLayout() {

    @Value("\${spring.application.name}")
    private lateinit var appName: String

    @PostConstruct
    fun init() {
        primarySection = Section.NAVBAR
        isDrawerOpened = true

        val header = buildHeader()
        addToNavbar(true, header)

        val sideNav = buildSideNav()
        addToDrawer(sideNav)
    }

    private fun buildSideNav(): SideNav {
        val nav = SideNav()

        nav.addItem(
            SideNavItem("Home", HomeView::class.java, VaadinIcon.HOME.create()),
            SideNavItem("Greeting", GreetingView::class.java, VaadinIcon.COMMENT.create())
        )

        return nav
    }

    private fun buildHeader(): Component {
        val logo = H2(appName)

        val header = HorizontalLayout(DrawerToggle(), logo).apply {
            defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
            expand(logo)
            setWidthFull()
        }

        return header
    }
}
