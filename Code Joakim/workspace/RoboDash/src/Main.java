
import java.nio.ByteBuffer;

import javax.swing.text.html.parser.ParserDelegator;


//import com.JuhoMikael.RoboDash.Main.CanvasPlayerComponent;
import com.sun.jna.Memory;

import eu.hansolo.colors.MaterialDesign;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Gauge.SkinType;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Main extends Application {
	
	Stage primaryStage;
    Stage dialog = new Stage(StageStyle.UNDECORATED);
    Stage maoPopUp = new Stage(StageStyle.UNDECORATED);
	
	int meterHeight = 150;
	int meterWidth = 150;
	String tempText = "Temperature";
	String tempUnit = "°C";
	String brighText = "Brightness";
	String brightUnit = "%";
	String MQTTUrl;
	String StreamUrl;
	
	
	Gauge gauge11 = new Gauge();
	Gauge gauge12 = new Gauge();
	Gauge gauge21 = new Gauge();
	Gauge gauge22 = new Gauge();
	Gauge gauge31 = new Gauge();
	Gauge gauge32 = new Gauge();
	Gauge gauge41 = new Gauge();
	Gauge gauge42 = new Gauge();
	
	Double val = 0.0;
	Double val2 = 0.0;
	Double val3 = 0.0;
	Double val4 = 0.0;
	Double val5 = 0.0;
	Double val6 = 0.0;
	Double val7 = 0.0;
	Double val8 = 0.0;
	
	String x = "5";
	String y = "5";
	char[] objects ={'4','3','2','1','0'};
	
	
	Text DC = new Text("Dark & Cold");
	Text BC = new Text("Bright & Cold");
	Text DW = new Text("Dark & Warm");
	Text BW = new Text("Bright & Warm");
	
	StackPane canvasHolder = new StackPane();
	Button setDC = new Button("Set As Target");
	Button setBC = new Button("Set As Target");
	Button setDW = new Button("Set As Target");
	Button setBW = new Button("Set As Target");
	Button bmao = new Button("Objects & Loaction");
	Button s = new Button("Save & Start");
	Button exit = new Button("exit");
	Button cmao;
	
	TextField tf1 = new TextField("" );
    TextField tf2 = new TextField("" );
    
    CheckBox box = new CheckBox("Offline demo mode");
    
	
	private AnimationTimer timer;
	private long lastTimerCall;
	
	
	 //private static final String PATH_TO_VIDEO = "/home/jumi/Downloads/test.mp4";

	    private ImageView imageView;

	    private DirectMediaPlayerComponent mediaPlayerComponent;

	    private WritableImage writableImage;

	    private Pane playerHolder;

	    private WritablePixelFormat<ByteBuffer> pixelFormat;

	    private FloatProperty videoSourceRatioProperty;
	    
	    
	
	@Override
	public void init(){
		
		lastTimerCall = System.nanoTime() + 1_000_000_000l;
		
		timer = new AnimationTimer(){
			@Override public void handle(long now){
				if(now > lastTimerCall + 2_000_000l){
					gauge11.setValue(val);
					gauge12.setValue(val2);
					gauge21.setValue(val3);
					gauge22.setValue(val4);
					gauge31.setValue(val5);
					gauge32.setValue(val6);
					gauge41.setValue(val7);
					gauge42.setValue(val8);

					lastTimerCall=now;
				}
				
				
			}
		};
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			maoPopUp.initModality(Modality.APPLICATION_MODAL);
	        maoPopUp.initOwner(primaryStage);
			
	        MQTTPub setCommand = new MQTTPub();
			mapAndObjectView mao = new mapAndObjectView();
			
			BorderPane root = new BorderPane();
			
			//canvasHolder.setPrefSize(640, 480);
			root.setTop(getMeterDC());
			
			
			root.setBottom(getMeterBW());

			
			root.setLeft(getMeterDW());
			root.setRight(getMeterBC());

	        
			Scene scene = new Scene(root,900,900);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
	
			
			timer.start();
			
			 dialog.initModality(Modality.APPLICATION_MODAL);
		        dialog.initOwner(primaryStage);
		        VBox dialogVbox = new VBox(20);
		        Text t1 = new Text("MQQT Broker IP:");
		        Text t2 = new Text("Stream source:");
		        t1.setFill(Color.WHITE);
		        t2.setFill(Color.WHITE);
		        box.setTextFill(Color.WHITE);
		        
		        
		        dialogVbox.getChildren().addAll(t1,tf1,t2,tf2,box,s);
		        dialogVbox.setAlignment(Pos.CENTER);
		        dialogVbox.setBackground(new Background(new BackgroundFill(MaterialDesign.GREY_900.get(), CornerRadii.EMPTY, Insets.EMPTY)));
		        Scene dialogScene = new Scene(dialogVbox, 300, 300);
		        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		        dialog.setScene(dialogScene);
		        
		        dialog.show();

		    	
		    	s.setOnAction(e -> {
		    	
		    	
		    	if(box.isSelected()){
		    		dialog.close();
		    		System.out.println("Test Mode");
		    		val =  Math.random() * 100 + 1;
		    		val2 =  Math.random() * 100 + 1;
		    		val3 =  Math.random() * 100 + 1;
		    		val4 =  Math.random() * 100 + 1;
		    		val5 =  Math.random() * 100 + 1;
		    		val6 =  Math.random() * 100 + 1;
		    		val7 =  Math.random() * 100 + 1;
		    		val8 =  Math.random() * 100 + 1;	 
		    		StackPane testHolder = new StackPane();
		    		testHolder.getChildren().add(new Text("!!GuiTestMode!!"));
		    		root.setCenter(testHolder);
		    	
		    	}
		    	else{
		    		MQTTUrl = tf1.getText();
		    		StreamUrl = tf2.getText();
		    		System.out.println(MQTTUrl);
		    		System.out.println(StreamUrl);
		    		dialog.close();
		    		
		    		try{
		    		mediaPlayerComponent = new CanvasPlayerComponent();
			        playerHolder = new Pane();
			        playerHolder.setBackground(new Background(new BackgroundFill(MaterialDesign.GREY_900.get(), CornerRadii.EMPTY, Insets.EMPTY)));
			        playerHolder.setMaxSize(640, 480);
			        videoSourceRatioProperty = new SimpleFloatProperty(0.4f);
			        pixelFormat = PixelFormat.getByteBgraPreInstance();
			        initializeImageView();
			        root.setCenter(playerHolder);

			        mediaPlayerComponent.getMediaPlayer().playMedia(StreamUrl);
			        //mediaPlayerComponent.getMediaPlayer().prepareMedia(PATH_TO_VIDEO);
			        mediaPlayerComponent.getMediaPlayer().start();
			        
		    		}
		    		catch(Exception j){
		    			
		    			root.setCenter(new Text("VLC player not installed or you are running OSX; Your system is: " + System.getProperty("os.name")));
		    		}
			        
			        new Thread(new Runnable() {
						@Override public void run() {

					                MQTTSub ref = new MQTTSub();


					        		new Thread(new Runnable() {
					        			@Override public void run() {
					                ref.mqttRef(MQTTUrl,"/darkAndCold/temperature", "/darkAndCold/brightness", "/darkAndWarm/temperature", "/darkAndWarm/brightness", "/brightAndCold/temperature", "/brightAndCold/brightness","/brightAndWarm/temperature","/brightAndWarm/brightness", "/robotCoord/Xvalue", "/robotCoord/Yvalue", "/storage/boxPriority");
					                try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					        			}}).start();
					  
					                 
					while(true) { 
					                 val = Double.parseDouble(ref.getValue7());
					                 val2 = Double.parseDouble(ref.getValue8());
					                 
					                 val3 = Double.parseDouble(ref.getValue());
					                 val4 = Double.parseDouble(ref.getValue2());
					                 
					                 val5 = Double.parseDouble(ref.getValue5());
					                 val6 = Double.parseDouble(ref.getValue6());
					                 
					                 val7 = Double.parseDouble(ref.getValue3());
					                 val8 = Double.parseDouble(ref.getValue4());
					                 
					                 x = ref.getValue9();
					                 y = ref.getValue10();
					                 
					                 objects = ref.getArrayValue11();
					                 			                 
					                 try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

					        }
					    
						}}).start();
		    	}
		    	
		    	primaryStage.setFullScreen(true);
		    	});
		    	
		    	bmao.setOnAction(e -> {
		    		//Update values for display
		    		mao.setPrio(objects);
		    		mao.setXY(x, y);
		    		System.out.println(objects);
		    		
		    		
			        StackPane maohold = new StackPane();
			        maohold.getChildren().addAll(mao.getMOHolder());
			        maohold.setAlignment(Pos.CENTER);
			        maohold.setBackground(new Background(new BackgroundFill(MaterialDesign.GREY_900.get(), CornerRadii.EMPTY, Insets.EMPTY)));
			        Scene maoPopUPScene = new Scene(maohold, 800, 800);
			        maoPopUPScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			        maoPopUp.setScene(maoPopUPScene);
			        
			        maoPopUp.show();
			        
		    	});
		    	
		    	exit.setOnAction(e -> System.exit(0));
		    	
		    	cmao = mao.close;
		    	
		    	cmao.setOnAction(e -> {
		    		//Get priority values save them locally and send to MQTT pub
		    		setCommand.publish(MQTTUrl, "/robotCommand/boxPriority", mao.getPriorisation());
		    		//System.out.println("MQTT pub: /robotCommand/boxPriority/ "+ mao.getPriorisation() );
		    		maoPopUp.close();
		    		
		    		;});
		    	
		    	setBW.setOnAction(e -> {
		    		setCommand.publish(MQTTUrl, "/robotCommand/target", "BW");
		    	});
		    	
		    	setBC.setOnAction(e -> {
		    		setCommand.publish(MQTTUrl, "/robotCommand/target", "BC");
		    	});
		    	
		    	setDC.setOnAction(e -> {
		    		setCommand.publish(MQTTUrl, "/robotCommand/target", "DC");
		    	});

		    	setDW.setOnAction(e -> {
		    		setCommand.publish(MQTTUrl, "/robotCommand/target", "DW");
		    	});

		        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	 private void initializeImageView() {
	        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
	        writableImage = new WritableImage((int) visualBounds.getWidth(), (int) visualBounds.getHeight());

	        imageView = new ImageView(writableImage);
	        playerHolder.getChildren().add(imageView);

	        playerHolder.widthProperty().addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize(newValue.floatValue(), (float) playerHolder.getHeight());
	        });

	        playerHolder.heightProperty().addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize((float) playerHolder.getWidth(), newValue.floatValue());
	        });

	        videoSourceRatioProperty.addListener((observable, oldValue, newValue) -> {
	            fitImageViewSize((float) playerHolder.getWidth(), (float) playerHolder.getHeight());
	        });
	    }

	    private void fitImageViewSize(float width, float height) {
	        Platform.runLater(() -> {
	            float fitHeight = videoSourceRatioProperty.get() * width;
	            if (fitHeight > height) {
	                imageView.setFitHeight(height);
	                double fitWidth = height / videoSourceRatioProperty.get();
	                imageView.setFitWidth(fitWidth);
	                imageView.setX((width - fitWidth) / 2);
	                imageView.setY(0);
	            }
	            else {
	                imageView.setFitWidth(width);
	                imageView.setFitHeight(fitHeight);
	                imageView.setY((height - fitHeight) / 2);
	                imageView.setX(0);
	            }
	        });
	    }
	    
	    
	
