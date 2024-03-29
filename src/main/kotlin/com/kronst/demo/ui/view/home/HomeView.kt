package com.kronst.demo.ui.view.home

import com.github.mvysny.karibudsl.v10.h1
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.kronst.demo.ui.MainLayout
import com.kronst.demo.ui.ScopeComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@PageTitle("Home | SVK")
@Route(value = "", layout = MainLayout::class)
class HomeView : ScopeComponent() {

    init {
        ui {
            verticalLayout {
                h1("Home")
            }
        }
    }
}
