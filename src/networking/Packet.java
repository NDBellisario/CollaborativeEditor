package networking;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Packet implements Serializable
{

	public abstract boolean execute();
}