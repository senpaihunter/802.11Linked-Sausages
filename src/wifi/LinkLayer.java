package wifi;

/*

package wifi;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import rf.RF;

/**
 * Use this layer as a starting point for your project code.  See {@link Dot11Interface} for more
 * details on these routines.
 * @author richards
 
public class LinkLayer implements Dot11Interface 
{
	private RF theRF;           // You'll need one of these eventually
	private short ourMAC;       // Our MAC address
	private PrintWriter output; // The output stream we'll write to
	private Queue<Boolean> ackQueue = new ArrayBlockingQueue<Boolean>(0);
	private Queue<byte[]> sendQueue = new ArrayBlockingQueue<byte[]>(0); //make this a packet object

	
	
	
	*//**
	 * Constructor takes a MAC address and the PrintWriter to which our output will
	 * be written.
	 * @param ourMAC  MAC address
	 * @param output  Output stream associated with GUI
	 *//*


	*//**
	 * Send method takes a destination, a buffer (array) of data, and the number
	 * of bytes to send.  See docs for full description.
	 *//*
	public int send(short dest, byte[] data, int len) {
		output.println("LinkLayer: Sending "+len+" bytes to "+dest);
		theRF.transmit(data);
		return len;
	}

	*//**
	 * Recv method blocks until data arrives, then writes it an address info into
	 * the Transmission object.  See docs for full description.
	 *//*
	public int recv(Transmission t) {
		System.out.println("please dont break");
		output.println("LinkLayer:blocking on recv()");
		byte[] packet;
		
		if(Receiver.popQueue()==null) {
			DIFS();	//wait difs as not to flood cpu time		
		}
		else {
			packet = Receiver.popQueue();
		}
		
		return 0; //fix this
	}
	

	*//**
	 * Returns a current status code.  See docs for full description.
	 *//*
	public int status() {
		output.println("LinkLayer: Faking a status() return value of 0");
		return 0;
	}

	*//**
	 * Passes command info to your link layer.  See docs for full description.
	 *//*
	public int command(int cmd, int val) {
		output.println("LinkLayer: Sending command "+cmd+" with value "+val);
		return 0;
	}
	
	

	
}*/



 
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import rf.RF;

/**
 * Use this layer as a starting point for your project code.  See {@link Dot11Interface} for more
 * details on these routines.
 * @author richards
 */
public class LinkLayer implements Dot11Interface, Runnable
{
	private RF theRF;           // You'll need one of these eventually
	private short ourMAC;       // Our MAC address
	private PrintWriter output; // The output stream we'll write to
	private Queue<Boolean> ackQueue = new ArrayBlockingQueue<Boolean>(0);
	private Queue<byte[]> sendQueue = new ArrayBlockingQueue<byte[]>(0); //make this a packet object

	/**
	 * Constructor takes a MAC address and the PrintWriter to which our output will
	 * be written.
	 * @param ourMAC  MAC address
	 * @param output  Output stream associated with GUI

 */
	
	public LinkLayer(short ourMAC, PrintWriter output) {
		this.ourMAC = ourMAC;
		this.output = output;      
		theRF = new RF(null, null);
		output.println("LinkLayer: Constructor ran.");
		//shoot of sender and receiver here
		Receiver recv = new Receiver(theRF, ackQueue);
		Sender sender = new Sender(theRF, ackQueue);
		(new Thread(recv)).start();
		System.exit(-1);
		(new Thread(sender)).start();
	}
	
	
	
	/*
	 * 	public LinkLayer(short ourMAC, PrintWriter output) {
		this.ourMAC = ourMAC;
		this.output = output;      
		theRF = new RF(null, null);
		output.println("LinkLayer: Constructor ran.");
	}
	 * 
	 */

	/**
	 * Send method takes a destination, a buffer (array) of data, and the number
	 * of bytes to send.  See docs for full description.
	 */
	public int send(short dest, byte[] data, int len) {
		output.println("LinkLayer: Sending "+len+" bytes to "+dest);
		theRF.transmit(data);
		return len;
	}

	/**
	 * Recv method blocks until data arrives, then writes it an address info into
	 * the Transmission object.  See docs for full description.
	 */
	public int recv(Transmission t) {
		output.println("LinkLayer: Pretending to block on recv()");
		while(true); // <--- This is a REALLY bad way to wait.  Sleep a little each time through.
		// return 0;
	}

	/**
	 * Returns a current status code.  See docs for full description.
	 */
	public int status() {
		output.println("LinkLayer: Faking a status() return value of 0");
		return 0;
	}

    private void DIFS(){
        int wait = RF.aSIFSTime + (RF.aSlotTime + RF.aSlotTime); //DIFS = SIFS + (SlotTime x 2) by 802.11 standard
        try{
            Thread.sleep(wait); //wait DIFS amount of time
        }
        
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }

    }
	
	/**
	 * Passes command info to your link layer.  See docs for full description.
	 */
	public int command(int cmd, int val) {
		output.println("LinkLayer: Sending command "+cmd+" with value "+val);
		return 0;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
