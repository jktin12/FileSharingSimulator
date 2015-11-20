package nullSquad.strategies.act;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.users.*;

public interface ProducerActStrategy {
	
	
	public void act(Producer producer, FileSharingSystem fileSharingSystem, int kResults);
	
}
