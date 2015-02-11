import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import components.BlackWhite;
import components.Blur;
import components.Flash;
import components.ImageLoader;
import components.ImageSaver;
import components.NormalPhoto;
import components.RawPhoto;
import components.Sepia;
import components.Zoom;
import messaging.*;
import types.*;

/**
 * This is the main class of the project. It starts the project , 
 * builds the configuration file and runs the application.
 * @author Calina Cristian
 *
 */
public class SimulationManager {
	private MessageCenter messageCenter;
	private List<MessageCenter> networkList = new LinkedList<MessageCenter>();
	
	/**
	 * The constructor uses the network configuration file to build the network.
	 * @param networkConfigFile 
	 */
	public SimulationManager(String networkConfigFile) {
		this.messageCenter = buildNetwork(networkConfigFile);
	}
	
	
	/**
	 * Builds the network of message centers.
	 * @param networkConfigFile configuration file
	 * @return networkList.get(0) the first message center from the configuration file
	 */
	private MessageCenter buildNetwork(String networkConfigFile) {
		int linesNumber;
		String line;
		try {
			Scanner sc = new Scanner(new File(networkConfigFile));
			linesNumber = sc.nextInt();
			sc.nextLine();
			for (int i = 0 ; i < linesNumber ; i ++ ) {
	            line = sc.nextLine().toString();
	            String centerAndComponents[] = line.split(" ");
	            MessageCenter msc = new MessageCenter(centerAndComponents);
	            networkList.add(msc);
	        }
			
			for (int i = 0 ; i < linesNumber ; i ++ ) {
				line = sc.nextLine().toString();
				String centerAndNeighbors[] = line.split(" ");
				for (int j = 1 ; j < centerAndNeighbors.length ; j ++ ){
					for ( int k = 0 ; k < networkList.size() ; k ++){
						if (networkList.get(k).getName().equals(centerAndNeighbors[j])){
							networkList.get(i).setNeighbour(networkList.get(k));
						}
					}
				}
			}
	        sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return networkList.get(0);
	}
	
	
	/**
	 * Reads commands from standard input and uses the messageCenter to solve all the tasks
	 */
	public void start() {
		
		//Input image , Output image, an array for Flash and Zoom(pre) , an array
		//for NormalPhoto and RawPhoto(capture) and an array for effects(post).
		String imageIn;
		String imageOut;
		String [] preCapture;
		String [] capture;
		String [] postCapture;
		
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNextLine()){
			
			String line = sc.nextLine().toString();
			
			if (line.toString().equals("exit")){
				sc.close();
				return;
			}
			
			else {
				
				//An array delimited by spaces
				String [] tasks = line.split(" ");
				
				//Sets arrays using their delimiters.
				imageIn = tasks[0];
				imageOut = tasks[1];
				preCapture = tasks[2].split("\\(|\\)|\\=|\\;|\\,");
				capture = tasks[3].split("\\(|\\=|\\)");
				postCapture = tasks[4].split("\\(|\\;|\\)");
				
				
				//Load the image from the first MessageCenter (and checking in publish if it has the option)
				MessageLoad load = new MessageLoad (TaskType.IMAGE_LOAD , imageIn);
				MessageImage image = (MessageImage)this.messageCenter.publish(load);
				image.generateId();
				
				
				//If preCapture has more than 3 items it means that it has also Zoom so it executes flash then zoom
				if (preCapture.length > 3){
					
					MessageFlash flash = new MessageFlash (TaskType.FLASH , image.getPixels() , image.getWidth(), 
							image.getHeight() , FlashType.valueOf(preCapture[2].toUpperCase()));
					image = (MessageImage)this.messageCenter.publish(flash);
					image.generateId();
					
					MessageZoom zoom = new MessageZoom (TaskType.ZOOM, image.getPixels() , image.getWidth(), 
							image.getHeight() , Integer.parseInt(preCapture[4]) , Integer.parseInt(preCapture[5]) , 
							Integer.parseInt(preCapture[6]), Integer.parseInt(preCapture[7]));
					image = (MessageImage)this.messageCenter.publish(zoom);
					image.generateId();
				}
	
				
				//Else it does the flash effect by sending it to the first messageCenter publish algorithm
				else{
					MessageFlash flash = new MessageFlash (TaskType.FLASH , image.getPixels() , image.getWidth(), 
							image.getHeight() , FlashType.valueOf(preCapture[2].toUpperCase()));
					image = (MessageImage)this.messageCenter.publish(flash);
					image.generateId();
				}
				
				
				
				//Choosing the capture of the photo (normal or raw) and publishing it through the MessageCenter
				MessageImage rawPhoto = new MessageImage (TaskType.RAW_PHOTO , image.getPixels() , image.getWidth(),
						image.getHeight());
				switch (capture[2]){
				case "normal":
					MessageImage normalPhoto = new MessageImage (TaskType.NORMAL_PHOTO , image.getPixels() , image.getWidth(),
							image.getHeight());
					image = (MessageImage) this.messageCenter.publish(rawPhoto);
					image.generateId();
					image = (MessageImage) this.messageCenter.publish(normalPhoto);
					image.generateId();
					break;
				case "raw":
					image = (MessageImage) this.messageCenter.publish(rawPhoto);
					image.generateId();
					break;
				}
				
				
				//Choosing the effect for the photo (black_white,sepia,blur) and publishing it through the MessageCenter
				if (postCapture.length > 1){
					for ( int i = 1 ; i < postCapture.length ; i ++){
						switch (postCapture[i]){
						case "black_white":
							MessageImage blackWhite = new MessageImage(TaskType.BLACK_WHITE, image.getPixels(), image.getWidth(),
									image.getHeight());
							image = (MessageImage) this.messageCenter.publish(blackWhite);
							//image.generateId();
							break;
						case "sepia":
							MessageImage sepia = new MessageImage(TaskType.SEPIA, image.getPixels(), image.getWidth(),
									image.getHeight());
							image = (MessageImage) this.messageCenter.publish(sepia);
							image.generateId();
							break;
						case "blur":
							MessageImage blur = new MessageImage(TaskType.BLUR, image.getPixels(), image.getWidth(),
									image.getHeight());
							image = (MessageImage) this.messageCenter.publish(blur);
							image.generateId();
							break;
						}
					}
				}
				
				
				//Creating and saving the output image.
				MessageSave save = new MessageSave(TaskType.IMAGE_SAVE , image.getPixels(), 
						image.getWidth(), image.getHeight() , imageOut);
				MessageSuccess success = (MessageSuccess)this.messageCenter.publish(save);
				success.generateId();
			}
		}
		sc.close();
	}
	
	
	/**
	 * Main method
	 * Starts the simulation after creating the network configuration.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: java SimulationManager <network_config_file>");
			return;
		}
		
		SimulationManager simulationManager = new SimulationManager(args[0]);
		simulationManager.start();
	
	}

}
