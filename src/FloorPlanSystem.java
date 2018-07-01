/**
 * FloorPlanSystem
 * This FloorPlanSystem class generates a JFrame
 * that displays the layout of students and tables
 * for prom.
 *
 * @author Kourosh M, Siyao C
 * @since 2018-03-03
 */

// Import GUI and Graphics components
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Import other necessary classes
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;

public class FloorPlanSystem extends JFrame implements ActionListener {

    // Declare GUI variables
    BorderLayout borderLayout;
    DisplayPanel dispPanel;
    JScrollPane scrollPanel;
    JPanel searchPanel;
    JButton searchButton;
    JButton moveButton;
    JTextField searchBar;
    FlowLayout flowLayout;

    // Declare other variables
    ArrayList<Integer> studentSearched; // List of tables with searched students
    ArrayList<Table> tables;
    TableGraphics[] table; // Graphics of all tables
    int numTables;
    boolean moveTable; // Boolean that represents whether the user is in table moving mode
    int tableSelected; // Index of the table that is selected
    int mouseX;
    int mouseY;
    boolean tableClicked; // Boolean that represents whether a table is selected
    BufferedImage background;
    BufferedImage stage;
    int count;//Counter that will be zero at the start of the program;
    public static final int STAGE_SIZE = 350; // Height of stage graphics

