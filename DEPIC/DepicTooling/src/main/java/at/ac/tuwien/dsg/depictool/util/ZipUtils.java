/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.util;

/**
 *
 * @author Jun
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private String parentDir;
    
    public void zipDir(String zipFileName, String dir) {
        File dirObj = new File(dir);
        
        
        parentDir = dirObj.getParent();
        System.out.println(" DIR: " + parentDir);
        
        try {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        System.out.println("Creating : " + zipFileName);
        addDir(dirObj, out);
        out.close();
        } catch(Exception ex){
             java.util.logging.Logger.getLogger(ZipUtils.class.getName()).log(Level.SEVERE, ex.toString());
        }
    }

    private void addDir(File dirObj, ZipOutputStream out) {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[1024];
        
        try {

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                addDir(files[i], out);
                continue;
            }
            FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
            System.out.println(" Adding: " + files[i].getAbsolutePath());
            
            
            out.putNextEntry(new ZipEntry(files[i].getAbsolutePath().replaceAll(parentDir, "")));
            int len;
            while ((len = in.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        } catch(Exception ex){
            java.util.logging.Logger.getLogger(ZipUtils.class.getName()).log(Level.SEVERE, ex.toString());
        }
    }

}
