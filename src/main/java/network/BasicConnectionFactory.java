package network;

import org.jgrapht.EdgeFactory;

/**
 * The connection factory provides instructions for creating a connection.
 * 
 * @author Brendan Short
 * 
 * @param <T>
 *            the vertex type of the connections
 */
public class BasicConnectionFactory<T> implements EdgeFactory<T, BasicConnection<T>>
{

	/**
	 * Create a connection between two vertices of type T
	 * 
	 * @param <T>
	 *            class type of the vertices
	 * 
	 * @param source
	 *            start vertex
	 * 
	 * @param destination
	 *            end vertex
	 */
	@Override
	public BasicConnection<T> createEdge(T sourceVertex, T targetVertex)
	{
		return new BasicConnection<T>(sourceVertex, targetVertex);
	}
}