public StackPane getMeterBC(){
		
		final Gauge gauge1 = GaugeBuilder.create()
				.skinType(SkinType.FLAT)
                .prefSize(meterWidth, meterHeight)
                .title(tempText)
                .unit(tempUnit)
                .minValue(0)
                .maxValue(100)
                .foregroundBaseColor(Color.WHITE)
                .barColor(Color.BLUEVIOLET)
                .animationDuration(2000)
                .build();
		
		final Gauge gauge2 = GaugeBuilder.create()
				.skinType(SkinType.FLAT)
                .prefSize(meterWidth, meterHeight)
                .title(brighText)
                .unit(brightUnit)
                .minValue(0)
                .maxValue(100)
                .foregroundBaseColor(Color.WHITE)
                .barColor(Color.BLUEVIOLET)
                .animationDuration(2000)
                .build();
		
		
		
		gauge11 =gauge1;
		gauge12 =gauge2;
		BC.setFill(Color.WHITE);
		
		VBox pane21 = new VBox();
		pane21.getChildren().addAll(BC,setBC,gauge11, gauge12);
		pane21.setAlignment(Pos.CENTER);
        
       pane21.setBackground(new Background(new BackgroundFill(MaterialDesign.GREY_900.get(), CornerRadii.EMPTY, Insets.EMPTY)));
        

    
        
		
		
		return new StackPane(pane21);
	}

