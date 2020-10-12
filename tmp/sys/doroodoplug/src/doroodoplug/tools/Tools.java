package doroodoplug.tools;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;

import doroodoplug.Activator;
import doroodoplug.tcpser.SimpleTask;
import doroodoplug.util.socket.SanySocketServer;

public class Tools
{

	public Tools()
	{
	}

	public static boolean reshProjectFile(String projectName)
	{
		if (projectName.trim().isEmpty())
			return false;
		try
		{
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			project.refreshLocal(2, null);
		}
		catch (Exception e)
		{
			Activator.log(e.getMessage());
		}
		return true;
	}
	
	public static String getProjectPath(String projectName){
		if (projectName.trim().isEmpty())
			return "";
		try{
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			//String s=project.getLocationURI().toString();
			return project.getLocation().toOSString();
		}catch (Exception e)
		{
			Activator.log(e.getMessage());
		}
		return "";
	}
	
	public static void main(String[] args) {
		Class<?> cls = SimpleTask.class;
		SanySocketServer server;
		try {
			server = new SanySocketServer(2089, 5, cls);
			server.process();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
