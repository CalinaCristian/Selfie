package components;

import messaging.Message;
import types.TaskType;

/**
 * This class extends the Component class and uses the RawPhoto 
 * class to flip the matrix vertically.
 * @author Calina Cristian
 *
 */
public class NormalPhoto extends Component {

	public NormalPhoto() {
		super(TaskType.NORMAL_PHOTO);
	}

	@Override
	public Message notify(Message message) {
		RawPhoto rp = new RawPhoto();
		return rp.notify(message);
	}

}
