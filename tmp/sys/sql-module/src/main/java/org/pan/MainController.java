package org.pan;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.pan.util.Alerts;
import org.pan.util.ExcelExportor;
import org.pan.util.SqlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by xiaopan on 2016-03-03.
 */
public class MainController implements Initializable{
    final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    public final String folder = "sql-module";
    public TabPane tabPane;
    public HBox banner;

    public TextField ip;
    public TextField port;
    public TextField username;
    public TextField password;
    public TextField databaseName;

    private List<String> headerNames;
    private List<String[]> rows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadTabItem(tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ip.setText(SqlReader.ip);
        port.setText(SqlReader.port);
        username.setText(SqlReader.username);
        password.setText(SqlReader.password);
        databaseName.setText(SqlReader.databaseName);

        Platform.runLater(()->{
            AppConfig appConfig = new AppConfig();
            appConfig.load();

            ip.setText(appConfig.get(AppConfig.key_ip));
            port.setText(appConfig.get(AppConfig.key_port));
            username.setText(appConfig.get(AppConfig.key_username));
            password.setText(appConfig.get(AppConfig.key_password));
            databaseName.setText(appConfig.get(AppConfig.key_database));
        });
    }

    public void loadTabItem(TabPane tabPane) throws IOException {
        Path path = Paths.get(folder);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        Files.list(path).forEach(each -> {
            String name = each.toFile().getName();
            Tab tab = new Tab(name);
            tab.setUserData(each);
            tabPane.getTabs().add(tab);
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                selectTab(newValue);
            } catch (Exception e) {
                Alerts.create(Alert.AlertType.ERROR).setTitle("错误").setHeaderText("加载数据失败，请检查当前数据库是否可执行当前查询模块或查询模块定义错误？").show();
            }
        });
    }

    public void clickSave(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls");
        File file = fileChooser.showSaveDialog(null);
        try {
            ExcelExportor.export(file.getAbsolutePath(),headerNames,rows);
            Alerts.create(Alert.AlertType.INFORMATION).setTitle("提示").setHeaderText("导出成功,excel保存在:"+file.getAbsolutePath()).show();
        } catch (IOException e) {
            LOGGER.error("导出数据到excel文件出错",e);
        }
    }

    public void saveConnect() {
        SqlReader.ip = ip.getText();
        SqlReader.port = port.getText();
        SqlReader.username = username.getText();
        SqlReader.password = password.getText();
        SqlReader.databaseName = databaseName.getText();

        try {
            SqlReader.getConnection();
        } catch (Exception e) {
            Alerts.create(Alert.AlertType.ERROR).setTitle("错误").setHeaderText("连接数据库失败，请检查连接参数是否正确？").show();
            return;
        }

        try {
            AppConfig appConfig = new AppConfig();
            appConfig.load();
            appConfig.set(SqlReader.ip,SqlReader.port,SqlReader.username,SqlReader.password,SqlReader.databaseName);
            Alerts.create(Alert.AlertType.INFORMATION).setTitle("提示").setHeaderText("保存成功").show();
        } catch (IOException e) {
            Alerts.create(Alert.AlertType.ERROR).setTitle("错误").setHeaderText("保存失败").show();
            e.printStackTrace();
        }
    }

    public void resetConnect() {
        ip.setText("127.0.0.1");
        port.setText("1433");
        username.setText("sa");
        password.setText("1");
        databaseName.setText("onecardTable");

        saveConnect();
    }

    public void clickRefresh(Event event) {
        Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
        try {
            selectTab(selectedItem);
        } catch (Exception e) {
            Alerts.create(Alert.AlertType.ERROR).setTitle("错误").setHeaderText("加载数据失败，请检查当前数据库是否可执行当前查询模块或查询模块定义错误？").show();
        }
    }

    private void selectTab(Tab tab){
        final Path sqlPath = (Path) tab.getUserData();
        if (sqlPath == null) {
            return;
        }

        try {
            SqlReader.getConnection();
        } catch (Exception e) {
            Alerts.create(Alert.AlertType.ERROR).setTitle("错误").setHeaderText("连接数据库失败，请检查连接参数是否正确？").show();
            return;
        }

        TableView<List<String>> objectTableView = new TableView<>();
        headerNames = SqlReader.getHeaderName(sqlPath);

        for (int i = 0; i < headerNames.size(); i++) {
            TableColumn<List<String>, String> tableColumn = new TableColumn(headerNames.get(i));
            tableColumn.setPrefWidth(100);
            objectTableView.getColumns().add(tableColumn);
            final int index = i;
            tableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(index)));
        }

        Platform.runLater(() -> {
            rows = SqlReader.getRows(sqlPath);
            rows.forEach(row -> objectTableView.getItems().add(Arrays.asList(row)));
        });

        tab.setContent(objectTableView);
        tab.setUserData(null);
    }
}
