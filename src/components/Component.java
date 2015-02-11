package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public abstract class Component {
	private TaskType taskType;

	public Component(TaskType taskType) {
		super();
		this.taskType = taskType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	/**
	 * This function checks if the pixels of an image are over
	 * 255 value and sets them 255 in case it's true.
	 * It was implemented here because it is required in more than 1 extension.
	 * @param image
	 */
	public void pixelsOverMax(MessageImage image){
		int [][][] pixels = image.getPixels();
		for (int i = 0 ; i < image.getHeight() ; i ++){
			for (int j = 0 ; j < image.getWidth() ; j ++){
				for (int k = 0 ; k < 3 ; k ++){
					if (pixels[i][j][k] > 255){
						pixels[i][j][k] = 255;
					}
				}
			}
		}
		image.setPixels(pixels);
	}
	
	public abstract Message notify(Message message);
	
}
