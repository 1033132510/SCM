package com.zzc.common.es;

import java.net.InetAddress;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Test;

public class ProductSearchView /**extends BaseServiceTest*/ {


	@Test
	public void testFilter() throws Exception {
		
		Client client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.223"), 9300));
	}
	

}
