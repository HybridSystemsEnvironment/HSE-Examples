package network;

/**
 * The basic connection is a graph edge that directly connects two objects of
 * class T.
 * 
 * @author Brendan Short
 * 
 * @param <T>
 *            The class type of the vertices that are to be connected
 */
public class BasicConnection<T>
{

	/*
	 * Source vertex
	 */
	private T source;
	/*
	 * Target vertex
	 */
	private T target;

	/**
	 * Constructor for the connection from specified source to destination
	 * 
	 * @param sourceVertex
	 *            start vertex
	 * @param targetVertex
	 *            end vertex
	 */
	public BasicConnection(T sourceVertex, T targetVertex)
	{
		this.source = sourceVertex;
		this.target = targetVertex;

	}

	/**
	 * Get source vertex
	 * 
	 * @return source vertex
	 */
	public T getSource()
	{
		return source;
	}

	/**
	 * Get target vertex
	 * 
	 * @return target vertex
	 */
	public T getTarget()
	{
		return target;
	}

}
