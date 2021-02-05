
import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Main {

    public static boolean isProcessing = false;
    public static Index index;
    public static String status = "";
    public static void main(String[] args) {
        JFrame frame = new JFrame("ExcelURLKiller");
        index = new Index();
        setInfo("");
        frame.setContentPane(index.getIndexPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        index.getChooseFileInputButton().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int option = fileChooser.showOpenDialog(index.getIndexPanel());
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                index.setInputPathString(file.getAbsolutePath());
                if (StringUtils.isNotBlank(index.getInputPathString())) {
                    index.getInputPath().setText(index.getInputPathString());
                }
            }
        });
        index.getChooseFileOutputButton().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(index.getIndexPanel());
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                index.setOutputPathString(file.getAbsolutePath());
                if (StringUtils.isNotBlank(index.getOutputPathString())) {
                    index.getOutputPath().setText(index.getOutputPathString() + File.separator + "out.xlsx");
                }
            }
        });
        index.getGoButton().addActionListener(e -> {
            new Thread(() -> {
                try {
                    status = "运行中..";
                    setInfo(status);
                    index.getGoButton().setEnabled(false);
                    String inputPath = index.getInputPath().getText();
                    String outputPath = index.getOutputPath().getText();
                    if (StringUtils.isBlank(inputPath) || StringUtils.isBlank(outputPath)) {
                        setInfo("请选择文件");
                    }
                    List<Map<Integer, String>> objects = EasyExcel.read(inputPath).headRowNumber(2).doReadAllSync();
                    List<List<Object>> objects2 = new ArrayList<>(objects.size());
                    objects.forEach(list -> {
                        List<Object> map = new ArrayList<>();
                        list.keySet().forEach(key -> {
                            String value = list.get(key);
                            Object v = list.get(key);
                            if (value != null && value.toLowerCase(Locale.ROOT).startsWith("http")) {
                                try {
                                    v = new URL(value);
                                } catch (MalformedURLException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            map.add(key, v);
                        });
                        objects2.add(map);
                    });
                    EasyExcel.write(outputPath).sheet().doWrite(objects2);
                    status = "成功";
                    setInfo(status);
                } catch (Exception e2) {
                    status = "错误";
                    setInfo(status);
                } finally {
                    index.getGoButton().setEnabled(true);
                }

            }).start();
        });
    }

    private static void setInfo(String s) {
        index.getInfo().setText(s);
    }
}
