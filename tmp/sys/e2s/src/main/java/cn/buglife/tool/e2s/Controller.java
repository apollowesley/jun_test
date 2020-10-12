package cn.buglife.tool.e2s;

import cn.buglife.tool.e2s.enter.ExcelReader;
import cn.buglife.tool.e2s.export.ExportUtil;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    /**
     * 文件路径显示
     */
    @FXML
    private Label excelPath = new Label();

    @FXML
    private AnchorPane panel;

    /**
     * 选择文件按钮
     */
    @FXML
    private Button scanButton = new Button();

    @FXML
    private GridPane gridPane = new GridPane();

    @FXML
    private ScrollBar scrollBar = new ScrollBar();

    /**
     * 打开文件选择对话框选择文件
     *
     * @param event
     */
    @FXML
    private void choose(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择Excel文件");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 2003", "*.xls"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 2007", "*.xlsx"));

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            excelPath.setText("请重新选择文件");
            return;
        }
        LOGGER.info("文件路径：" + file.getPath());
        scanButton.setText("重新选择文件");
        excelPath.setText(file.getPath());
        showSheet(ExcelReader.listSheet(file));
    }

    @FXML
    public void initialize() {
        panel.setMaxHeight(Double.MAX_VALUE);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(20);
        scrollBar.setPrefHeight(500);
        scrollBar.setMinHeight(panel.getHeight());
        scrollBar.setValue(panel.getHeight());
        scrollBar.setMax(0);
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                LOGGER.info("Y坐标值：" + new_val.doubleValue());
                panel.setLayoutY(-new_val.doubleValue());
            }
        });
    }

    private void showSheet(List<HSSFSheet> sheets) {
        if (sheets == null || sheets.size() == 0) {
            return;
        }
        for (int i = 0; i < sheets.size(); i++) {
            final HSSFSheet sheet = sheets.get(i);
            //放入excel文件中的名称
            Label tmp = new Label();
            tmp.setText(sheet.getSheetName());
            gridPane.add(tmp, 0, i + 1);
            //设置sql脚本中对应的表名
            final TextField textField = new TextField();
            textField.setText("table_" + format(sheet.getSheetName()));
            gridPane.add(textField, 1, i + 1);

            //处理属性名称
            GridPane attribute = new GridPane();
            attribute.autosize();
            final List<String> header = ExcelReader.getExcelHeader(sheet);
            final List<TextField> columns = new ArrayList<TextField>();
            if (header != null && header.size() != 0) {
                Label excelHeader = new Label();
                excelHeader.setText("Excel列名");
                excelHeader.setMinWidth(100);
                excelHeader.setStyle("-fx-text-fill:#85ff15");
                attribute.add(excelHeader, 0, 0);
                Label dbHeader = new Label();
                dbHeader.setText("DB属性名");
                dbHeader.setStyle("-fx-text-fill:#85ff15");
                dbHeader.setMinWidth(200);
                attribute.add(dbHeader, 1, 0);

                for (int j = 0; j < header.size(); j++) {
                    Label excelTmp = new Label();
                    excelTmp.setText(header.get(j));
                    excelTmp.setMinHeight(30);
                    attribute.add(excelTmp, 0, j + 1);
                    TextField dbTmp = new TextField();
                    dbTmp.setMinHeight(30);
                    dbTmp.setText("col_" + format(header.get(j)));
                    columns.add(dbTmp);
                    attribute.add(dbTmp, 1, j + 1);
                }
            }
            gridPane.add(attribute, 2, i + 1);
            //添加导出按钮
            Button exportButton = new Button();
            exportButton.setText("导出SQL");
            exportButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle("保存路径");
                    File file = directoryChooser.showDialog(null);
                    if (!file.isDirectory()){
                        return;
                    }
                    ExportUtil.export(file.getAbsolutePath(), textField.getText(), columns, ExcelReader.getData(sheet));
                }
            });
            gridPane.add(exportButton, 3, i + 1);
        }
        scrollBar.setMax(10000);
    }

    private String format(String str) {
        String tmp = PinyinHelper.convertToPinyinString(str, " ", PinyinFormat.WITHOUT_TONE);
        tmp = tmp.replaceAll(" ", "_");
        return tmp;
    }
}
