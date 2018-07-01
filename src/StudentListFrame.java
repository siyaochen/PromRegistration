/**
 * StudentListFrame
 * This StudentListFrame class represents the Frame
 * created listing the students in that table.
 *
 * @author Kourosh M, Siyao C
 * @since 2018-03-03
 */

// Import GUI and Graphics components
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class StudentListFrame extends JFrame implements ActionListener {

    // Declare variables for GUI
    JPanel studentPanel;
    JPanel namePanel;
    JPanel infoPanel;
    JLabel studentInfo;
    JLabel studentID;
    JLabel diet;
    JLabel seatNumber;
    JList<String> studentJList;
    BorderLayout borderLayout;
    GridLayout gridLayoutName;
    GridLayout gridLayoutInfo;
    FlowLayout flowLayout;
    JButton studentButton;
    JScrollPane scrollablePanel;

    Table table; // The table associated with the StudentListFrame

    /**
     * This is the constructor for StudentListFrame
     * that will display the list of students and
     * their information.
     * @param table The Table associated with the StudentListFrame
     */
    StudentListFrame(Table table) {
        super("Student Information");

        // Set table
        this.table = table;

        // Set up dimensions and characteristics of the frame
        this.setSize(400, 200);
        this.setResizable(false);
        setLocationRelativeTo(null);

        // Set layout
        borderLayout = new BorderLayout();
        gridLayoutName = new GridLayout(1, 2);
        gridLayoutInfo = new GridLayout(10, 1);
        flowLayout = new FlowLayout();

        // Create panels
        namePanel = new JPanel(gridLayoutName);
        studentPanel = new JPanel(borderLayout);

        // Create student list
        studentJList = new JList<String>(generateStudents());

        // Create scrollable panels and add them
        scrollablePanel = new JScrollPane(studentJList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollablePanel.setPreferredSize(new Dimension(500, 5000));
        namePanel.add(scrollablePanel);
        studentPanel.add(namePanel, borderLayout.CENTER);

        // Create panel with student information and add it
        infoPanel = new JPanel(gridLayoutInfo);
        studentInfo = new JLabel("      STUDENT INFORMATION");
        studentID = new JLabel("Student ID: ");
        diet = new JLabel("Diet: ");
        seatNumber = new JLabel("Seat Number: ");
        infoPanel.add(studentInfo);
        infoPanel.add(studentID);
        infoPanel.add(diet);
        infoPanel.add(seatNumber);
        namePanel.add(infoPanel);

        // Create a button that displays the information
        studentButton = new JButton("Show Information");
        studentButton.setVisible(true);
        studentButton.addActionListener(this);
        studentPanel.add(studentButton, borderLayout.PAGE_END);

        // Add the final student panel to the frame and make visible
        this.add(studentPanel);
        this.setVisible(true);
    }

    /**
     * This method sets the information of the selected
     * student when the button is pressed.
     * @param e The ActionEvent to respond to
     */
    public void actionPerformed(ActionEvent e) {
        // If a student is selected, set student info
        if (studentJList.getSelectedIndex() != -1) {
            // Set the ID and diet of the student
            studentID.setText("Student ID: " + table.getStudentList().get(studentJList.getSelectedIndex()).getStudentNumber());
            diet.setText("Diet: " + table.getStudentList().get(studentJList.getSelectedIndex()).getDietRequirements());

            // Set the seat number of the student
            String seatNum = "";
            seatNum += studentJList.getSelectedIndex() + 1;
            seatNumber.setText("Seat Number: " + seatNum);
        }
    }

    /**
     * This method returns the list of students
     * seated on the table.
     * @return The String array of student names
     */
    public String[] generateStudents() {
        // Create new String array
        String listOfStudents[] = new String[table.getStudentList().size()];

        // Fill array with students and return it
        for (int i = 0; i < listOfStudents.length; i++) {
            listOfStudents[i] = table.getStudentList().get(i).getName();
        }
        return listOfStudents;
    }

}