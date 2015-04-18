package networking;

public class LoginPacket extends Packet
{

	String userName;
	String password;

	public LoginPacket(String uname, String pw)
	{
		userName = uname;
		password = pw;
	}
	public String getName()
	{
		return userName;
	}
	public boolean execute()
	{
		// TODO: Checks user info, returns if they can login or not!

		return true;

	}

}
