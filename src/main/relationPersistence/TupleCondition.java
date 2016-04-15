
package main.relationPersistence;

import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public class TupleCondition {

    ArrayList<Condition> conditions;
    ArrayList<String> bops;
    RowParser parser;
    
    //id = 10 AND note = hey
    //conditions have three parts [attribute] [compare method] [compared value]
    
    public TupleCondition(String condition, RowParser parser){
        String[] split = condition.split(" ");
        this.parser = parser;
        conditions = new ArrayList<>();
        bops = new ArrayList<>();
        int i=0;
        while(i<split.length){
            conditions.add(new Condition(split[i],split[i+1],split[i+2]));
            bops.add(split[i+3]);
            i+=4;
        }
    }
    
    
    public boolean isTupleValid(Object[] tuple){
        //Do the first condition to fill out lastCondition
        boolean lastCondition = testCondition(conditions.get(0),tuple);
        for(int i=1; i<conditions.size(); i++){
            Condition condition = conditions.get(i);
            boolean temp = testCondition(condition,tuple);
            //Check the 
        }
        return lastCondition;
    }
    
    /**
     * This is a helper method for isTupleValid. This runs a single Condition on
     * a given set of tuple values.
     * @param condition The condition to check.
     * @param tuple The list of tuple objects
     * @return True if the condition is valid over this tuple.
     */
    private boolean testCondition(Condition condition, Object[] tuple){
        int index = parser.getIndexOfAttribute(condition.attributeName);
        return parser.getParserForAttribute(index).compareAttribute(tuple[index], condition.compareOperation, condition.compareTo);
    }
    
}

class Condition {

    public String attributeName;
    public String compareTo;
    public String compareOperation;
    
    public Condition(String an, String co, String ct){
        attributeName = an;
        compareTo = ct;
        compareOperation = co;
    }
    
}
