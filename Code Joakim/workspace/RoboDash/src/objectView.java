
import com.sun.javafx.geom.Rectangle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class objectView {

	int sw=5;
	int bs=35;
	
	Text h1 = new Text("Object1");
	Text h2 = new Text("Object2");
	Text h3 = new Text("Object3");
	Text h4 = new Text("Object4");
	Text h5 = new Text("Object5");
	
	Circle c1 = new Circle(10,10,bs);
	Group g1 = new Group(c1);
	
	Circle c2 = new Circle(10,10,bs);
	Group g2 = new Group(c2);
	
	Circle c3 = new Circle(10,10,bs);
	Group g3 = new Group(c3);
	
	Circle c4 = new Circle(10,10,bs);
	Group g4 = new Group(c4);
	
	Circle c5 = new Circle(10,10,bs);
	Group g5 = new Group(c5);
	
	
	Text l1 = new Text("High");
	Text l2 = new Text("Low");
	Text l3 = new Text("Empty");
	Text l4 = new Text("IDK");
	Text l5 = new Text("POO");
	
	private String[] values = {"Empty","Low","Moderate","High","Urgent"};
	

	public ComboBox v1 = new ComboBox();
	public ComboBox v2 = new ComboBox();
	public ComboBox v3 = new ComboBox();
	public ComboBox v4 = new ComboBox();
	public ComboBox v5 = new ComboBox();
	
	
public void setc1(int priority){
	
	switch(priority){
	case 0:  c1.setFill(Color.WHITE);
			 l1.setText("Empty");
			 v1.setValue(values[0]);
			 v1.setDisable(true);
    break;

	case 1:  c1.setFill(Color.GREEN);
	 l1.setText("Low");
	 v1.setValue(values[1]);
	 v1.setDisable(false);
	 break;
	 
	case 2:  c1.setFill(Color.YELLOW);
	 l1.setText("Moderate");
	 v1.setValue(values[2]);
	 v1.setDisable(false);
	 break;

	case 3:  c1.setFill(Color.ORANGE);
	 l1.setText("High");
	 v1.setValue(values[3]);
	 v1.setDisable(false);
	 break;

	case 4:  c1.setFill(Color.RED);
	 l1.setText("Urgent");
	 v1.setValue(values[4]);
	 v1.setDisable(false);
	 break;

	
	}
}

public void setc2(int priority){
	
	switch(priority){
	case 0:  c2.setFill(Color.WHITE);
			 l2.setText("Empty");
			 v2.setValue(values[0]);
			 v2.setDisable(true);
    break;

	case 1:  c2.setFill(Color.GREEN);
	 l2.setText("Low");
	 v2.setValue(values[1]);
	 v2.setDisable(false);
	 break;
	 
	case 2:  c2.setFill(Color.YELLOW);
	 l2.setText("Moderate");
	 v2.setValue(values[2]);
	 v2.setDisable(false);
	 break;

	case 3:  c2.setFill(Color.ORANGE);
	 l2.setText("High");
	 v2.setValue(values[3]);
	 v2.setDisable(false);
	 break;

	case 4:  c2.setFill(Color.RED);
	 l2.setText("Urgent");
	 v2.setValue(values[4]);
	 v2.setDisable(false);
	 break;

	
	}
}

public void setc3(int priority){
	
	switch(priority){
	case 0:  c3.setFill(Color.WHITE);
			 l3.setText("Empty");
			 v3.setValue(values[0]);
			 v3.setDisable(true);
    break;

	case 1:  c3.setFill(Color.GREEN);
	 l3.setText("Low");
	 v3.setValue(values[1]);
	 v3.setDisable(false);
	 break;
	 
	case 2:  c3.setFill(Color.YELLOW);
	 l3.setText("Moderate");
	 v3.setValue(values[2]);
	 v3.setDisable(false);
	 break;

	case 3:  c3.setFill(Color.ORANGE);
	 l3.setText("High");
	 v3.setValue(values[3]);
	 v3.setDisable(false);
	 break;

	case 4:  c3.setFill(Color.RED);
	 l3.setText("Urgent");
	 v3.setValue(values[4]);
	 v3.setDisable(false);
	 break;

	
	}
}

public void setc4(int priority){
	
	switch(priority){
	case 0:  c4.setFill(Color.WHITE);
			 l4.setText("Empty");
			 v4.setValue(values[0]);
			 v4.setDisable(true);
    break;

	case 1:  c4.setFill(Color.GREEN);
	 l4.setText("Low");
	 v4.setValue(values[1]);
	 v4.setDisable(false);
	 break;
	 
	case 2:  c4.setFill(Color.YELLOW);
	 l4.setText("Moderate");
	 v4.setValue(values[2]);
	 v4.setDisable(false);
	 break;

	case 3:  c4.setFill(Color.ORANGE);
	 l4.setText("High");
	 v4.setValue(values[3]);
	 v4.setDisable(false);
	 break;

	case 4:  c4.setFill(Color.RED);
	 l4.setText("Urgent");
	 v4.setValue(values[4]);
	 v4.setDisable(false);
	 break;

	
	}
}

public void setc5(int priority){
	
	switch(priority){
	case 0:  c5.setFill(Color.WHITE);
			 l5.setText("Empty");
			 v5.setValue(values[0]);
			 v5.setDisable(true);
    break;

	case 1:  c5.setFill(Color.GREEN);
	 l5.setText("Low");
	 v5.setValue(values[1]);
	 v5.setDisable(false);
	 break;
	 
	case 2:  c5.setFill(Color.YELLOW);
	 l5.setText("Moderate");
	 v5.setValue(values[2]);
	 v5.setDisable(false);
	 break;

	case 3:  c5.setFill(Color.ORANGE);
	 l5.setText("High");
	 v5.setValue(values[3]);
	 v5.setDisable(false);
	 break;

	case 4:  c5.setFill(Color.RED);
	 l5.setText("Urgent");
	 v5.setValue(values[4]);
	 v5.setDisable(false);
	 break;

	
	}
}


	
public StackPane objects(int o1p, int o2p, int o3p, int o4p,int o5p){
	
	setc1(o1p);
	setc2(o2p);
	setc3(o3p);
	setc4(o4p);
	setc5(o5p);
	
	v1.getItems().addAll(values);
	v2.getItems().addAll(values);
	v3.getItems().addAll(values);
	v4.getItems().addAll(values);
	v5.getItems().addAll(values);
	
	
	c1.setStrokeWidth(sw);
	c2.setStrokeWidth(sw);
	c3.setStrokeWidth(sw);
	c4.setStrokeWidth(sw);
	c5.setStrokeWidth(sw);
	
	c1.setStroke(Color.BLUEVIOLET);
	c2.setStroke(Color.BLUEVIOLET);
	c3.setStroke(Color.BLUEVIOLET);
	c4.setStroke(Color.BLUEVIOLET);
	c5.setStroke(Color.BLUEVIOLET);
	
	h1.setFill(Color.WHITE);
	h2.setFill(Color.WHITE);
	h3.setFill(Color.WHITE);
	h4.setFill(Color.WHITE);
	h5.setFill(Color.WHITE);
	h1.setFont(Font.font(21));
	h2.setFont(Font.font(21));
	h3.setFont(Font.font(21));
	h4.setFont(Font.font(21));
	h5.setFont(Font.font(21));
	
	l1.setFill(Color.WHITE);
	l2.setFill(Color.WHITE);
	l3.setFill(Color.WHITE);
	l4.setFill(Color.WHITE);
	l5.setFill(Color.WHITE);
	
	
	
	
	
	VBox col1 = new VBox(h1,g1,l1,v1);
	col1.setPadding(new Insets(10,10,10,10));
	col1.setSpacing(10);
	
	VBox col2 = new VBox(h2,g2,l2,v2);
	col2.setSpacing(10);
	col2.setPadding(new Insets(10,10,10,10));
	
	VBox col3 = new VBox(h3,g3,l3,v3);
	col3.setPadding(new Insets(10,10,10,10));
	col3.setSpacing(10);
	
	VBox col4 = new VBox(h4,g4,l4,v4);
	col4.setPadding(new Insets(10,10,10,10));
	col4.setSpacing(10);
	
	VBox col5 = new VBox(h5,g5,l5,v5);
	col5.setSpacing(10);
	col5.setPadding(new Insets(10,10,10,10));
	
	col1.setAlignment(Pos.CENTER);
	col2.setAlignment(Pos.CENTER);
	col3.setAlignment(Pos.CENTER);
	col4.setAlignment(Pos.CENTER);
	col5.setAlignment(Pos.CENTER);
	
	
	HBox allHolder = new HBox(col1,col2,col3,col4,col5);
	allHolder.setAlignment(Pos.CENTER);
	//objectHolder.getChildren().addAll(c1,c2,c3,c4,c5);
	
	StackPane pane = new StackPane();
	pane.getChildren().addAll(allHolder);
	pane.setAlignment(Pos.CENTER);
	return pane;
}
}
