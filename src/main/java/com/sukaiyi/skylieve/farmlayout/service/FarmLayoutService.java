package com.sukaiyi.skylieve.farmlayout.service;

import java.io.IOException;

/**
 * @author sukaiyi
 */
public interface FarmLayoutService {

	void backup() throws IOException;

	void restore(String id);

	void delete(String id);
}