    /**
     * This is the constructor of FloorPlanSystem and
     * will display the JFrame once called.
     * @param tables The list of Tables containing to Students to display.
     */
    FloorPlanSystem(ArrayList<Table> tables) {
        super("Floor Plan System");

        // Set the list of tables
        this.tables = tables;

        // Set default characteristics
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Load background (non-table) images
        try {
            // Try to load the marble and stage images
            background = ImageIO.read(new File("resources/marble-texture.jpg"));
            stage = ImageIO.read(new File("resources/Stage.jpg"));
        } catch (Exception e) {
            // Display error if necessary
            System.out.println("Error loading background images");
        }

        // Initialize ArrayList of tables with searched students as empty
        studentSearched = new ArrayList<Integer>();

        //Initializing counter to represent the start of the program
        count=0;

        // Set the default mode to not moving tables
        moveTable = false;

        // Declare GUI components
        borderLayout = new BorderLayout();
        dispPanel = new DisplayPanel();
        flowLayout = new FlowLayout();

        // Initialize searching GUI components
        searchPanel = new JPanel();
        searchBar = new JTextField("Enter Student ID", 50);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(searchBar, flowLayout);
        searchPanel.add(searchButton, flowLayout);

        // Initialize table moving GUI components and add to searchPanel
        moveButton = new JButton("Move Table OFF");
        moveButton.addActionListener(this);
        searchPanel.add(moveButton, flowLayout);
        searchPanel.setBackground(new Color(200, 200, 200));

        // Set the dimensions and scrolling of the panels, with height adjusted to number of tables
        dispPanel.setPreferredSize(new Dimension(1200, 350 + ((tables.size() / 15) + 1) * (2 * TableGraphics.TABLE_GAP_SIZE + 2 * TableGraphics.TABLE_SIZE))); //Height adjusted to number of tables
        scrollPanel = new JScrollPane(dispPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add panels to the JFrame
        this.add(scrollPanel, borderLayout.CENTER);
        this.add(searchPanel, borderLayout.PAGE_END);

        // Make GUI visible
        this.setVisible(true);
    }

    /**
     * This method checks if the user presses any buttons
     * and performs the appropriate action.
     * @param e The ActionEvent of the user's action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Search if searchButton is pressed
        if (e.getSource() == searchButton) {
            // Initialize an empty ArrayList of tables
            studentSearched = new ArrayList<Integer>();

            // Search all tables for students with the same studentNumber
            for (int i = 0; i < tables.size(); i++) {
                for (int k = 0; k < tables.get(i).getStudentList().size(); k++) {
                    // If the studentNumber is found, add the table to be highlighted
                    if (tables.get(i).getStudentList().get(k).getStudentNumber().equals(searchBar.getText())) {
                        studentSearched.add(i);
                    }
                }
            }

            // Move screen to selected tables
            if (studentSearched.size() != 0) {
                dispPanel.scrollRectToVisible(new Rectangle((int) table[studentSearched.get(0)].getTableX(), (int) table[studentSearched.get(0)].getTableY(), TableGraphics.TABLE_SIZE, TableGraphics.TABLE_SIZE));
            }
        }
        // Change mode to table moving mode
        if (e.getSource() == moveButton) {
            // Change boolean value of mode
            moveTable = !moveTable;

            // Set text
            if (moveTable) {
                moveButton.setText("Move Table ON");
                tableSelected = -1; // Set index of selected table to 0 by default
            } else {
                moveButton.setText("Move Table OFF");
            }
        }
    }

    // INNER CLASS
    /**
     * This DisplayPanel class is the main JPanel that
     * displays the layout of tables.
     */
    class DisplayPanel extends JPanel {

        /**
         * This is the constructor of the DisplayPanel class
         * that initializes the position of tables and
         * prepares for user interaction.
         */
        DisplayPanel() {
            // Find the number of tables and create new array of TableGraphics
            numTables = tables.size();
            table = new TableGraphics[tables.size()];

            // Loop through array and set the initial coordinates of each table
            for (int i = 0; i < tables.size(); i++) {
                // Get the table
                table[i] = new TableGraphics(tables.get(i));

                // Algorithm for placing tables, with rowNum being the row number of two rows
                int rowNum = i / 15;

                // Check if it's the first of the two rows or second of the two rows
                if ((i - rowNum * 15) <= 7) {
                    // Use complex formula to determine x and y of table
                    table[i].setTableX((i - (rowNum * 15)) * TableGraphics.TABLE_SIZE + (i - (rowNum * 15) + 1) * TableGraphics.TABLE_GAP_SIZE);
                    table[i].setTableY(STAGE_SIZE + rowNum * ((2 * TableGraphics.TABLE_GAP_SIZE) + (2 * TableGraphics.TABLE_SIZE)));
                } else if ((i - rowNum * 15) > 7) {
                    // Use complex formula to determine x and y of table
                    table[i].setTableX((int) ((1.5 * TableGraphics.TABLE_GAP_SIZE) + (0.5 * TableGraphics.TABLE_SIZE) + (i - (rowNum * 15) - 8) * (TableGraphics.TABLE_GAP_SIZE + TableGraphics.TABLE_SIZE)));
                    table[i].setTableY(STAGE_SIZE + rowNum * ((2 * TableGraphics.TABLE_SIZE) + (2 * TableGraphics.TABLE_GAP_SIZE)) + TableGraphics.TABLE_GAP_SIZE + TableGraphics.TABLE_SIZE);
                }
            }

            // Initialize the mouseListener
            MyMouseListener myMouseListener = new MyMouseListener();
            this.addMouseListener(myMouseListener);
        }

        /**
         * This paintComponent method draws all the images
         * and automatically refreshes if any changes are made.
         * @param g The Graphics object that draws images.
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // If the table moving mode is selected, perform the table moving
            if (moveTable) {
                // If a table is selected
                if (tableSelected != -1) {
                    // Get location and convert it
                    Point m = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(m, this);
                    mouseX = (int) m.getX();
                    mouseY = (int) m.getY();

                    // Move table to mouse location
                    table[tableSelected].setTableX(mouseX - 34);
                    table[tableSelected].setTableY(mouseY - 34);
                }
            }

            // Display the background of marble and stage
            drawBackground(g);

            // Loop through tables to check and perform various operations
            for (int i = 0; i < tables.size(); i++) {
                // If students are found in the search, highlight the table
                if (searchIntArray(studentSearched, i)) {
                    g.setColor(new Color(0, 0, 255));
                    highlightTable(g, table[i]);
                }

                // Draw the table
                table[i].drawTable(g);

                // Create String of table number
                String num = "";
                num += (i + 1);

                // Set the colour of the table
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Arial", Font.PLAIN, 20));

                // Draw the String based on the number of digits
                if (Integer.parseInt(num) > 9) {
                    g.drawString(num, (int) table[i].getTableX() + 22, (int) table[i].getTableY() + 40);
                } else {
                    g.drawString(num, (int) table[i].getTableX() + 28, (int) table[i].getTableY() + 40);
                }

                // If studentList is selected, draw it
                if (table[i].isStudentList()) {
                    table[i].drawStudentList();
                    table[i].setStudentList(false);
                }
            }

            // Loop this method
            repaint();
        }

        /**
         * This searchIntArray method checks if a specific
         * integer exists within an array.
         * @param arrList The Integer array to search.
         * @param integer The Integer value to search for.
         * @return True if the Integer exists, false otherwise.
         */
        public boolean searchIntArray(ArrayList<Integer> arrList, int integer) {
            // Loop through array and return true if value is found
            for (int i = 0; i < arrList.size(); i++) {
                if (arrList.get(i).equals(integer)) {
                    return true;
                }
            }
            // Return false otherwise
            return false;
        }

        /**
         * This highlightTable method draws a highlighted box
         * over a table.
         * @param g The Graphics object used to draw the box.
         * @param table The TableGraphics of the table to highlight.
         */
        public void highlightTable(Graphics g, TableGraphics table) {
            // Draw outer blue rectangle
            g.fillRect((int) table.getTableX() - 5, (int) table.getTableY() - 5, TableGraphics.TABLE_SIZE + 10, TableGraphics.TABLE_SIZE + 10);

            // Set white colour and draw inner white rectangle
            g.setColor(new Color(255, 255, 255));
            g.fillRect((int) table.getTableX() - 1, (int) table.getTableY() - 1, TableGraphics.TABLE_SIZE + 2, TableGraphics.TABLE_SIZE + 2);
        }

        /**
         * This drawBackground method draws the background of
         * marble and the stage.
         * @param g The Graphics object used to draw the background.
         */
        public void drawBackground(Graphics g) {
            // Determine the height of the screen needed
            int sizeOfScreen = 350 + ((tables.size() / 15) + 1) * (2 * TableGraphics.TABLE_GAP_SIZE + 2 * TableGraphics.TABLE_SIZE);
            // Draw marble background repeatedly depending on the height of the screen
            for (int i = 0; i < sizeOfScreen; i += 350) {
                g.drawImage(background, 0, i, 1200, 350, null);
            }

            // Draw the stage image
            g.drawImage(stage, 0, 0, 1200, 350, null);
        }

        /**
         * This MyMouseListener inner class is used to
         * track any mouse actions of the user and
         * respond accordingly.
         */
        private class MyMouseListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            /**
             * This mousePressed method enables moving tables
             * if the user presses down on a table.
             * @param e The MouseEvent to respond to.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                // Set default value of whether table is selected to false
                tableClicked = false;

                // Set the index of the table
                int k = tableSelected;

                // If a table is currently selected, change tableClicked to true and deselect table
                if (tableSelected >= 0) {
                    tableSelected = -1;
                    tableClicked = true;
                }

                //Prevents a bug where studentList will not show up the first time it is clicked
                if (count == 0){
                    tableClicked = false;
                    count++;
                }

                // Loop through tables to find selected table
                for (int i = 0; i < tables.size(); i++) {
                    // If the click is on the table, select it
                    if (k != i && (((e.getX() < (table[i].getTableX() + TableGraphics.TABLE_SIZE * 1.5)) && (e.getX() > table[i].getTableX() - 0.5 * TableGraphics.TABLE_SIZE) && (e.getY() < (table[i].getTableY() + 1.5 * TableGraphics.TABLE_SIZE)) && (e.getY() > table[i].getTableY() - 0.5 * TableGraphics.TABLE_SIZE)))) {
                        tableSelected = k;
                    }

                    // Find out which table the user clicked
                    if ((e.getX() < (table[i].getTableX() + TableGraphics.TABLE_SIZE
                    )) && (e.getX() > table[i].getTableX()) && (e.getY() < (table[i].getTableY() + TableGraphics.TABLE_SIZE)) && (e.getY() > table[i].getTableY())) {
                        // If no table is currently selected, select the table; otherwise, display the studentList
                        if (moveTable && !tableClicked) {
                            tableSelected = i;
                        } else {
                            // If no table is clicked, open or close studentList
                            if (!tableClicked) {
                                if (table[i].isStudentList()) {
                                    table[i].setStudentList(false);
                                } else {
                                    table[i].setStudentList(true);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
    }
}