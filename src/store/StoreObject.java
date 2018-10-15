package store;

import java.io.Serializable;

import common.GeneralInfo;
import common.Generator;
import entity.Node;
import entity.Server;

@SuppressWarnings("serial")
public class StoreObject implements Serializable{
	public GeneralInfo g = new GeneralInfo();
	public Generator generator = new Generator(g,10);
	
	//generator.Run();
	
	
	public void GenerateNodes(Server s) {
		
		for (int i = 0; i < g.numberOfNodes; ++i) {
			Node node = new Node(i, s, g, g.bufferSize);
			g.allNodes.add(node);
		}

	}
	
	
	
}
