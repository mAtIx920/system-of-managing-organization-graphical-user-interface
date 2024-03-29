import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class DatePicker extends JFrame {
    static LocalDateTime date;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;

    JPanel panel = new JPanel(new GridLayout(2,1));

    public DatePicker() {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);

        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);

        JButton button = new JButton("Get Selected Date Time");
        button.addActionListener(e -> {
            Calendar selectedDateTime = this.getSelectedDateTime();
            int year = selectedDateTime.get(Calendar.YEAR);
            int month = selectedDateTime.get(Calendar.MONTH);
            int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);
            int hour = selectedDateTime.get(Calendar.HOUR);
            int minute = selectedDateTime.get(Calendar.MINUTE);
            int second = selectedDateTime.get(Calendar.SECOND);
            date = LocalDateTime.of(year, month+1, day, hour, minute, second);
            NewUserPanel.birth.setText(String.valueOf(date));
        });

        panel.add(dateSpinner);
        panel.add(timeSpinner);
        panel.add(button, BorderLayout.SOUTH);
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public Calendar getSelectedDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((java.util.Date) dateSpinner.getValue());

        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime((java.util.Date) timeSpinner.getValue());

        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));

        return calendar;
    }

}