public StackPane getMeterDC(){
	
	final Gauge gauge1 = GaugeBuilder.create()
			.skinType(SkinType.FLAT)
            .prefSize(meterWidth, meterHeight)
            .title(tempText)
            .unit(tempUnit)
            .minValue(0)
            .maxValue(100)
            .foregroundBaseColor(Color.WHITE)
            .barColor(Color.BLUEVIOLET)
            .animationDuration(2000)
            .build();
	
	final Gauge gauge2 = GaugeBuilder.create()
			.skinType(SkinType.FLAT)
            .prefSize(meterWidth, meterHeight)
            .title(brighText)
            .unit(brightUnit)
            .minValue(0)
            .maxValue(100)
            .foregroundBaseColor(Color.WHITE)
            .barColor(Color.BLUEVIOLET)
            .animationDuration(2000)
            .build();
	
	
	gauge21 =gauge1;
	gauge22 =gauge2;
	DC.setFill(Color.WHITE);
	
	
	VBox pane21 = new VBox();
	HBox pane11 = new HBox();
	BorderPane pane01 = new BorderPane();
	StackPane pane31 = new StackPane(bmao);
	StackPane pane41 = new StackPane(exit);
	
	pane31.setPadding(new Insets(20,20,20,20));
	pane41.setPadding(new Insets(20,20,20,20));
	
	pane01.setLeft(pane31);
	pane01.setRight(pane41);
	pane11.getChildren().addAll(gauge21, gauge22);
	pane11.setAlignment(Pos.CENTER);
	pane21.getChildren().addAll(pane01,DC,setDC,pane11);
	pane21.setAlignment(Pos.CENTER);
	
   
   pane21.setBackground(new Background(new BackgroundFill(MaterialDesign.GREY_900.get(), CornerRadii.EMPTY, Insets.EMPTY)));
    
	
	return new StackPane(pane21);
	
}

