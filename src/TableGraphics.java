/**
 * TableGraphics
 * This TableGraphics class is the associated graphics class
 * for the Table object that holds the images, coordinates,
 * and other information for drawing the Table.
 *
 * @author Kourosh M, Siyao C
 * @since 2018-03-03
 */

// Import required image classes
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TableGraphics {

    // Variable declarations
    private Table table;
    private BufferedImage[] tableImage;
    private double tableX;
    private double tableY;
    private boolean studentList; // Boolean that indicates whether the user wants to view the studentList contained in table
    StudentListFrame listFrame;
    static final int TABLE_SIZE = 69;
    static final int TABLE_GAP_SIZE = 72;

    /**
     * This constructor initializes the variables and graphics
     * of the TableGraphics object.
     * @param table The Table object this TableGraphics class associated with
     */
    TableGraphics(Table table) {
        this.table = table;

        // Set the studentList to false by default
        studentList = false;

        // Load table images into tableImage array
        try {
            tableImage = new BufferedImage[12];
            tableImage[0] = ImageIO.read(new File("resources/table-1.png"));
            tableImage[1] = ImageIO.read(new File("resources/table-2.png"));
            tableImage[2] = ImageIO.read(new File("resources/table-3.png"));
            tableImage[3] = ImageIO.read(new File("resources/table-4.png"));
            tableImage[4] = ImageIO.read(new File("resources/table-5.png"));
            tableImage[5] = ImageIO.read(new File("resources/table-6.png"));
            tableImage[6] = ImageIO.read(new File("resources/table-7.png"));
            tableImage[7] = ImageIO.read(new File("resources/table-8.png"));
            tableImage[8] = ImageIO.read(new File("resources/table-9.png"));
            tableImage[9] = ImageIO.read(new File("resources/table-10.png"));
            tableImage[10] = ImageIO.read(new File("resources/table-11.png"));
            tableImage[11] = ImageIO.read(new File("resources/table-12.png"));
        } catch (IOException e) {
            // Display exception if there is an error reading images
            System.out.println("Error loading table images");
        }
    }

    /**
     * This method draws the Table object.
     * @param g The Graphics object used to draw the Tables
     */
    public void drawTable(Graphics g) {
        // Loop through the studentList
        for (int i = 0; i < table.getStudentList().size(); i++) {
            // Draw corresponding table graphics image based on number of students
            if (table.getStudentList().size() - 1 < 11) {
                g.drawImage(tableImage[table.getStudentList().size() - 1], (int) tableX, (int) tableY, TABLE_SIZE, TABLE_SIZE, null);
            } else {
                g.drawImage(tableImage[11], (int) tableX, (int) tableY, TABLE_SIZE, TABLE_SIZE, null);
            }
        }
    }

    /**
     * This method opens the studentList frame, where a
     * list of Students can be seen.
     */
    public void drawStudentList() {
        // Create new StudentListFrame object
        listFrame = new StudentListFrame(table);
    }

    /**
     * This method returns the Table object associated
     * with the TableGraphics object.
     * @return The Table object
     */
    public Table getTable() {
        return table;
    }

    /**
     * This method sets the Table object associated with
     * this TableGraphics object.
     * @param table The Table object to set
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * This method returns the image of the
     * TableGraphics object.
     * @return The image of the table
     */
    public BufferedImage[] getTableImage() {
        return tableImage;
    }

    /**
     * This method sets the image of the tableImage.
     * @param tableImage The image of the table to set
     */
    public void setTableImage(BufferedImage[] tableImage) {
        this.tableImage = tableImage;
    }

    /**
     * This method returns the x-coordinate of the table.
     * @return The x-coordinate of the table
     */
    public double getTableX() {
        return tableX;
    }

    /**
     * This method sets the x-coordinate of the table.
     * @param tableX The new x-coordinate of the table to set
     */
    public void setTableX(double tableX) {
        this.tableX = tableX;
    }

    /**
     * This method returns the y-coordinate of the table.
     * @return The y-coordinate of the table
     */
    public double getTableY() {
        return tableY;
    }

    /**
     * This method sets the y-coordinate of the table.
     * @param tableY The new y-coordinate of the table to set
     */
    public void setTableY(double tableY) {
        this.tableY = tableY;
    }

    /**
     * This method returns a boolean that describes whether to display studentList.
     * @return The boolean describing whether to display studentList
     */
    public boolean isStudentList() {
        return studentList;
    }

    /**
     * This method sets the boolean value that describes whether
     * to display studentList.
     * @param studentList The boolean describing whether to display studentList to set
     */
    public void setStudentList(boolean studentList) {
        this.studentList = studentList;
    }
}