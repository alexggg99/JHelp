/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPersonTest;

import IPerson.ProxyClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexey
 */
public class ClientTest {
    
    ProxyClient client;

    @Before
    public void SetUp(){
        this.client = new ProxyClient();
    }
    
    @Test
    public void tableInsertTest(){
        
    }
}
