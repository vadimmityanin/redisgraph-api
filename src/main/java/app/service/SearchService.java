package app.service;

import com.redislabs.redisgraph.Record;
import com.redislabs.redisgraph.RedisGraphAPI;
import com.redislabs.redisgraph.ResultSet;
import iot.jcypher.database.internal.PlannerStrategy;
import iot.jcypher.domainquery.internal.Settings;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.api.predicate.Concatenator;
import iot.jcypher.query.api.returns.RSortable;
import iot.jcypher.query.factories.clause.MATCH;
import iot.jcypher.query.factories.clause.RETURN;
import iot.jcypher.query.factories.clause.WHERE;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.values.JcRelation;
import iot.jcypher.query.values.JcValue;
import iot.jcypher.query.writer.Format;
import iot.jcypher.util.Util;

public class SearchService {

    public static void search() {
        Settings.plannerStrategy = PlannerStrategy.DEFAULT;

        RedisGraphAPI api = new RedisGraphAPI("tempgrph");

        api.query("CREATE (:Person{name:'roi',age:32})");
        api.query("CREATE (:Person{name:'amit',age:30})");
        api.query("MATCH (a:Person), (b:Person) WHERE (a.name = 'roi' AND b.name='amit') CREATE (a)-[:knows]->(b)");


        JcNode a = new JcNode("a");
        JcNode b = new JcNode("b");
        JcRelation r = new JcRelation(":knows");

        Node p = MATCH.node(a)
//        .node(a)
                .label("Person")
                .relation(r).out()
                .node(b)
                .label("Person")
                ;
        Concatenator where = WHERE.valueOf(a.property("name")).EQUALS("roi");
        RSortable ret = RETURN.value(a);
        JcQuery jcQuery = new JcQuery();
        jcQuery.setClauses(new IClause[] {p,where,ret});
        String cypher = Util.toCypher(jcQuery, Format.PRETTY_3
        );
        System.err.println(cypher);

        ResultSet resultSet = api.query(cypher);
//        ResultSet resultSet = api.query("MATCH (a:Person)-[:knows]->(b:Person) RETURN a");
        System.out.println(resultSet.getStatistics());

        while(resultSet.hasNext()){
            Record record = resultSet.next();
            System.out.println(record.getString("a.name"));
        }
        api.deleteGraph();

    }
}
