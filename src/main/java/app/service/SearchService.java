package app.service;

import app.model.Item;
import app.model.NodeDefinition;
import app.model.QItem;
import app.model.create.CreateDefinition;
import app.model.match.MatchDefinition;
import app.model.relation.DomainRelation;
import app.query.Query;
import com.redislabs.redisgraph.Record;
import com.redislabs.redisgraph.RedisGraphAPI;
import com.redislabs.redisgraph.ResultSet;
import iot.jcypher.database.internal.PlannerStrategy;
import iot.jcypher.domainquery.internal.Settings;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.api.modify.ModifyTerminal;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.api.predicate.Concatenator;
import iot.jcypher.query.api.returns.RSortable;
import iot.jcypher.query.factories.clause.DO;
import iot.jcypher.query.factories.clause.MATCH;
import iot.jcypher.query.factories.clause.RETURN;
import iot.jcypher.query.factories.clause.WHERE;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.values.JcRelation;
import iot.jcypher.query.writer.Format;
import iot.jcypher.util.Util;

import static app.model.relation.RelationType.OUT;
import static app.util.QueryDSLSupport.ITEM;

public class SearchService {

    public static void search() {
        Settings.plannerStrategy = PlannerStrategy.DEFAULT;

        RedisGraphAPI api = new RedisGraphAPI("tempgrph");

        api.query("CREATE (:item{name:'roi',age:32})");
        api.query("CREATE (:item{name:'amit',age:30})");
        api.query("MATCH (a:item), (b:item) WHERE (a.name = 'roi' AND b.name='amit') CREATE (a)-[:knows]->(b)");


        JcNode a = new JcNode("a");
        JcNode b = new JcNode("b");
        JcRelation r = new JcRelation(":knows");

        Node p = MATCH.node(a)
//        .node(a)
                .label("item")
                .relation(r).out()
                .node(b)
                .label("item");
        Concatenator where = WHERE.valueOf(a.property("name")).EQUALS("roi");
        RSortable ret = RETURN.value(a);
        JcQuery jcQuery = new JcQuery();
        jcQuery.setClauses(new IClause[]{p, where, ret});
        String cypher = Util.toCypher(jcQuery, Format.PRETTY_3
        );
        System.err.println(cypher);

        ResultSet resultSet = api.query(cypher);
//        ResultSet resultSet = api.query("MATCH (a:item)-[:knows]->(b:item) RETURN a");
        System.out.println(resultSet.getStatistics());

        while (resultSet.hasNext()) {
            Record record = resultSet.next();
            System.out.println(record.getString("a.name"));
        }
        api.deleteGraph();

    }

    public static void search2() {
//        Settings.plannerStrategy = PlannerStrategy.RULE;

        RedisGraphAPI api = new RedisGraphAPI("tempgrph");
        NodeDefinition<QItem> a = new NodeDefinition<>("a", QItem.class);
        NodeDefinition<QItem> b = new NodeDefinition<>("b", QItem.class);

        Node cre = CreateDefinition.builder()
                .node(new NodeDefinition<>("b", QItem.class), new Item("vasya", "clown", 11L))
                 .withRelation(new JcRelation("prefix"), OUT, DomainRelation.CONNECTS)
                .node(new NodeDefinition<>("", QItem.class), new Item("kolya", "president", 9L))
                .build();






        Node cre2 = CreateDefinition.builder().node(new NodeDefinition<>("", QItem.class), new Item("kolya", "grud", 9L)).build();
        Node cre3 = CreateDefinition.builder().node(new NodeDefinition<>("", QItem.class), new Item("vadya", "top", 88L)).build();
        String creasion = Query.builder().create(cre)
                .create(cre3)
                .build();
        System.out.println(creasion);
        api.query(creasion);

        JcRelation r = new JcRelation("t");


        Node p = MatchDefinition.builder()
                .node(a, null)
//                .withRelation(r, OUT)
//                .node(b, null)
                .build();


        Concatenator where = WHERE.valueOf(a.property(ITEM.name)).EQUALS("vasya");

        RSortable ret = RETURN.value(a);
//        ModifyTerminal set = DO.SET(a.property(ITEM.name)).to("kolya");

//        String query = CypherDomainUtils.getQuery(p, where, ret);
        String query = Query.builder().match(p)
                .where(where)
//                .set(set)
                .ret(ret)
                .build();
        System.err.println(query);

        ResultSet resultSet = api.query(query);
//        ResultSet resultSet = api.query("MATCH (a:item)-[:knows]->(b:item) RETURN a");
//        System.out.println(resultSet.getStatistics());
//        System.err.println(resultSet);
        while (resultSet.hasNext()) {
            Record record = resultSet.next();
            System.err.println("RESULT: "+record);
        }
        api.deleteGraph();

    }
}
