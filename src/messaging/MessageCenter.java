package messaging;

import java.util.LinkedList;
import java.util.List;

import components.*;

/**
 * MessageCenter contains the name of the center , it's neighbors , 
 * it's components , and it's list of message id's.
 * @author Calina Cristian
 *
 */
public class MessageCenter {
	private String centerName;
	List<MessageCenter> neighbors;
	List<Component> components; 
	List<Integer> messageID;
	
	/**
	 *Sets the centerName to be the first position of the array.
	 *Initialize the lists of neighbors,components and messageID's.
	 *Creates the component list with a switch case.
	 * @param centerAndComponents on the first position is the name and on the next
	 * positions are the components.
	 */
	public MessageCenter(String centerAndComponents[]) {
		
		super();
		this.centerName = centerAndComponents[0];
		
		neighbors = new LinkedList<MessageCenter>();
		components = new LinkedList<Component>();
		messageID = new LinkedList<Integer>();
		
		 for (int i = 1 ; i < centerAndComponents.length ; i++)
		 	switch (centerAndComponents[i]){
		 	case "Zoom" :
		 		components.add(new Zoom());
		 		break;
		 	case "Blur" :
		 		components.add(new Blur());
		 		break;
		 	case "BlackWhite" : 
		 		components.add(new BlackWhite());
		 		break;
		 	case "Sepia" : 
		 		components.add(new Sepia());
		 		break;
		 	case "RawPhoto" :
		 		components.add(new RawPhoto());
		 		break;
		 	case "NormalPhoto" :
		 		components.add(new NormalPhoto());
		 		break;
		 	case "ImageSaver" : 
		 		components.add(new ImageSaver());
		 		break;
		 	case "ImageLoader" :
		 		components.add(new ImageLoader());
		 		break;
		 	case "Flash" :
		 		components.add(new Flash());
		 		break;
		 	};
	}
	
	/**
	 * Adds to the neighbor list the received parameter.
	 * @param neighbor
	 */
	public void setNeighbour(MessageCenter neighbor){
		neighbors.add(neighbor);
	 }
	 
	 public String getName(){
		 return centerName;
	 }
	 
	public Message publish(Message message)	{
		System.out.println(centerName);
		return publishAlgorithm(message);
	}
	
	protected Message publishAlgorithm(Message message){
		if (!this.messageID.isEmpty()) {
			for (int i = 0 ; i < this.messageID.size() ; i ++){
				if (this.messageID.get(i) == message.getId()){
					return null;
				}
			}
		}
		this.messageID.add(message.getId());
		for (int j = 0 ; j < this.components.size() ; j++){
			if (message.getTaskType() == this.components.get(j).getTaskType()){
				return this.components.get(j).notify(message);
			}
		}
		for (int k = 0 ; k < this.neighbors.size() ; k ++){
			Message ret = neighbors.get(k).publish(message);
			if ( ret != null ){
				return ret;
			}
		}
		return null;
	}
}
