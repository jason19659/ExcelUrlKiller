
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Index {
    private JButton chooseFileInputButton;
    private JButton chooseFileOutputButton;
    private JButton goButton;
    private JPanel indexPanel;
    private JLabel inputPath;
    private JLabel outputPath;
    private JLabel info;
    private String inputPathString;
    private String outputPathString;


    public JButton getChooseFileInputButton() {
        return chooseFileInputButton;
    }


    public JButton getChooseFileOutputButton() {
        return chooseFileOutputButton;
    }


    public JButton getGoButton() {
        return goButton;
    }



    public JLabel getInputPath() {
        return inputPath;
    }


    public JLabel getOutputPath() {
        return outputPath;
    }


    public String getInputPathString() {
        return inputPathString;
    }

    public void setInputPathString(String inputPathString) {
        this.inputPathString = inputPathString;
    }

    public String getOutputPathString() {
        return outputPathString;
    }

    public void setOutputPathString(String outputPathString) {
        this.outputPathString = outputPathString;
    }

    public JPanel getIndexPanel() {
        return indexPanel;
    }

    public JLabel getInfo() {
        return info;
    }
}
