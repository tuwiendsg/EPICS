/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.demo;
import at.ac.tuwien.dsg.orchestrator.services.OrchestratorService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author Jun
 */
public class TimerTest extends TimerTask {

    /**
     * @param args the command line arguments
     */
    
    private static int delta_t=2; // seconds
    private static int maxRunTime =60; // seconds
    private static int currentNoOfUser=0;
    private static List<Thread> threadList;
   
    
    
    
    
    
    
    @Override
    public void run() {
      //  System.out.println("Timer task started at:"+new Date());
        completeTask();
      //  System.out.println("Timer task finished at:"+new Date());
    }
 
    private void completeTask() {
      
            //assuming it takes 20 secs to complete the task
            //Thread.sleep(1000);
        
       //     System.out.println("do something ... ");
        
            
             double A = 60; // amplitude
        double f=15; // frequency
        double omega= 2*Math.PI/f;
        
      
        System.out.println();
        Calendar calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int t = minute*60 + second;
      
        double y = A*Math.sin(omega*t) +A;
        
        int changeNoOfUser = (int)Math.ceil(y);
        
        
        if (changeNoOfUser>currentNoOfUser) {
            
            
            for (int i=currentNoOfUser;i<changeNoOfUser;i++) {
                
                Thread thread_i = threadList.get(i);
                thread_i.start();
                
            }
            
            
            currentNoOfUser = changeNoOfUser;
            
        } else {
            
            for (int i=changeNoOfUser-1;i<currentNoOfUser;i++) {
                Thread thread_i = threadList.get(i);
                thread_i.stop();
            }
            
        }
        
            System.out.println(t + " , NoOfUsers:  " + currentNoOfUser);
            
            
        
        
    }
    
    public static void main(String[] args) {
        // init 100 users
        int maxNoOfUser = 5;
        threadList = new ArrayList<>();
                    
                
            
                for (int i=0;i<maxNoOfUser;i++) {
                    
                    String userID = String.valueOf(i+1);
                    Thread ti = new Thread(new OrchestratorService(userID), userID);
                    threadList.add(ti);
                }
        
        
      
        
        
        
        
        TimerTask timerTask = new TimerTest();
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        
        
        
        
        
        timer.scheduleAtFixedRate(timerTask, 0, delta_t*1000);
        System.out.println("TimerTask started");
        //cancel after sometime
        try {
            Thread.sleep(maxRunTime*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
        try {
            Thread.sleep(maxRunTime*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}
