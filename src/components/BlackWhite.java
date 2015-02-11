package components;

import types.TaskType;
import messaging.Message;
import messaging.MessageImage;

/**
 * This class extends the Component class and creates the black_white effect
 * @author Calina Cristian
 *
 */
public class BlackWhite extends Component {

	public BlackWhite() {
		super(TaskType.BLACK_WHITE);
	}

	@Override
	public Message notify(Message message) {
		MessageImage image = (MessageImage) message;
		int [][][] pixels = image.getPixels();
		int inputRed;
		int inputGreen;
		int inputBlue;
		for (int i = 0 ; i < image.getHeight() ; i ++){
			for (int j = 0 ; j < image.getWidth() ; j ++){
				inputRed = pixels[i][j][0];
				inputGreen = pixels[i][j][1];
				inputBlue = pixels[i][j][2];
				for (int k = 0 ; k < 3 ; k ++ ){
					pixels[i][j][k] = (int) Math.round( (inputRed * 0.3) + (inputGreen * 0.59) + (inputBlue * 0.11) );
				}
			}
		}
		image.setPixels(pixels);
		this.pixelsOverMax(image);
		return image;
	}

}
