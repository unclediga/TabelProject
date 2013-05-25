package view.editor;

import com.michaelbaranov.microba.calendar.DatePicker;
import com.michaelbaranov.microba.calendar.DatePickerCellEditor;

/**
 *  Wrapper on
 *  {@link com.michaelbaranov.microba.calendar.DatePickerCellEditor}.
 *  Add default constructor
 *  Using in all date-columns in this project
 */
public class DateColumnEditor extends DatePickerCellEditor {
    /**
     * Constructor.
     * <p/>
     * Note: you probably will want to set the property of the
     * {@link com.michaelbaranov.microba.calendar.DatePicker} {@value com.michaelbaranov.microba.calendar.DatePicker#PROPERTY_NAME_DROPDOWN_FOCUSABLE}
     * to <code>false</code> before using it to construct
     *
     *
     */
//    public DateColumnEditor(DatePicker datePicker) {
//        super(datePicker);
//    }

    public DateColumnEditor(){
        super(new DatePicker());

    }
}
