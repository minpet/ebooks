package com.minpet.local.interf;

import java.io.Serializable;

public interface IVersionService extends Serializable{

	String getVersion();

	String getBuildDate();

}
