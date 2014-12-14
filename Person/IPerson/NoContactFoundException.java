/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

/**
 *
 * @author alexey
 */
public class NoContactFoundException extends RuntimeException{

    public NoContactFoundException(String message) {
        super(message);
    }
    
}
