package de.gandalf1783.tilegame.discord;


import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * An examples showing how to automatically download, extract and load
 * Discord's native library.
 */
public class DownloadNativeLibrary
{
	public static File downloadDiscordLibrary() throws IOException
	{
		// Find out which name Discord's library has (.dll for Windows, .so for Linux)
		String name = "discord_game_sdk";
		String suffix;
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			suffix = ".dll";
		}
		else
		{
			suffix = ".so";
		}

		// Path of Discord's library inside the ZIP
		String zipPath = "lib/x86_64/"+name+suffix;

		// Open the URL as a ZipInputStream
		URL downloadUrl = new URL("http://dl-game-sdk.discordapp.net/2.5.6/discord_game_sdk.zip");
		ZipInputStream zin = new ZipInputStream(downloadUrl.openStream());

		// Search for the right file inside the ZIP
		ZipEntry entry;
		while((entry = zin.getNextEntry())!=null)
		{
			if(entry.getName().equals(zipPath))
			{
				// Create a new temporary directory
				// We need to do this, because we may not change the filename on Windows
				File tempDir = new File(System.getProperty("java.io.tmpdir"), "java-"+name+System.nanoTime());
				if(!tempDir.mkdir())
					throw new IOException("Cannot create temporary directory");
				tempDir.deleteOnExit();

				// Create a temporary file inside our directory (with a "normal" name)
				File temp = new File(tempDir, name+suffix);
				temp.deleteOnExit();

				// Copy the file in the ZIP to our temporary file
				Files.copy(zin, temp.toPath());

				// We are done, so close the input stream
				zin.close();

				// Return our temporary file
				return temp;
			}
			// next entry
			zin.closeEntry();
		}
		zin.close();
		// We couldn't find the library inside the ZIP
		return null;
	}

	public static void main(String[] args)
	{
		try
		{
			File discordLibrary = downloadDiscordLibrary();
			if(discordLibrary == null)
			{
				System.err.println("Error downloading Discord SDK.");
				System.exit(-1);
			}
			// Initialize the Core
			Core.init(discordLibrary);

			// Set parameters for the Core
			try(CreateParams params = new CreateParams())
			{
				params.setClientID(698611073133051974L);
				params.setFlags(CreateParams.getDefaultFlags());
				// Create the Core
				try(Core core = new Core(params))
				{
					// Run callbacks forever
					while(true)
					{
						core.runCallbacks();
						try
						{
							// Sleep a bit to save CPU
							Thread.sleep(16);
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.err.println("Error downloading Discord SDK.");
			System.exit(-1);
		}
	}
}