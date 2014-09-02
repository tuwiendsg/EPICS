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
    private static int maxRunTime =400; // seconds
    private static int currentNoOfUser=0;
    private static  int maxNoOfUser = 6;
    private static  int minNoOfUser =1;
    private static boolean isNoOfUserIncrease=true;
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
        
            
             double A = maxNoOfUser/2; // amplitude
             double B = minNoOfUser; // amplitude
             double f=6; // frequency
        double omega= 2*Math.PI/f;
        
      
        System.out.println();
        Calendar calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int t = minute*60 + second;
      
      //  double y = (A-B)*Math.sin(omega*t) +A;
        
        
        
      //  currentNoOfUser++;
        
        if (currentNoOfUser==minNoOfUser) {
            isNoOfUserIncrease = true;
            
        }
        
        if (currentNoOfUser==maxNoOfUser) {
            isNoOfUserIncrease = false;
        }
        
        
        double y=0;
        if (isNoOfUserIncrease) {
            y = currentNoOfUser +1;
        } else {
            y = currentNoOfUser -1;
            
        }
        
        int changeNoOfUser = (int)Math.ceil(y);
        
        
        if (changeNoOfUser>currentNoOfUser) {
            
            
            for (int i=currentNoOfUser;i<changeNoOfUser;i++) {
                
                Thread thread_i = threadList.get(i);
                thread_i.start();
                System.out.println("Start User " + i);
                
            }
            
            
            currentNoOfUser = changeNoOfUser;
            
        } else {
            
            for (int i=changeNoOfUser;i<currentNoOfUser;i++) {
                Thread thread_i = threadList.get(i);
                thread_i.stop();
               System.out.println("Stop User " + i);
            }
            
            currentNoOfUser = changeNoOfUser;
            
            
        }
        
            System.out.println(t + " , NoOfUsers:  " + currentNoOfUser);
            
            
        
        
    }
    
    public static void main(String[] args) {
        // init 100 users
      
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
            System.out.println("Interrupt: " + e.toString());         
        }
        timer.cancel();
        
       
        
        
        System.out.println("TimerTask cancelled");
        try {
            Thread.sleep(maxRunTime*1000);
            
            
            /*
             for (Thread thread_i : threadList) {
               // Thread thread_i = threadList.get(i);
                thread_i.stop();
              
            }
        */
        } catch (InterruptedException e) {
            e.printStackTrace();
            
            
            
            System.out.println("Interrupt: " + e.toString());         
        }
        
    }
    
}
