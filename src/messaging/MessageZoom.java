package messaging;

import types.TaskType;

public class MessageZoom extends Message{

	private int[][][] pixels;
	private int width, height;
	private int xUp , yUp;
	private int xDown, yDown;
	
	/**
	 * Receives four more parameters which represent the up-Left
	 * and the down-right corners coordinates.
	 * @param taskType
	 * @param pixels
	 * @param width
	 * @param height
	 * @param xUp
	 * @param yUp
	 * @param xDown
	 * @param yDown
	 */
	public MessageZoom(TaskType taskType, int[][][] pixels,
			int width, int height, int xUp, int yUp, int xDown, int yDown) {
		super(taskType);
		this.pixels = pixels;
		this.width = width;
		this.height = height;
		this.xUp = xUp;
		this.yUp = yUp;
		this.xDown = xDown;
		this.yDown = yDown;
	}

	public int getUpX(){
		return xUp;
	}
	
	public int getUpY(){
		return yUp;
	}
	
	public int getDownX(){
		return xDown;
	}
	
	public int getDownY(){
		return yDown;
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
}
