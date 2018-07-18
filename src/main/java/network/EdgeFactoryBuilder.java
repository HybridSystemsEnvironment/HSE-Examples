
package network;

import org.jgrapht.EdgeFactory;

import edu.ucsc.cross.hse.core.network.Connection;

public class EdgeFactoryBuilder<Q> implements EdgeFactory<Q, Connection<Q>> {

	public Connection<Q> builder;

	public EdgeFactoryBuilder(Connection<Q> build) {

		this.builder = build;
	}

	@Override
	public Connection<Q> createEdge(Q sourceVertex, Q targetVertex) {

		return builder.createConnection(sourceVertex, targetVertex);
	}

	public static <R> EdgeFactory<R, Connection<R>> createFactory(Connection<R> build) {

		return new EdgeFactoryBuilder<R>(build);
	}

}