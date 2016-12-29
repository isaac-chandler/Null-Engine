package sandbox;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.Stdlib;

import java.io.File;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("natives/windows").getAbsolutePath());
		String deviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
		long device = ALC10.alcOpenDevice(deviceName);
		long context = ALC10.alcCreateContext(device, new int[] {0});
		ALC10.alcMakeContextCurrent(context);
		ALCCapabilities alcCaps = ALC.createCapabilities(device);
		ALCapabilities alCaps = AL.createCapabilities(alcCaps);

		if (alCaps.OpenAL10) {
			String file = "C:\\Users\\Isaac\\Desktop\\workspace\\assets\\audio\\gunshot2.ogg";

			MemoryStack.stackPush();
			IntBuffer channelsBuf = MemoryStack.stackMallocInt(1);
			MemoryStack.stackPush();
			IntBuffer sampleRateBuf = MemoryStack.stackMallocInt(1);

			ShortBuffer audioBuffer = STBVorbis.stb_vorbis_decode_filename(file, channelsBuf, sampleRateBuf);

			int channels = channelsBuf.get();
			int sampleRate = sampleRateBuf.get();

			MemoryStack.stackPop();
			MemoryStack.stackPop();

			int format = -1;
			if (channels == 1) {
				format = AL10.AL_FORMAT_MONO16;
			} else if (channels == 2) {
				format = AL10.AL_FORMAT_STEREO16;
			}

			int buffer = AL10.alGenBuffers();

			AL10.alBufferData(buffer, format, audioBuffer, sampleRate);

			Stdlib.free(audioBuffer);

			int[] sources = new int[4];
			AL10.alGenSources(sources);

			for (int source : sources) {
				AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
				AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_TRUE);
			}

			float length = (float) (AL10.alGetBufferi(buffer, AL10.AL_SIZE) / 2 / channels) / sampleRate;

			try {
				AL10.alSourcePlay(sources[0]);
				Thread.sleep((long) (length * 250));
				AL10.alSourcePlay(sources[1]);
				Thread.sleep((long) (length * 250));
				AL10.alSourcePlay(sources[2]);
				Thread.sleep((long) (length * 250));
				AL10.alSourcePlay(sources[3]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {}

			new Scanner(System.in).nextLine();

			AL10.alSourceStopv(sources);
			AL10.alDeleteSources(sources);
			AL10.alDeleteBuffers(buffer);
		}

		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
		ALC.destroy();
	}
}
