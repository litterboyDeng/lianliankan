import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressUI extends BasicProgressBarUI {

    private JProgressBar jProgressBar;
    private int progressvalue;
    private Color forecolor;

    ProgressUI(JProgressBar jProgressBar,Color forecolor) {
        this.jProgressBar = jProgressBar;
        this.forecolor=forecolor;
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {

        this.jProgressBar.setBackground(new Color(255, 255, 255));
        this.jProgressBar.setForeground(forecolor);

        super.paintDeterminate(g, c);
    }

}
