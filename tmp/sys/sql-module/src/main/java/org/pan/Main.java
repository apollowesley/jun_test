package org.pan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("开始启动程序");
        Parent parent = FXMLLoader.load(Main.class.getResource("index.fxml"));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("excel_128.png")));
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("一卡通数据导出工具");
        primaryStage.show();
        LOGGER.info("启动程序成功");
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
