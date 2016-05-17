import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class mapAndObjectView {

public Button close = new Button("Save and Close");
objectView obie = new objectView();	
mapView map = new mapView();

private int prio1 = 0;
private int prio2 = 0;
private int prio3 = 0;
private int prio4 = 0;
private int prio5 = 0;

private int x =5;
private int y = 5;

public void setPrio(char[] priority){
	prio1 = Character.getNumericValue(priority[0]);
	prio2 = Character.getNumericValue(priority[1]);
	prio3 = Character.getNumericValue(priority[2]);
	prio4 = Character.getNumericValue(priority[3]);
	prio5 = Character.getNumericValue(priority[4]);
	System.out.println("from mao: " + prio1+prio2+prio3+prio4+prio5);
}

public void setXY(String X, String Y){
	x = Integer.parseInt(X);
	y= Integer.parseInt(Y);
	
}


public VBox getMOHolder(){
	
	VBox movHolder = new VBox();
	StackPane closeHold = new StackPane();
	closeHold.getChildren().add(close);
	closeHold.setPadding(new Insets(40,10,10,10));
	
	movHolder.getChildren().addAll(obie.objects(prio1, prio2, prio3, prio4, prio5),closeHold, map.map(x, y));
	movHolder.setAlignment(Pos.CENTER);
	movHolder.setPadding(new Insets(40,10,10,10));
	

	
	return movHolder;
}

public String getPriorisation(){
	String a = (String) obie.v1.getValue();
	String b = (String) obie.v2.getValue();
	String c = (String) obie.v3.getValue();
	String d = (String) obie.v4.getValue();
	String e = (String) obie.v5.getValue();
	
	char[] collection = {getPrioNumber(a),getPrioNumber(b),getPrioNumber(c),getPrioNumber(d),getPrioNumber(e)};
	String priorities = new String(collection);
	 
	return priorities;
}

private char getPrioNumber(String selection){
	char prio= '0';
	
	if(selection.equals("Empty")){
		prio = '0';
	}
	else if(selection.equals("Low")){
		prio = '1';
	}
	else if(selection.equals("Moderate")){
		prio = '2';
	}
	else if(selection.equals("High")){
		prio = '3';
	}
	else{
		prio = '4';
	}
	return prio;
}

}
