package components;

import types.TaskType;
import messaging.Message;
import messaging.MessageImage;
import messaging.MessageZoom;

/**
 * This class extends the Component class and creates the zoom effect
 * @author Cristian
 *
 */
public class Zoom extends Component{

	public Zoom() {
		super(TaskType.ZOOM);
	}

	/**
	 * This function uses an auxiliary matrix to keep the
	 * original pixels in order to use them in the formula which
	 * cuts the picture. 
	 * @param message
	 * @return messageImage
	 */
	@Override
	public Message notify(Message message) {
		
		MessageZoom zoom = (MessageZoom) message;
		int originalpixels[][][] = zoom.getPixels();
		int width = zoom.getDownX() - zoom.getUpX() + 1;
		int height = zoom.getDownY() - zoom.getUpY() + 1;
		int pixels[][][] = new int [height][width][3]; 
		
		for ( int i = 0 ; i < height ; i ++){
			for (int j = 0 ; j < width ; j ++){
				for (int k = 0 ; k < 3 ; k ++){
					pixels[i][j][k] = originalpixels[zoom.getUpY() + i][zoom.getUpX() + j][k];
				}
			}
		}
		MessageImage messageImage = new MessageImage(TaskType.ZOOM, 
				pixels, width, height);
		return messageImage;
	}

}
