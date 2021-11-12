import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Database_View {

    private JTable table2;
    public JPanel Database_View_Panel;
    private JButton showdataButton;
    private JButton homeButton;
    private Database db = new Database();

    public Database_View() {
        table2.setModel(db.getMetadataFromDatabase());

        showdataButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                    table2.setModel(db.getMetadataFromDatabase());
                }
        });
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Main.frame_Database_View.setVisible(false);
                Main.frame_Intro.setVisible(true);
            }
        });
    }
}
