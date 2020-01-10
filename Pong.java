import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Define our drawing area, which is a JPanel. The ActionListener is for the timer that redraws
 * the screen, and the KeyListener is for the keyboard
 * 
 */
public class Pong extends JPanel implements ActionListener, KeyListener {

    private int ballX = 250;
    private int ballY = 250;
    private int ballSize = 10;
    private int ballMotionX = 3, ballMotionY = 3;
    private int leftPaddleX = 0, leftPaddleY = 225;
    private int rightPaddleX, rightPaddleY = 225;
    private int paddleHeight = 90, paddleWidth = 10, paddleMotion = 5;
    private boolean playerOneUp = false;
    private boolean playerOneDown = false;
    private boolean playerTwoUp = false;
    private boolean playerTwoDown = false;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int frameWidth = 0, frameHeight = 0, newWidth, newHeight;
    private Color bgColor[] = {Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA,
                               Color.CYAN, Color.YELLOW, Color.PINK, Color.LIGHT_GRAY, Color.WHITE};
    private Color fgColor[] = {Color.WHITE, Color.RED, Color.LIGHT_GRAY, Color.PINK, Color.BLACK,
                               Color.RED, Color.BLACK, Color.GREEN, Color.CYAN, Color.BLACK};
    private int colorValue;
                
    /**
     *
     * Constructor for Pong
     */
    public Pong(){
        Random generator = new Random();
        colorValue = generator.nextInt(10); 
        setBackground(bgColor[colorValue]);
        
        // Listen to key events
        setFocusable(true);
        addKeyListener(this);
        
        // Start an event timer at 60 fps
        Timer timer = new Timer(1000/60, this);
        timer.start();
    }


    /**
     * 
     * Define the method that gets called when the timer goes off
     */
    public void actionPerformed(ActionEvent e){
        doit();
    }

  /**
     * 
     * This method updates the locations of graphical items in the Pong panel
     */
    public void doit(){
        
        newWidth = getWidth();
        newHeight = getHeight();
        // If this is the first time entering this method, or if the window size
          // has changed, then set default paddle locations and size
        if ((newWidth != frameWidth) || newHeight != frameHeight){
            rightPaddleX = getWidth() - paddleWidth;
            leftPaddleY = getHeight() / 2 - paddleHeight / 2;
            rightPaddleY = getHeight() / 2 - paddleHeight / 2;
            paddleHeight = getHeight() / 10;
            frameWidth = newWidth;
            frameHeight = newHeight;
        }
        
        // Move the paddles if the up or down keys are being pressed
        if (playerOneUp == true) leftPaddleY -= paddleMotion;
        if (playerOneDown == true) leftPaddleY += paddleMotion;
        if (playerTwoUp == true) rightPaddleY -= paddleMotion;
        if (playerTwoDown == true) rightPaddleY += paddleMotion;
      
          // Move the ball
        ballX += ballMotionX;
        ballY += ballMotionY;
        // check if the ball hit the top or bottom
        if ((ballY < 0) || (ballY + ballSize) > frameHeight){
            // Toolkit.getDefaultToolkit().beep();
            ballMotionY *= -1;
        }
          // Check if the ball hit the sides. Check if the call is within the racket; if so,
          // reverse the X motion, and, if not, then update the score
        if (ballX < paddleWidth) {            
            if (ballY + ballSize >= leftPaddleY && ballY <= leftPaddleY + paddleHeight){
                ballMotionX *= -1;               
            }
            else{
                ballX = getWidth() / 2;
                ballY = getHeight() / 2;
                ++playerTwoScore;    
                if (playerTwoScore > 9){
                  System.exit(0);
                }
                
            }
        }
         if (ballX + ballSize > frameWidth - paddleWidth){
            if (ballY + ballSize >= rightPaddleY && ballY <= rightPaddleY + paddleHeight){
                ballMotionX *= -1;               
            }
           else{
            ballX = getWidth() / 2;
            ballY = getHeight() / 2;
            ++playerOneScore;    
            if (playerOneScore > 9){
               System.exit(0);
            }
        }
        }
       
        //|| (ballX + ballSize) > frameWidth)       
        // Tell this JPanel to repaint itself
        repaint();
    }

    // Draw the paddles, ball, goal lines, and scores
    public void paintComponent(Graphics g){

        // Perform the default painting operations for JPanel, including the background
        super.paintComponent(g);
        
        // Set the foreground color for graphics
        g.setColor(fgColor[colorValue]);

        //draw the current score
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
        g.drawString(String.valueOf(playerOneScore), 100, 50);
        g.drawString(String.valueOf(playerTwoScore), frameWidth - 100, 50);

        // Draw the ball
        g.fillOval(ballX, ballY, ballSize, ballSize);
        // Draw the left paddle
        g.fillRect(leftPaddleX, leftPaddleY, paddleWidth, paddleHeight);
        g.fillRect(rightPaddleX, rightPaddleY, paddleWidth, paddleHeight);
    }
    
    public void Faster ( ) {              
        // Increment the speed
        if (ballMotionX >= 0)
           ++ballMotionX;       
        else 
           --ballMotionX;
        if (ballMotionY >= 0)
           ++ballMotionY;        
        else 
           --ballMotionY;      
    }
    
    public void Slower ( ) {
       if (ballMotionX >= 0)
           --ballMotionX;       
        else 
           ++ballMotionX;
       if (ballMotionY >= 0)
           --ballMotionY;        
        else 
           ++ballMotionY;   
       
    }
    
    // Not used
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            playerTwoUp = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerTwoDown = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            playerOneUp = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_Z) {
            playerOneDown = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            Slower ();
        }
        else if (e.getKeyCode() == KeyEvent.VK_F) {
            Faster ();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            playerTwoUp = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerTwoDown = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            playerOneUp = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_Z) {
            playerOneDown = false;
        }
    }
    
    
}

