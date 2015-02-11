package messaging;

import types.TaskType;

public abstract class Message {
	private TaskType taskType;
	private int messageId;
	private static int count = 0;
	
	public Message(TaskType taskType) {
		super();
		this.taskType = taskType;
		generateId();
	}
	
	/**
	 * Uses a static counter which increases everytime
	 * generateId is called and changes the messageId value.
	 */
	public void generateId() {
		Message.count ++;
		this.messageId = count;
	}
	
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public int getId() {
		return messageId;
	}
	
}
