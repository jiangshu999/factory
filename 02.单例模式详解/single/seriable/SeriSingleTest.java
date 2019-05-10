package com.jiash.single.seriable;

import java.io.*;

/**
 * @program: single
 * @Date: 2019/3/27 16:14
 * @Author: huangjp
 * @Description:
 */
public class SeriSingleTest {

    public static void main(String[] args) {
        SeriSingle s1 = null;
        SeriSingle s2 = SeriSingle.getInstance();

        FileOutputStream os = null;

        try {
            os = new FileOutputStream("SeriSingle.obj");
            ObjectOutputStream oou = new ObjectOutputStream(os);
            oou.writeObject(s2);
            oou.flush();
            oou.close();
            os.close();

            FileInputStream fis = new FileInputStream("SeriSingle.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (SeriSingle) ois.readObject();
            ois.close();
            fis.close();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
