package test;


import java.util.*;
import java.util.stream.Collectors;

class Emp {
    Integer eid;
    String did;
    Integer amt;

    public Emp(Integer eid, String did, Integer amt) {
        this.eid = eid;
        this.did = did;
        this.amt = amt;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Integer getAmt() {
        return amt;
    }

    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "eid=" + eid +
                ", did='" + did + '\'' +
                ", amt=" + amt +
                '}';
    }
}

public class Test1 {

//   Find emp with second Highest Sale For Each Department.
    
//    empid,deptid,saleid,amt~
//     1,IT,123,400~
//     4,OPS,124,320~
//     3,IT,24,300~
//     2,OPS,124,321~
//     3,IT,24,200~
//     5,IT,123,20~

//    empid,deptid, amt~
//     1,IT,400~
//     4,OPS,320~


    public static void main(String[] args) {
        List<Emp> li = new ArrayList<Emp>();
        li.add(new Emp(1, "IT",400 ));
        li.add(new Emp(4, "OPS",320 ));
        li.add(new Emp(3, "IT",300));
        li.add(new Emp(2, "OPS",321 ));
        li.add(new Emp(3, "IT",200));
        li.add(new Emp(5, "IT",20 ));
        
        // Doing groupby wrt empid
        Map<Integer, List<Emp>> collect = li.stream().collect(Collectors.groupingBy(Emp::getEid, Collectors.toList()));
        List<Integer> keys = new ArrayList<>(collect.keySet());

        // All distint dept
        Set<String> dept = li.stream().map(i -> i.getDid()).collect(Collectors.toSet());
        ArrayList<String> deptAL = new ArrayList(dept);

        // Merging the sale amount
        List<Emp> mergedEmp = new ArrayList<>();
        for(Integer key : keys){
            List<Emp> elements = collect.get(key);
            mergedEmp.add(elements.stream().reduce((e1, e2) ->
                new Emp(e1.getEid(), e1.getDid(), e1.getAmt()+e2.getAmt())
            ).get());
        }
        
        // Second Highest Sale
        List<Emp> secondHighestSale = new ArrayList<Emp>();
        for(String  dId : deptAL){
            Emp eid = mergedEmp.stream().filter(i -> i.getDid().equals(dId)).sorted(Comparator.comparing(Emp::getAmt).reversed()).skip(1).limit(1).collect(Collectors.toList()).get(0);
            secondHighestSale.add(eid);
        }
        System.out.println(secondHighestSale);
    }
}
