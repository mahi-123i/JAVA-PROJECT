import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrainReservationSystem extends JFrame implements ActionListener {
    private int[][] seatStatus;
    private JButton[][] seats;
    private JComboBox<String> rowsComboBox, colsComboBox;
    private JLabel statusLabel;

    public TrainReservationSystem() {
        super("Train Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize seat status array
        seatStatus = new int[12][6];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                seatStatus[i][j] = 0;
            }
        }

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create seat panel
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(12, 6));
        seats = new JButton[12][6];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                JButton seat = new JButton((i+1) + "-" + (j+1));
                seat.addActionListener(this);
                seatPanel.add(seat);
                seats[i][j] = seat;
            }
        }
        mainPanel.add(seatPanel, BorderLayout.CENTER);

        // Create control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JLabel rowLabel = new JLabel("Row:");
        controlPanel.add(rowLabel);

        String[] rows = new String[12];
        for (int i = 0; i < 12; i++) {
            rows[i] = String.valueOf(i+1);
        }
        rowsComboBox = new JComboBox<>(rows);
        controlPanel.add(rowsComboBox);

        JLabel colLabel = new JLabel("Column:");
        controlPanel.add(colLabel);

        String[] cols = new String[6];
        for (int i = 0; i < 6; i++) {
            cols[i] = String.valueOf(i+1);
        }
        colsComboBox = new JComboBox<>(cols);
        controlPanel.add(colsComboBox);

        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(this);
        controlPanel.add(bookButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        controlPanel.add(cancelButton);

        statusLabel = new JLabel("Select a seat.");
        controlPanel.add(statusLabel);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Book")) {
            int row = rowsComboBox.getSelectedIndex();
            int col = colsComboBox.getSelectedIndex();
            if (seatStatus[row][col] == 0) {
                seatStatus[row][col] = 1;
                seats[row][col].setBackground(Color.GREEN);
                statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " booked.");
            } else {
                statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " is already booked.");
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            int row = rowsComboBox.getSelectedIndex();
            int col = colsComboBox.getSelectedIndex();
            if (seatStatus[row][col] == 1) {
                seatStatus[row][col] = 0;
                            seats[row][col].setBackground(null);
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " cancelled.");
        } else {
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " is not booked.");
        }
    } else {
        // Seat button pressed
        String[] seatString = e.getActionCommand().split("-");
        int row = Integer.parseInt(seatString[0]) - 1;
        int col = Integer.parseInt(seatString[1]) - 1;
        if (seatStatus[row][col] == 0) {
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " is available.");
        } else {
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " is already booked.");
        }
    }
}

public static void main(String[] args) {
    new TrainReservationSystem();
}
}
