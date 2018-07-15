package com.briup.bigdata.project.grms.step8;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class ValueToDB implements WritableComparable<ValueToDB>,DBWritable{
    private String uid;
    private String gid;
    private int exp;
    public ValueToDB(){
    }
    public ValueToDB(String uid,String gid,int exp){
        this.uid = uid;
        this.gid = gid;
        this.exp = exp;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getGid() {
        return gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
    }
    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(),getGid(),getExp());
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof ValueToDB))
            return false;
        ValueToDB vb = (ValueToDB)obj;
        return getExp()==vb.getExp()&&Objects.equals(getUid(),vb.getUid())&&Objects.equals(getGid(),vb.getGid());
    }

    public int compareTo(ValueToDB o) {
        int u = this.uid.compareTo(o.uid);
        int g = this.gid.compareTo(o.gid);
        int e = this.exp-o.exp;
        return u==0?(g==0?e:g):u;
    }
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(uid);
        dataOutput.writeUTF(gid);
        dataOutput.writeInt(exp);
    }

    public void readFields(DataInput dataInput) throws IOException {
        uid = dataInput.readUTF();
        gid = dataInput.readUTF();
        exp = dataInput.readInt();
    }

    public void write(PreparedStatement ps) throws SQLException {
        ps.setString(1,uid);
        ps.setString(2,gid);
        ps.setInt(3,exp);
    }

    public void readFields(ResultSet rs) throws SQLException {
        if(rs==null)
            return;
        uid = rs.getString(1);
        gid = rs.getString(2);
        exp = rs.getInt(3);
    }
}
