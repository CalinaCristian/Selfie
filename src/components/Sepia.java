package components;

import types.TaskType;
import messaging.Message;
import messaging.MessageImage;

/**
 * This class extends the Component class and creates the sepia effect.
 * @author Cristian
 *
 */
public class Sepia extends Component{

	public Sepia () {
		super(TaskType.SEPIA);
	}

	@Override
	public Message notify(Message message) {
		MessageImage image = (MessageImage) message;
		int pixels[][][] = image.getPixels();
		int inputRed;
		int inputGreen;
		int inputBlue;
		for (int i = 0 ; i < image.getHeight() ; i ++){
			for (int j = 0 ; j < image.getWidth() ; j ++){
				inputRed = pixels[i][j][0];
				inputGreen = pixels[i][j][1];
				inputBlue = pixels[i][j][2];
				pixels[i][j][0] = (int) Math.round( (inputRed * 0.393) + (inputGreen * 0.769) + (inputBlue * 0.189) );
				pixels[i][j][1] = (int) Math.round( (inputRed * 0.349) + (inputGreen * 0.686) + (inputBlue * 0.168) );
				pixels[i][j][2] = (int) Math.round( (inputRed * 0.272) + (inputGreen * 0.534) + (inputBlue * 0.131) );
			}
		}
		image.setPixels(pixels);
		this.pixelsOverMax(image);
		return image;
	}

}
