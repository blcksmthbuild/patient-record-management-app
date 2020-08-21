package com.starterapp.fxgarage.test.ui;

import com.starterapp.fxgarage.test.ui.views.list.PatientView;
import com.starterapp.fxgarage.test.ui.views.list.VisitsView;
import com.starterapp.fxgarage.test.ui.views.prescription.MedicinePrescriptionView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        creatDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("PRMD");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout","Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);

    }

    private void creatDrawer() {
        RouterLink patientListLink = new RouterLink("Patient List", PatientView.class);
        patientListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink medicinePrescriptionLink = new RouterLink("Medicine Prescription", MedicinePrescriptionView.class);
        patientListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink visitsView = new RouterLink("Visits for Patients", VisitsView.class);
        patientListLink.setHighlightCondition(HighlightConditions.sameLocation());



        addToDrawer(new VerticalLayout(patientListLink, medicinePrescriptionLink, visitsView));



    }
}
