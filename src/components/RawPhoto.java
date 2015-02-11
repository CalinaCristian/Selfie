package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

/**
 * This class extends the Component class and flips the 
 * pixels vertically using only the height for this operation.
 * @author Calina Cristian
 *
 */
public class RawPhoto extends Component{

	public RawPhoto() {
		super(TaskType.RAW_PHOTO);
	}

	@Override
	public Message notify(Message message) {
		MessageImage image = (MessageImage) message;
		int [][][] pixels = image.getPixels();
		for (int i = 0 ; i < image.getHeight() /2 ; i ++){
			int[][] temp = pixels[image.getHeight() - i - 1];
			pixels[image.getHeight() - i - 1] = pixels[i];
			pixels[i] = temp;
		}
		image.setPixels(pixels);
		return image;
	}

}
