package network;

import model.*;

public abstract class Packet
{
	public abstract void Packet();
	public abstract boolean execute(UserAssistant theUser, Object arg);
}
