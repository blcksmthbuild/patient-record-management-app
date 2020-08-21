package com.starterapp.fxgarage.test.ui.views.prescription;

import com.starterapp.fxgarage.test.backend.entity.MedicinePrescription;
import com.starterapp.fxgarage.test.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "prescription",layout = MainLayout.class)
@PageTitle("Medicine Prescription || PRMD")
public class MedicinePrescriptionView extends VerticalLayout {


    private Grid<MedicinePrescription> medicinePrescriptionGrid = new Grid<>(MedicinePrescription.class);



    public MedicinePrescriptionView() {


        add(
                buildForm(),
                medicinePrescriptionGrid);
        configureMedicinePrescriptionGrid();
    }

    private Component buildForm() {

        ComboBox<MedicinePrescription.Medicine> medicineSelect = new ComboBox<>("Medicine", Collections.emptyList());
        TextField quantityField = new TextField("Quantity");
        TextField nameField = new TextField("Name");
        Button submitButton = new Button("Print Prescription");
        DatePicker dateField = new DatePicker("Local date");
        dateField.setValue(LocalDate.now());

        Div errorsLayout = new Div();

        submitButton.setThemeName("primary");
        submitButton.addClickShortcut(Key.ENTER);

        HorizontalLayout formLayout = new HorizontalLayout(nameField, medicineSelect, quantityField, submitButton);
        Div wrapperLayout = new Div(formLayout, errorsLayout, dateField);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        wrapperLayout.setWidth("100%");


        Binder<MedicinePrescription> binder = new Binder<>(MedicinePrescription.class);

        binder.forField(quantityField)
                .asRequired()
                .withConverter(new StringToIntegerConverter("Quantity must be a number"))
                .withValidator(new IntegerRangeValidator("Quantity must be at least 1", 1, Integer.MAX_VALUE))
                .bind(MedicinePrescription::getQuantity, MedicinePrescription::setQuantity);
        medicineSelect.setItems(MedicinePrescription.Medicine.values());
        binder.forField(medicineSelect)
                .asRequired("Please choose a medicine")
                .bind(MedicinePrescription::getMedicine, MedicinePrescription::setMedicine);
        binder.forField(nameField)
                .asRequired("Name is required")
                .bind(MedicinePrescription::getName, MedicinePrescription::setName);






        binder.readBean(new MedicinePrescription());

        submitButton.addClickListener(click -> {
            try {
                errorsLayout.setText("");
                MedicinePrescription savedOrder = new MedicinePrescription();
                binder.writeBean(savedOrder);
                addOrder(savedOrder);
                binder.readBean(new MedicinePrescription());



            } catch (ValidationException e) {
                errorsLayout.add(new Html(e.getValidationErrors().stream()
                        .map(res -> "<p>" + res.getErrorMessage() + "</p>")
                        .collect(Collectors.joining("\n"))));
            }
        });






        return wrapperLayout;
    }

    private List<MedicinePrescription> medicinePrescriptions = new LinkedList<>();


    private void addOrder(MedicinePrescription order) {
        medicinePrescriptions.add(order);
        medicinePrescriptionGrid.setItems(medicinePrescriptions);


    }


    private void configureMedicinePrescriptionGrid() {

        medicinePrescriptionGrid.setColumns("name", "medicine", "quantity", "date");







        }



}




