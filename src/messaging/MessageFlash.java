package messaging;

import types.TaskType;
import types.FlashType;

public class MessageFlash extends Message {
		
	FlashType flashType;
	private int[][][] pixels;
	private int width, height;
	
	/**
	 * Receives an extra parameter , the flash type (Auto,On or Off)
	 * @param taskType
	 * @param pixels
	 * @param width
	 * @param height
	 * @param flashType
	 */
	public MessageFlash(TaskType taskType, int[][][] pixels,
			int width, int height , FlashType flashType) {
		super(taskType);
		this.pixels = pixels;
		this.width = width;
		this.height = height;
		this.flashType = flashType;
	}
	
	public int[][][] getPixels() {
		return pixels;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public FlashType getFlashType(){
		return flashType;
	}
}
