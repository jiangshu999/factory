package com.jiash.prototype.deep;

import java.io.*;

/**
 * @program: single
 * @Date: 2019/5/10 16:56
 * @Author: huangjp
 * @Description:
 */
public class PersonB implements Serializable,Cloneable {

    @Override
    protected PersonB clone() throws CloneNotSupportedException {
        return this.deepCopy();
    }

    private PersonB deepCopy() {
        ByteArrayOutputStream baso = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baso);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(baso.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            PersonB personB = (PersonB) ois.readObject();
            return personB;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
