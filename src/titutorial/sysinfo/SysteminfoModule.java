/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package titutorial.sysinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;
import org.json.JSONObject;

import android.os.Build;

@Kroll.module(name="Systeminfo", id="titutorial.sysinfo")
public class SysteminfoModule extends KrollModule
{

	// Standard Debugging variables
	private static final String TAG = "SysteminfoModule";

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;
	
	public SysteminfoModule()
	{
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		Log.d(TAG, "inside onAppCreate");
		// put module init code that needs to run when the application is created
	}

	// Methods
	@Kroll.method	
	public String getInfo()
	{
		JSONObject obj = new JSONObject();
		try{
			obj.put("OS_VERSION", android.os.Build.VERSION.RELEASE.toString());
			obj.put("API_LEVEL", (android.os.Build.VERSION.SDK_INT+"").toString());
			obj.put("MANUFACTURER", android.os.Build.MANUFACTURER.toString());
			obj.put("MODEL", android.os.Build.MODEL.toString());
			obj.put("RAM(kb)", getTotalRAM().toString());
			obj.put("RAM(mb)", getTotalRAMinMb().toString());
			obj.put("ABI", Build.CPU_ABI.toString());
			obj.put("PROCESSOR", getCPUInfo().toString());
		}catch(Exception e){
			
		}
		 
		 return obj.toString();
	}
	
	
	@Kroll.method
	public String getTotalRAM() {
	    RandomAccessFile reader = null;
	    String load = null;
	    try {
	        reader = new RandomAccessFile("/proc/meminfo", "r");
	        load = reader.readLine();
	        load = load.replace("MemTotal:", "");
	        load = load.replaceAll("\\s+", " ");
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } finally {
	        // Streams.close(reader);
	    }
	    return load;
	}

	@Kroll.method
	public String getTotalRAMinMb() {
		String ramInMb = null;
		String ram = getTotalRAM();
		if(ram != null){
			ram = ram.replace(" kB", "");
			ram = ram.replaceAll("\\s","");
			int ramValue = Integer.parseInt(ram);
			ramValue = Math.round(ramValue/1024);
			ramInMb = ramValue+" MB";
		}
	    return ramInMb;
	}
	
	@Kroll.method
	public String getCPUInfo() {
		 String processor = null;
	    if (new File("/proc/cpuinfo").exists()) {
	        try {
	            BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
	            String input = br.readLine();
	            String[] splits = input.split(":");
	            processor = splits[1];

	            if (br != null) {
	                br.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    }
	    return processor;
	}

}

