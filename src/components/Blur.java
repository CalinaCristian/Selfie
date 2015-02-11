package components;

import types.TaskType;
import messaging.Message;
import messaging.MessageImage;

/**
 * This class extends the Component class and creates the blur effect
 * @author Calina Cristian
 *
 */
public class Blur extends Component{

	public Blur() {
		super(TaskType.BLUR);
	}
	/**
	 * This function creates an auxiliary matrix of pixels and uses it to
	 * change every pixel to the sum of it's neighbors divided by the number
	 * of neighbors 
	 * @param message 
	 * @return image
	 */
	@Override
	public Message notify(Message message) {
		
		MessageImage image = (MessageImage) message;
		int pixels[][][] = image.getPixels();
		int inputPixels[][][] = new int [image.getHeight()][image.getWidth()][3];
		
		
		int neighbourNumber;
		int neighbourRedSum;
		int neighbourGreenSum;
		int neighbourBlueSum;
		
		for (int k = 0 ; k < 10 ; k ++){
			for (int i = 0 ; i < image.getHeight() ; i ++){
				for (int j = 0 ; j < image.getWidth() ; j ++){
					neighbourRedSum = 0;
					neighbourGreenSum = 0;
					neighbourBlueSum = 0;
					neighbourNumber = 0;
					for (int lines = i - 1 ; lines <= i + 1 ; lines ++){
						for (int cols = j - 1 ; cols <= j + 1 ; cols ++) {
							if (lines >= 0 && lines < image.getHeight() && cols >= 0 && 
									cols < image.getWidth() && (lines != i || cols != j) ){
								neighbourNumber++;
								neighbourRedSum += pixels[lines][cols][0];
								neighbourGreenSum += pixels[lines][cols][1];
								neighbourBlueSum += pixels[lines][cols][2];
							}
						}
					}
					inputPixels[i][j][0] = Math.round((float) neighbourRedSum/(float)neighbourNumber);
					inputPixels[i][j][1] = Math.round((float) neighbourGreenSum/(float)neighbourNumber);
					inputPixels[i][j][2] = Math.round((float) neighbourBlueSum/(float)neighbourNumber);
				}
			}
			for (int i = 0 ; i < image.getHeight() ; i ++){
				for (int j = 0 ; j < image.getWidth() ; j ++){
					for (int q = 0 ; q < 3 ; q ++){
						pixels[i][j][q] = inputPixels[i][j][q];
					}
				}
			}
		}
		image.setPixels(pixels);
		return image;
	}

}
