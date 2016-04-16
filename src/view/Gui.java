package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application{
	
	
     public static void main(String[] args) {	
    	Control ctr=new Control();
    	ctr.set_buttons();
		launch();	
    }
       
       
	public Button read_but=new Button("Read file");
	Button def_but=new Button("Default");
	public Button calc1_but=new Button("Calculate 1x");
	Button calc10_but=new Button("Calculate 10x");
	Button calc100_but=new Button("Calculate 100x");
	Button calcx_but=new Button("Calculate inf.");
	Button reset_but=new Button("Calculate inf.");
	GridPane grid=new GridPane();
	Label result_text=new Label();
	@Override
	public void start(Stage main_stage) throws Exception {
		VBox root=new VBox();
		
		HBox file_b=new HBox();
		HBox calc_b=new HBox();
		
		file_b.getChildren().addAll(read_but,def_but);
		calc_b.getChildren().addAll(calc1_but,calc10_but,calc100_but,calcx_but,reset_but);
		root.getChildren().addAll(file_b,calc_b,grid,result_text);
		
		
		Scene scene = new Scene(root, 500, 500);
		main_stage.setTitle("UI evo sim");
		main_stage.setScene(scene);
	    main_stage.show();
	}

}
