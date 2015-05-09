import hsa.*;
import java.awt.event.*;


public class ControlsConsole extends hsa.Console
{   

    private boolean [] _keys;
    
    public ControlsConsole(){
	super();
	_keys = new boolean[26];
	for (int i = 0; i < 26; i++){
	    _keys[i] = false;
	}
    }
    
    public ControlsConsole(int columns, int rows){
	super(columns, rows);
	_keys = new boolean[26];
	for (int i = 0; i < 26; i++){
	    _keys[i] = false;
	}
    }
    
    public boolean isKeyDown(char c){
	c -= 'A';
	return _keys[c];
    }
    
    public synchronized void keyPressed (KeyEvent e){
	int key = e.getKeyCode()  - 'A';
	if (key <= 26 && key >= 0)
	    _keys[key] = true;
	super.keyPressed (e);
    }
    
    public synchronized void keyReleased (KeyEvent e){
	int key = e.getKeyCode() - 'A';
	if (key <= 26 && key >= 0)
	    _keys[key] = false;
	super.keyReleased (e);
    }
}
