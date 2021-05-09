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
    
    
    /* logs ----
     * "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\jbr\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\lib\idea_rt.jar=57607:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.3.3\bin" -Dfile.encoding=UTF-8 -classpath "C:\Users\Sandeep.KS\OneDrive - EY\Desktop\lib\New folder\java8\target\classes;C:\Users\Sandeep.KS\.m2\repository\org\jooq\jool\0.9.14\jool-0.9.14.jar;C:\Users\Sandeep.KS\.m2\repository\com\diogonunes\JColor\5.0.1\JColor-5.0.1.jar;C:\Users\Sandeep.KS\.m2\repository\net\java\dev\jna\jna\5.5.0\jna-5.5.0.jar;C:\Users\Sandeep.KS\.m2\repository\net\java\dev\jna\jna-platform\5.5.0\jna-platform-5.5.0.jar;C:\Users\Sandeep.KS\.m2\repository\org\apache\commons\commons-lang3\3.11\commons-lang3-3.11.jar;C:\Users\Sandeep.KS\.m2\repository\com\github\javafaker\javafaker\0.12\javafaker-0.12.jar;C:\Users\Sandeep.KS\.m2\repository\com\github\mifmif\generex\1.0.2\generex-1.0.2.jar;C:\Users\Sandeep.KS\.m2\repository\dk\brics\automaton\automaton\1.11-8\automaton-1.11-8.jar" test.Temp1
     * [Emp{eid=4, did='OPS', amt=320}, Emp{eid=1, did='IT', amt=400}]
     * Process finished with exit code 0
     */
    
    
    /*SQL Solution 
     *Assume Table (T) struture -> 
     *  eId -> int
     *  dId -> string
     *  samt -> int
     *
     *SELECT t1.eId as empId,  t1.dId as deptId , t1.samt as sAmt, DENSE_RANK() OVER (partition by t1.dId order by t1.samt desc) rank 
     *        FROM  (Select t.empId as eId ,  t.deptId as dId, sum(t.saleId) as samt from T t group by empId,deptId)as Temptable t1 
     *        WHERE rank =2 order by dId;  
     *
     *Output Columns -> empId deptId sAmt rank
     *                     1    IT   400   2
     *                     4    OPS  320   2
     */


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