public StackPane getMeterBW(){
	
	final Gauge gauge1 = GaugeBuilder.create()
			.skinType(SkinType.FLAT)
            .prefSize(meterWidth, meterHeight)
            .title(tempText)
            .unit(tempUnit)
            .minValue(0)
            .maxValue(100)
            .foregroundBaseColor(Color.WHITE)
            .barColor(Color.BLUEVIOLET)
            .animationDuration(2000)
            .build();
	
	final Gauge gauge2 = GaugeBuilder.create()
			.skinType(SkinType.FLAT)
            .prefSize(meterWidth, meterHeight)
            .title(brighText)
            .unit(brightUnit)
            .minValue(0)
            .maxValue(100)
            .foregroundBaseColor(Color.WHITE)
            .barColor(Color.BLUEVIOLET)
            .animationDuration(2000)
            .build();
	
	
	gauge31 =gauge1;
	gauge32 =gauge2;
	BW.setFill(Color.WHITE);
	
	VBox pane21 = new VBox();
	HBox pane11 = new HBox();
	HBox pane01= new HBox();

	
	pane01.setAlignment(Pos.BOTTOM_LEFT);
	pane11.getChildren().addAll(gauge31, gauge32);
	pane11.setAlignment(Pos.CENTER);
	pane21.getChildren().addAll(BW,setBW,pane11,pane01);
	pane21.setAlignment(Pos.CENTER);
	
		
   pane21.setBackground(new Background(new BackgroundFill(MaterialDesign.GREY_900.get(), CornerRadii.EMPTY, Insets.EMPTY)));
    

	
	return new StackPane(pane21);
	
}

public StackPane getMeterDW(){
	
	final Gauge gauge1 = GaugeBuilder.create()
			.skinType(SkinType.FLAT)
            .prefSize(meterWidth, meterHeight)
            .title(tempText)
            .unit(tempUnit)
            .minValue(0)
            .maxValue(100)
            .foregroundBaseColor(Color.WHITE)
            .barColor(Color.BLUEVIOLET)
            .animationDuration(2000)
            .build();
	
	final Gauge gauge2 = GaugeBuilder.create()
			.skinType(SkinType.FLAT)
            .prefSize(meterWidth, meterHeight)
            .title(brighText)
            .unit(brightUnit)
            .minValue(0)
            .maxValue(100)
            .foregroundBaseColor(Color.WHITE)
            .barColor(Color.BLUEVIOLET)
            .animationDuration(2000)
            .build();
	
	gauge41 =gauge1;
	gauge42 =gauge2;
	DW.setFill(Color.WHITE);
	
	VBox pane21 = new VBox();
	pane21.getChildren().addAll(DW,setDW,gauge41, gauge42);
	
	
	pane21.setAlignment(Pos.CENTER);
	pane21.setBackground(new Background(new BackgroundFill(MaterialDesign.GREY_900.get(), CornerRadii.EMPTY, Insets.EMPTY)));

	
	
	return new StackPane(pane21);
	
}

public class CanvasPlayerComponent extends DirectMediaPlayerComponent {

    public CanvasPlayerComponent() {
        super(new CanvasBufferFormatCallback());
    }

    PixelWriter pixelWriter = null;

    private PixelWriter getPW() {
        if (pixelWriter == null) {
            pixelWriter = writableImage.getPixelWriter();
        }
        return pixelWriter;
    }

    @Override
    public void display(DirectMediaPlayer mediaPlayer, Memory[] nativeBuffers, BufferFormat bufferFormat) {
        if (writableImage == null) {
            return;
        }
        Platform.runLater(() -> {
            Memory nativeBuffer = mediaPlayer.lock()[0];
            try {
                ByteBuffer byteBuffer = nativeBuffer.getByteBuffer(0, nativeBuffer.size());
                getPW().setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, byteBuffer, bufferFormat.getPitches()[0]);
            }
            finally {
                mediaPlayer.unlock();
            }
        });
    }
}

private class CanvasBufferFormatCallback implements BufferFormatCallback {
    @Override
    public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Platform.runLater(() -> videoSourceRatioProperty.set((float) sourceHeight / (float) sourceWidth));
        return new RV32BufferFormat((int) visualBounds.getWidth(), (int) visualBounds.getHeight());
    }
}
}
