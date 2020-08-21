package com.starterapp.fxgarage.test.ui.views.list;

import com.starterapp.fxgarage.test.backend.entity.Doctor;
import com.starterapp.fxgarage.test.backend.entity.Patient;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


import java.util.List;


public class PatientForm extends FormLayout {

    private Patient contact;

    public void setPatient(Patient contact) {
        this.contact = contact;
        binder.readBean(contact);
    }

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    ComboBox<Patient.ChronicDisease> chronicDisease = new ComboBox<>("Chronic Disease");
    ComboBox<Patient.SimpleDisease> simpleDisease = new ComboBox<>("Simple Disease");
    ComboBox<Doctor> doctor = new ComboBox<>("Doctor");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Patient> binder = new Binder<>(Patient.class);

    public PatientForm(List<Doctor> doctors) {
        addClassName("patient-form");
        binder.bindInstanceFields(this);

        doctor.setItems(doctors);
        doctor.setItemLabelGenerator(Doctor::getName);
        chronicDisease.setItems(Patient.ChronicDisease.values());
        simpleDisease.setItems(Patient.SimpleDisease.values());

        add(
                firstName,
                lastName,
                chronicDisease,
                simpleDisease,
                doctor,
                createButtonsLayout());
    }


    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, contact)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled((binder.isValid())));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(contact);
            fireEvent(new SaveEvent(this, contact));
        } catch (ValidationException e){
            e.printStackTrace();

        }
    }

    public static abstract class PatientFormEvent extends ComponentEvent<PatientForm> {
        private Patient contact;

        protected PatientFormEvent(PatientForm source, Patient contact) {
            super(source, false);
            this.contact = contact;
        }

        public Patient getPatient() {
            return contact;
        }
    }

    public static class SaveEvent extends PatientFormEvent {
        SaveEvent(PatientForm source, Patient contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends PatientFormEvent {
        DeleteEvent(PatientForm source, Patient contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends PatientFormEvent {
        CloseEvent(PatientForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}




