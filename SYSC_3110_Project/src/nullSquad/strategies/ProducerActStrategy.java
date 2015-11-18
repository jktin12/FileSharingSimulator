package nullSquad.strategies;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.users.*;

public interface ProducerActStrategy {
	
	
	public void act(Producer producer, FileSharingSystem network, int kResults);
	
}
