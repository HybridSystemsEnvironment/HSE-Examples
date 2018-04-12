package network;

import java.util.ArrayList;

import org.jgrapht.graph.DirectedPseudograph;

/**
 * This class implements a basic network that connects elements of a specific
 * type T
 * 
 * @author Brendan Short
 * 
 * @param <T>
 *            the class type of the objects connected by the network
 *
 */
public class BasicNetwork<T>
{

	/**
	 * Directed graph that describes the network topology
	 */
	public DirectedPseudograph<T, BasicConnection<T>> topology;

	/**
	 * Constructor for a new network
	 */
	public BasicNetwork()
	{

		topology = new DirectedPseudograph<T, BasicConnection<T>>(new BasicConnectionFactory<T>());
	}

	/**
	 * Access the network topology graph
	 * 
	 * @return directed graph that describes the network topology
	 */
	public DirectedPseudograph<T, BasicConnection<T>> getTopology()
	{
		return topology;
	}

	/**
	 * Establish a connection between two vertices
	 * 
	 * @param source
	 *            start vertex
	 * @param target
	 *            end vertex
	 */
	public void establishConnection(T source, T target)
	{
		topology.addVertex(source);
		topology.addVertex(target);
		topology.addEdge(source, target);
	}

	/**
	 * Get all of the objects connected to a specified object
	 * 
	 * @param source
	 *            source vertex
	 * @return array list containing all objects that the source is connected to
	 */
	public ArrayList<T> getConnections(T source)
	{
		ArrayList<T> connections = new ArrayList<T>();
		for (BasicConnection<T> connection : topology.edgesOf(source))
		{
			connections.add(connection.getTarget());
		}
		return connections;
	}

	/**
	 * Get all of the vertices in the network
	 * 
	 * @return array list containing all objects that are vertices of the
	 *         network
	 */
	public ArrayList<T> getAllVertices()
	{
		ArrayList<T> connections = new ArrayList<T>();
		for (T connection : topology.vertexSet())
		{
			connections.add(connection);
		}
		return connections;
	}
}
