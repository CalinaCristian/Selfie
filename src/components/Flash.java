package components;

import messaging.Message;
import messaging.MessageFlash;
import messaging.MessageImage;
import types.TaskType;
import types.FlashType;

/**
 * This class extends the Component class and creates the flash effect
 * @author Calina Cristian
 *
 */
public class Flash extends Component{

	public Flash() {
		super(TaskType.FLASH);
	}

	/**
	 * This function creates a messageImage based on the messageFlash it received , 
	 * and if the flash type is on , it adds 50 to the RGB pixels and if it's auto
	 * it checks if the division of the given formula and the total size of picture
	 * is lower than 60. If it is , it applies the flash effect, else it returns the
	 * same picture (as if the flash type was off). 
	 * @param message
	 * @return image
	 */
	@Override
	public Message notify(Message message) {
		
		MessageFlash flash = (MessageFlash) message;
		int pixels[][][] = flash.getPixels();
		MessageImage image = new MessageImage(TaskType.FLASH, flash.getPixels(), 
				flash.getWidth(), flash.getHeight());
		
		if (flash.getFlashType() == FlashType.ON){
			for (int i = 0 ; i < flash.getHeight() ; i ++){
				for (int j = 0 ; j < flash.getWidth() ; j ++){
					for (int k = 0 ; k < 3 ; k ++){
						pixels[i][j][k] += 50;
					}
				}
			}
		}
		
		else if (flash.getFlashType() == FlashType.AUTO){
			double sum = 0;
			for (int i = 0 ; i < flash.getHeight() ; i ++){
				for (int j = 0 ; j < flash.getWidth() ; j ++){
					sum += Math.round(0.2126 * pixels[i][j][0] + 0.7152 * pixels[i][j][1] + 0.0722 * pixels[i][j][2]);
				}
			}
			sum = sum / (flash.getHeight()*flash.getWidth());
			if (sum < 60) {
				for (int i = 0 ; i < flash.getHeight() ; i ++){
					for (int j = 0 ; j < flash.getWidth() ; j ++){
						for (int k = 0 ; k < 3 ; k ++){
							pixels[i][j][k] += 50;
						}
					}
				}
			}
		}
		image.setPixels(pixels);
		this.pixelsOverMax(image);
		return image;
	}
}
