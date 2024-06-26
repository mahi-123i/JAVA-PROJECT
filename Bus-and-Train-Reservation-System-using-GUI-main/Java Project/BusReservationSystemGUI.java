import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BusReservationSystemGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel, seatsPanel;
    private JButton[][] seats;
    private JLabel titleLabel, statusLabel;
    private JComboBox<Integer> rowsComboBox, colsComboBox;
    private JButton bookButton, cancelButton;
    private int[][] seatStatus = new int[10][4];

    public BusReservationSystemGUI() {
        // Set up the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Set up the title label
        titleLabel = new JLabel("Bus Reservation System");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        mainPanel.add(titleLabel);

        // Set up the seats panel
        seatsPanel = new JPanel(new GridLayout(10, 4));
        seats = new JButton[10][4];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                seats[i][j] = new JButton((i + 1) + "-" + (j + 1));
                seats[i][j].addActionListener(this);
                seatsPanel.add(seats[i][j]);
            }
        }
        mainPanel.add(seatsPanel);

        // Set up the status label
        statusLabel = new JLabel("Please select a seat");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);

        // Set up the rows combo box
        rowsComboBox = new JComboBox<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        rowsComboBox.setMaximumSize(new Dimension(100, 30));
        rowsComboBox.addActionListener(this);
        mainPanel.add(rowsComboBox);

        // Set up the columns combo box
        colsComboBox = new JComboBox<Integer>(new Integer[] {1, 2, 3, 4});
        colsComboBox.setMaximumSize(new Dimension(100, 30));
        colsComboBox.addActionListener(this);
        mainPanel.add(colsComboBox);

        // Set up the book button
        bookButton = new JButton("Book");
        bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookButton.addActionListener(this);
        mainPanel.add(bookButton);

        // Set up the cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(this);
        mainPanel.add(cancelButton);

        // Set up the frame
        setTitle("Bus Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new BusReservationSystemGUI();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            int row = rowsComboBox.getSelectedIndex();
            int col = colsComboBox.getSelectedIndex();
            if (seatStatus[row][col] == 0) {
                seatStatus[row][col] = 1;
                seats[row][col].setBackground(Color.RED);
                statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " booked.");
            } else {
                statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " is already booked.");
            }
        } else if (e.getSource() == cancelButton){
                        int row = rowsComboBox.getSelectedIndex();
        int col = colsComboBox.getSelectedIndex();
        if (seatStatus[row][col] == 1) {
            seatStatus[row][col] = 0;
            seats[row][col].setBackground(null);
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " cancelled.");
        } else {
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " is not booked.");
        }
    } else if (e.getSource() instanceof JButton) {
        JButton seatButton = (JButton) e.getSource();
        String seatNumber = seatButton.getText();
        int row = Integer.parseInt(seatNumber.split("-")[0]) - 1;
        int col = Integer.parseInt(seatNumber.split("-")[1]) - 1;
        if (seatStatus[row][col] == 0) {
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " selected.");
            rowsComboBox.setSelectedIndex(row);
            colsComboBox.setSelectedIndex(col);
        } else {
            statusLabel.setText("Seat " + (row + 1) + "-" + (col + 1) + " is already booked.");
        }
    }
}
}

