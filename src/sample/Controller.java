package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import arduino.Arduino;
//import jssc.SerialPortList;
import java.util.Scanner;


public class Controller {


    private String port;
    Arduino arduino;
    private boolean status = false;

    @FXML
    private Label label;
    @FXML
    private ComboBox comboBox;
    @FXML
    private Slider slider;
    @FXML
    private Button BtClose;
    @FXML
    private Button btOpen;

        @FXML
    private void OpenPortAction(ActionEvent event) {
        System.out.println("OpenPortAction");
            if(port != null) {
                arduino = new Arduino(port, 9600);
                boolean connected = arduino.openConnection();
                System.out.println("Соединение установлено: " + connected);
                if(connected) {
                    btOpen.setDisable(true);
                    BtClose.setDisable(false);
                    status = true;
                }
            }
    }

    @FXML
    private void ClosePortAction(ActionEvent event) {
        System.out.println("ClosePortAction");
        arduino.closeConnection();
        btOpen.setDisable(false);
        BtClose.setDisable(true);
        status = false;
    }

    @FXML
    private void ExitAction(ActionEvent event) {
        System.out.println("ExitAction");
        System.exit(0);

    }

    @FXML
    private void SliderChange() {
        System.out.println("Slider");
    }

    @FXML
    private void ChangePort(ActionEvent event) {
        System.out.println("ChangePort");
        port = comboBox.getValue().toString();
        System.out.println(port);
    }

    @FXML
    public void initialize() {
        comboBox.getItems().addAll(
      "COM1",
                "COM2",
                "COM3",
                "COM4",
                "COM5",
                "COM6",
                "COM7",
                "COM8",
                "COM9",
                "COM10",
                "COM11",
                "COM12",
                "COM13",
                "COM14",
                "COM15"
        );

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {

                float valueViwe = (new_val.floatValue() + 870)/10;
                String vauleSet;
//                float valueArduino = (new_val.floatValue() + 870)*10;
                int vArduino = new_val.intValue();
                if(vArduino < 10){
                    vauleSet = "00" + vArduino;
                }else {
                    if(vArduino >= 10 && vArduino < 100) {
                        vauleSet = "0" + vArduino;
                    }
                    else{
                        vauleSet = "" + vArduino;
                    }
                }

                label.setText(String.format("%.1f", valueViwe)+"0");
                System.out.println("Value Viwe = " + String.format("%.1f", valueViwe)+"0; Value set to Arduino = " + vauleSet);
                if(status) {
                    arduino.serialWrite("00" + vauleSet + "0");
                }
            }
        });
    }

    public Controller() {

    }
}
