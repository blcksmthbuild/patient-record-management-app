package com.starterapp.fxgarage.test.ui.views.list;


import com.starterapp.fxgarage.test.backend.entity.Doctor;
import com.starterapp.fxgarage.test.backend.entity.Patient;
import com.starterapp.fxgarage.test.backend.service.DoctorService;
import com.starterapp.fxgarage.test.backend.service.PatientService;
import com.starterapp.fxgarage.test.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;



@Route(value = "",layout = MainLayout.class)
@PageTitle("Patients || PRMD")


public class PatientView extends VerticalLayout {


    private final PatientForm form;
    Grid<Patient> grid = new Grid<>(Patient.class);
    TextField filterText = new TextField();

    private PatientService patientService;

    public PatientView(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        form = new PatientForm(doctorService.findAll());
        form.addListener(PatientForm.SaveEvent.class, this::savePatient);
        form.addListener(PatientForm.DeleteEvent.class, this::deletePatient);
        form.addListener(PatientForm.CloseEvent.class, evt -> closeEditor());



        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();

    }

    private void deletePatient(PatientForm.DeleteEvent evt) {
        patientService.delete(evt.getPatient());
        updateList();
        closeEditor();
    }

    private void savePatient(PatientForm.SaveEvent evt) {
        patientService.save(evt.getPatient());
        updateList();
        closeEditor();
    }


    private void configureGrid() {
        grid.addClassName("patient-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("doctor");
        grid.setColumns("firstName", "lastName", "simpleDisease", "chronicDisease");
        grid.addColumn(contact -> {
            Doctor doctor = contact.getDoctor();
            return doctor == null ? "-" : doctor.getName();

        }).setHeader("Patient's Doctor");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editPatient(evt.getValue()) );


    }

    private void editPatient(Patient contact) {
        if (contact == null) {
            closeEditor();
    } else {
            form.setPatient(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPatient(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(event -> updateList());

        Button addPatientButton = new Button("Add new patient");
        addPatientButton.addClickListener(click -> addPatient());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPatientButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addPatient() {
        grid.asSingleSelect().clear();
        editPatient(new Patient());
    }

    private void updateList() {
        grid.setItems(patientService.findAll(filterText.getValue()));
    }
}